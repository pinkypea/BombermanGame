package uet.oop.bomberman.MapLevel;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Player;
import uet.oop.bomberman.entities.enemy.Balloon;
import uet.oop.bomberman.entities.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FireItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLevelLoader extends LevelLoader {
    /**
     * Constructor.
     */
    public FileLevelLoader(int level, Board board) {
        super(level, board);
    }

    private static String[] map;

    @Override
    public void loadLevel(int level) {
        try {
            Scanner sc = new Scanner(new File("res/levels/Level" + level + ".txt"));
            this.level = sc.nextInt();
            this.height = sc.nextInt();
            this.width = sc.nextInt();
            map = new String[height];
            map[0] = sc.nextLine();
            for (int i = 0; i < this.height; i++) {
                map[i] = sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createEntities() {
        for (int j = 0; j < this.height; j++) {
            for (int i = 0; i < this.width; i++) {
                addEntity(map[j].charAt(i), i, j);
            }
        }
    }
    /**
     * Thêm entity.
     */
    public void addEntity(char c, int x, int y) {
        int p = x + y * this.width;
        switch (c) {
            case '#':
                board.addEntity(p, new Wall(x, y, Sprite.wall));
                break;
            case ' ':
                board.addEntity(p, new Grass(x, y, Sprite.grass));
                break;
            case 'p':
                board.addCharacter(new Player(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                Screen.setOffset(0, 0);
                board.addEntity(p, new Grass(x, y, Sprite.grass));
                break;
            case '*':
                board.addEntity(p, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick)));
                break;
            case 'b':
                board.addEntity(p, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new BombItem(x, y, Sprite.powerup_bombs), new Brick(x, y, Sprite.brick)));
                break;
            case 'f':
                board.addEntity(p, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new FireItem(x, y, Sprite.powerup_flames), new Brick(x, y, Sprite.brick)));
                break;
            case 's':
                board.addEntity(p, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new SpeedItem(x, y, Sprite.powerup_speed), new Brick(x, y, Sprite.brick)));
                break;
            case 'x':
                board.addEntity(p, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Portal(x, y, Sprite.portal, board), new Brick(x, y, Sprite.brick)));
                break;
            case '1':
                board.addCharacter(new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                board.addEntity(p, new Grass(x, y, Sprite.grass));
                break;
            case '2':
                board.addCharacter(new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                board.addEntity(p, new Grass(x, y, Sprite.grass));
                break;
            default:
                board.addEntity(p, new Grass(x, y, Sprite.grass));
                break;
        }
    }
}
