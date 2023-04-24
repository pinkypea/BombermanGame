package uet.oop.bomberman.entities.tile.destroyable;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Fire;
import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphics.Sprite;

public class Destroyable extends Tile {
    private final int maxAnimate = 60;
    private int animate;
    protected boolean destroyed = false;
    protected int timeRemain = 20;
    protected Sprite sprite = Sprite.grass;

    /**
     * Constructor.
     */
    public Destroyable(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        if (destroyed) {
            if (animate < maxAnimate) {
                animate++;
            } else {
                animate = 0;
            }
            if (timeRemain > 0) {
                timeRemain--;
            } else {
                remove();
            }
        }
    }

    public void destroy() {
        this.destroyed = true;
    }

    @Override
    public boolean checkCollide(Entity entity) {
        if(entity instanceof Fire) {
            destroy();
        }
        return false;
    }

    public void addBelow(Sprite sprite) {
        this.sprite = sprite;
    }

    protected Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2) {
        int calc = animate % 30;
        if (calc < 10) {
            return normal;
        }
        if (calc < 20) {
            return x1;
        }
        return x2;
    }
}
