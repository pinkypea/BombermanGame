package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.MapLevel.Coordinates;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Tile extends Entity {

    /**
     * Constructor.
     */
    public Tile(int x, int y, Sprite sprite) {
        this.coordinateX = x;
        this.coordinateY = y;
        this.sprite = sprite;
    }

    @Override
    public boolean checkCollide(Entity e) {
        return false;
    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity(Coordinates.tileToPixel(this.coordinateX), Coordinates.tileToPixel(this.coordinateY), this);
    }

    @Override
    public void update() {

    }
}
