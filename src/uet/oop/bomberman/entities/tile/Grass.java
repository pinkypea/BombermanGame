package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Grass extends Tile {

    /**
     * Constructor.
     */
    public Grass(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    /**
     * Tất cả đối tượng đều đi qua được.
     */
    @Override
    public boolean checkCollide(Entity entity) {
        return true;
    }
}
