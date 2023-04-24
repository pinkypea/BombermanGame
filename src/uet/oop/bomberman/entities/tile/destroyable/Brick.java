package uet.oop.bomberman.entities.tile.destroyable;

import uet.oop.bomberman.MapLevel.Coordinates;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Destroyable {
    /**
     * Constructor.
     */
    public Brick(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Screen screen) {
        int x1 = Coordinates.tileToPixel(coordinateX);
        int y1 = Coordinates.tileToPixel(coordinateY);
        if (destroyed) {
            sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);
            screen.renderEntityWithSpriteBelow(x1, y1, this, sprite);
        } else {
            screen.renderEntity(x1, y1, this);
        }
    }
}
