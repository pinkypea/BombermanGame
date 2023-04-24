package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Animation;
import uet.oop.bomberman.graphics.Screen;

public abstract class Character extends Animation {
    protected Board board;
    protected int direction = -1;
    protected boolean alive = true;
    protected boolean moving = false;
    public int  timeAfter = 40;

    /**
     * Constructor.
     */
    public Character(int x, int y, Board board) {
        coordinateX = x;
        coordinateY = y;
        this.board = board;
    }

    @Override
    public abstract void update();

    @Override
    public abstract void render(Screen screen);

    /**
     * Tính toán hướng đi.
     */
    protected abstract void calculateMove();

    protected abstract void move(double xa, double ya);

    /**
     * Được gọi khi đối tượng bị tiêu diệt.
     */
    public abstract void killed();

    /**
     * Xử lý hiệu ứng bị tiêu diệt.
     */
    protected abstract void afterKilled();

    /**
     * Kiểm tra xem đối tượng có di chuyển tới vị trí đã tính toán hay không.
     *
     * @param x double x
     * @param y double y
     * @return boolean true or false
     */
    protected abstract boolean canMove(double x, double y);
}
