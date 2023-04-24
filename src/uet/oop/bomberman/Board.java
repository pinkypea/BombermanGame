package uet.oop.bomberman;

import uet.oop.bomberman.KeyBoard.Keyboard;
import uet.oop.bomberman.MapLevel.FileLevelLoader;
import uet.oop.bomberman.MapLevel.LevelLoader;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.DirecFire;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.Player;
import uet.oop.bomberman.graphics.Render;
import uet.oop.bomberman.graphics.Screen;

import java.awt.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class Board implements Render {
    protected Game game;
    protected LevelLoader levelLoader;
    protected Keyboard input;
    protected Screen screen;

    public Entity[] entities;
    public List<Character> characters = new ArrayList<>();
    protected List<Bomb> bombs = new ArrayList<>();

    private int screenToShow = -1; //1:endgame, 2:changelevel, 3:paused
    private int time = Game.TIME;
    private int points = Game.POINTS;
    private int lives = Game.LIVES;

    /**
     * Constructor.
     */
    public Board(Game game, Keyboard input, Screen screen) {
        this.game = game;
        this.input = input;
        this.screen = screen;
        loadLevel(1);
    }

    @Override
    public void update() {
        if (screenToShow == 1 && input.space) {
            newGame();
        }

        if (game.isPaused()) {
            return;
        }
        updateEntities();
        updateCharacters();
        updateBombs();
        detectEndGame();

        for (int i = 0; i < characters.size(); i++) {
            Character a = characters.get(i);
            if (a.isRemove()) {
                characters.remove(i);
            }
        }
    }

    @Override
    public void render(Screen screen) {
        if (game.isPaused()) return;

        int xLeft = Screen.xOffset >> 4;
        int yUp = Screen.yOffset >> 4;
        int xRight = (Screen.xOffset + screen.getWidth() + Game.TILES_SIZE) / Game.TILES_SIZE;
        int yDown = (Screen.yOffset + screen.getHeight()) / Game.TILES_SIZE;
        for (int y = yUp; y < yDown; y++) {
            for (int x = xLeft; x < xRight; x++) {
                entities[x + y * levelLoader.getWidth()].render(screen);
            }
        }
        renderBombs(screen);
        renderCharacter(screen);
    }

    public void loadLevel(int level) {
        time = Game.TIME;
        screenToShow = 2;
        game.resetScreenDelay();
        game.pause();
        characters.clear();
        bombs.clear();

        if (level != 1) {
            endGame();
            return;
        }
        levelLoader = new FileLevelLoader(level, this);
        levelLoader.loadLevel(1);
        entities = new Entity[levelLoader.getHeight() * levelLoader.getWidth()];
        levelLoader.createEntities();
    }

    public void restartLevel() {
        loadLevel(levelLoader.getLevel());
    }

    protected void detectEndGame() {
        if (time <= 0) {
            restartLevel();
        }
    }

    public void newGame() {
        lives = Game.LIVES;
        points = Game.POINTS;
        Game.bombRadius = Game.BOMBRADIUS;
        Game.bombRate = Game.BOMBRATE;
        Game.bomberSpeed = Game.BOMBERSPEED;
        loadLevel(1);
    }

    public void endGame() {
        screenToShow = 1;
        game.resetScreenDelay();
        game.pause();
    }

    /**
     * kiem tra còn enemy  ?
     *
     * @return false nếu còn
     */
    public boolean detectNoEnemies() {
        for (Character c : characters) {
            if (c instanceof Player == false)
                return false;
        }
        return true;
    }

    /**
     * Vẽ các màn hình phụ cho game.
     */
    public void drawScreen(Graphics g) {
        switch (screenToShow) {
            case 1:
                screen.drawGameOver(g, points);
                break;
            case 2:
                screen.drawChangeLevel(g, levelLoader.getLevel());
                break;
            case 3:
                screen.drawPaused(g);
                break;
        }
    }

    public Entity getEntity(double x, double y, Character m) {
        Entity other = null;

        other = getDirecFireAt((int) x, (int) y);
        if (other != null) {
            return other;
        }
        other = getBombAt(x, y);
        if (other != null) {
            return other;
        }
        other = getCharacterAtExcluding((int) x, (int) y, m);
        if (other != null) {
            return other;
        }
        other = getEntityAt((int) x, (int) y);
        return other;
    }

    /**
     * Trả về Character ở vị trí x,y.
     */
    public Character getCharacterAt(double x, double y) {
        for (Character other : characters) {
            if (other.getXTile() == x && other.getYTile() == y) {
                return other;
            }
        }
        return null;
    }

    /**
     * Trả về bombs.
     */
    public List<Bomb> getBombs() {
        return bombs;
    }

    /**
     * Lấy ra Bomb ở vị trí x,y.
     */
    public Bomb getBombAt(double x, double y) {
        for (Bomb b : bombs) {
            if (b.getX() == (int) x && b.getY() == (int) y) {
                return b;
            }
        }
        return null;
    }

    /**
     * Trả về đối tượng Bomber.
     *
     * @return Bomber
     */
    public Player getBomber() {
        for (Character other : characters) {
            if (other instanceof Player) {
                return (Player) other;
            }
        }
        return null;
    }

    /**
     * Trả về thực thể ở ô vị trí x,y và khác Character a.
     */
    public Character getCharacterAtExcluding(int x, int y, Character a) {
        for (Character character : characters) {
            if (character == a) {
                continue;
            }
            if (character.getXTile() == x && character.getYTile() == y) {
                return character;
            }
        }
        return null;
    }

    /**
     * Trả về đối tượng DirecFire ở vị trí (x,y).
     */
    public DirecFire getDirecFireAt(int x, int y) {
        for (Bomb b : bombs) {
            DirecFire direcFire = b.fireAt(x, y);
            if (direcFire != null) {
                return direcFire;
            }
        }
        return null;
    }

    /**
     * Trả về thực thể ở vị trí x,y.
     */
    public Entity getEntityAt(int x, int y) {
        return entities[x + y * levelLoader.getWidth()];
    }

    /**
     * Thêm mới 1 entity vào bảng.
     */
    public void addEntity(int pos, Entity e) {
        entities[pos] = e;
    }

    /**
     * Thêm mới 1 Character vào board.
     */
    public void addCharacter(Character e) {
        characters.add(e);
    }

    /**
     * Add a new Bomb to board.
     */
    public void addBomb(Bomb e) {
        bombs.add(e);
    }

    /**
     * Render all Character to Screen.
     */
    protected void renderCharacter(Screen screen) {
        for (Character character : characters) {
            character.render(screen);
        }
    }

    /**
     * render all Bombs to Screen.
     */
    protected void renderBombs(Screen screen) {
        for (Bomb b : bombs) {
            b.render(screen);
        }
    }

    /**
     * update Entities.
     */
    protected void updateEntities() {
        if (game.isPaused()) return;
        for (Entity entity : entities) {
            entity.update();
        }
    }

    /**
     * update Characters.
     */
    protected void updateCharacters() {
        if (game.isPaused()) {
            return;
        }
        Iterator<Character> itr = characters.iterator();
        while (itr.hasNext() && !game.isPaused()) {
            itr.next().update();
        }
    }

    /**
     * Update Bombs.
     */
    protected void updateBombs() {
        if (game.isPaused()) {
                return;
        }
        for (Bomb b : bombs) {
            b.update();
        }
    }


    public int subtractTime() {
        if(game.isPaused())
            return this.time;
        else
            return this.time--;
    }

    public Keyboard getInput() {
        return input;
    }

    public int getLives() {
        return lives;
    }

    public void addLives(int lives) {
        this.lives += lives;
    }

    public Game getGame() {
        return game;
    }

    public int getShow() {
        return screenToShow;
    }

    public void setShow(int i) {
        screenToShow = i;
    }

    public int getTime() {
        return time;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getWidth() {
        return levelLoader.getWidth();
    }

    public int getHeight() {
        return levelLoader.getHeight();
    }
}
