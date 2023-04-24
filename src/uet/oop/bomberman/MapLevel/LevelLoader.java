package uet.oop.bomberman.MapLevel;

import uet.oop.bomberman.Board;

public abstract class LevelLoader {
    protected int width = 20, height = 20;
    protected int level;
    protected Board board;

    /**
     * Constructor.
     */
    public LevelLoader(int level, Board board) {
        this.level = level;
        this.board = board;
    }

    public abstract void loadLevel(int level);

    public abstract void createEntities();

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLevel() {
        return level;
    }

}
