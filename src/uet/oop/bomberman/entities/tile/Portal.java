package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Player;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {
    protected Board board;

    /**
     * Constructor.
     */
    public Portal(int x, int y, Sprite sprite, Board board) {
        super(x, y, sprite);
        this.board = board;
    }

    /**
     * Xử lý va cham với player.
     */
    @Override
    public boolean checkCollide(Entity entity) {
        if (entity instanceof Player) {
            if (!board.detectNoEnemies())
                return false;

            if (entity.getXTile() == getX() && entity.getYTile() == getY()) {
                if (board.detectNoEnemies())
                    board.newGame();
            }

            return true;
        }
        return false;
    }

}
