package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.KeyBoard.Keyboard;
import uet.oop.bomberman.MapLevel.Coordinates;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.DirecFire;
import uet.oop.bomberman.entities.bomb.Fire;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Iterator;
import java.util.List;

public class Player extends Character {
    private List<Bomb> bombs;
    protected Keyboard input;

    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update().
     */
    protected int timeBetweenPutBombs = 0;

    /**
     * Constructor.
     */
    public Player(int x, int y, Board board) {
        super(x, y, board);
        bombs = board.getBombs();
        input = board.getInput();
        sprite = Sprite.player_right;
    }
    @Override
    public void update() {
        clearBombs();
        if (!alive) {
            afterKilled();
            return;
        }
        if (timeBetweenPutBombs < -3000) {
            timeBetweenPutBombs = 0;
        } else {
            timeBetweenPutBombs--;
        }
        animate();
        calculateMove();
        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();
        if (alive) {
            chooseSprite();
        } else {
            sprite = Sprite.player_dead1;
        }
        screen.renderEntity((int) coordinateX, (int) coordinateY - sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(board, this);
        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiểm tra có đặt bom được không? Có thì đặt bom tại vị trí hiện tại của Player.
     */
    private void detectPlaceBomb() {
        if (input.space && timeBetweenPutBombs < 0 && Game.getBombRate() > 0) {
            int x1 = Coordinates.pixelToTile(sprite.getSize() / 2 + coordinateX);
            int y1 = Coordinates.pixelToTile(coordinateY - sprite.getSize() / 1.5);

            placeBomb(x1, y1);
            Game.addBombRate(-1);
            timeBetweenPutBombs = 30;
        }
    }

    /**
     * dat bom tai vi tri x, y.
     */
    protected void placeBomb(int x, int y) {
        Bomb b = new Bomb(x, y, board);
        board.addBomb(b);
    }

    private void clearBombs() {
        Iterator<Bomb> b = bombs.iterator();
        Bomb bomb;
        while (b.hasNext()) {
            bomb = b.next();
            if (bomb.isRemove()) {
                b.remove();
                Game.addBombRate(1);
            }
        }
    }

    @Override
    protected void calculateMove() {
        int x0 = 0, y0 = 0;
        if (input.left) {
            x0--;
        }

        if (input.right) {
            x0++;
        }

        if (input.up) {
            y0--;
        }

        if (input.down) {
            y0++;
        }

        if (x0 != 0 || y0 != 0) {
            move(x0 * Game.getBomberSpeed(), y0 * Game.getBomberSpeed());
            moving = true;
        } else {
            moving = false;
        }
    }

    @Override
    public void move(double xa, double ya) {
        if (canMove(0, ya)) {
            coordinateY += ya;
        }
        if (canMove(xa, 0)) {
            coordinateX += xa;
        }
        if (xa < 0) {
            direction = 3;
        }
        if (xa > 0) {
            direction = 1;
        }
        if (ya < 0) {
            direction = 0;
        }
        if (ya > 0) {
            direction = 2;
        }
    }

    @Override
    public void killed() {
        if (!alive) return;
        alive = false;

        board.addLives(-1);

    }

    @Override
    protected void afterKilled() {
        if (timeAfter > 0) {
            timeAfter--;
        } else {
            if (bombs.size() == 0) {
                if (board.getLives() == 0) {
                    board.endGame();
                } else {
                    board.restartLevel();
                }
            }
        }
    }

    @Override
    public boolean canMove(double x, double y) {
        for (int i = 0; i < 4; i++) {
            double x1 = ((coordinateX + x) + i % 2 * 11) / Game.TILES_SIZE;
            double y1 = ((coordinateY + y) + i / 2 * 12 - 13) / Game.TILES_SIZE;
            Entity entity = board.getEntity(x1, y1, this);
            if (entity != null) {
                if (!entity.checkCollide(this))
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkCollide(Entity entity) {

        if(entity instanceof Fire || entity instanceof DirecFire) {
            killed();
            return false;
        }

        if(entity instanceof Enemy) {
            killed();
            return true;
        }

        return true;
    }

    private void chooseSprite() {
        switch (direction) {
            case 0:
                sprite = Sprite.player_up;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20);
                }
                break;
            case 1:
                sprite = Sprite.player_right;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                }
                break;
            case 2:
                sprite = Sprite.player_down;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20);
                }
                break;
            case 3:
                sprite = Sprite.player_left;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20);
                }
                break;
            default:
                sprite = Sprite.player_right;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                }
                break;
        }
    }
}
