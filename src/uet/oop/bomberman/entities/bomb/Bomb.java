package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.MapLevel.Coordinates;
import uet.oop.bomberman.entities.Animation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.Player;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Animation {
    public int time = 20;
    public Fire[] fires;
    protected double timeRemainToExplode = 110;
    protected boolean exploded = false;
    protected boolean canBePassed = true;
    protected Board board;

    /**
     * Constructor.
     */
    public Bomb(int x, int y, Board board) {
        this.coordinateX = x;
        this.coordinateY = y;
        this.board = board;
        this.sprite = Sprite.bomb;
    }

    @Override
    public void update() {
        if (timeRemainToExplode > 0) {
            timeRemainToExplode--;
        } else {
            if (!exploded) {
                explode();
            } else {
                updateFire();
            }

            if (time > 0) {
                time--;
            } else {
                remove();
            }
        }
        animate();
    }

    @Override
    public void render(Screen screen) {
        if(exploded) {
            sprite =  Sprite.bomb_exploded2;
            renderFire(screen);
        } else {
            sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60);
        }

        int xt = (int) (coordinateX * 16);
        int yt = (int) (coordinateY * 16);

        screen.renderEntity(xt, yt , this);
    }

    public void renderFire(Screen screen) {
        for (int i = 0; i < fires.length; i++) {
            fires[i].render(screen);
        }
    }

    public void updateFire() {
        for (int i = 0; i < fires.length; i++) {
            fires[i].update();
        }
    }

    /**
     * Xử lý bomb nổ.
     */
    protected void explode() {
        exploded = true;
        canBePassed = true;
        Character character = board.getCharacterAt(coordinateX, coordinateY);
        if (character != null) {
            character.killed();
        }
        fires = new Fire[4];
        for (int i = 0; i < fires.length; i++) {
            fires[i] = new Fire((int) coordinateX, (int) coordinateY, i, Game.getBombRadius(), board);
        }

    }

    public DirecFire fireAt(int x, int y) {
        if (!exploded) {
            return null;
        }
        for (int i = 0; i < fires.length; i++) {
            if (fires[i] == null) {
                return null;
            }
            DirecFire e = fires[i].fireSegmentAt(x, y);
            if (e != null) {
                return e;
            }
        }
        return null;
    }

    /**
     * Xu ly va cham.
     */
    @Override
    public boolean checkCollide(Entity entity) {
        if (entity instanceof Player) {
            double diffX = entity.getX() - Coordinates.tileToPixel(getX());
            double diffY = entity.getY() - Coordinates.tileToPixel(getY());
            if (!(diffX >= -10 && diffX < 16 && diffY >= 1 && diffY <= 28)) {
                canBePassed = false;
            }
            return canBePassed;
        }
        if (entity instanceof DirecFire || entity instanceof Fire) {
            timeRemainToExplode = 0;
            return true;
        }
        return false;
    }
}
