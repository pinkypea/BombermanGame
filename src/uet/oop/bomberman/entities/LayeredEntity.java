package uet.oop.bomberman.entities;

import uet.oop.bomberman.entities.tile.destroyable.Destroyable;
import uet.oop.bomberman.graphics.Screen;

import java.util.LinkedList;

public class LayeredEntity extends Entity {
    protected LinkedList<Entity> entity = new LinkedList<>();

    /**
     * Constructor.
     */
    public LayeredEntity(int x, int y, Entity ... entities) {
        coordinateX = x;
        coordinateY = y;
        for (int i = 0; i < entities.length; i++) {
            entity.add(entities[i]);
            if (i > 1) {
                if (entities[i] instanceof Destroyable)
                    ((Destroyable) entities[i]).addBelow(entities[i - 1].getSprite());
            }
        }
    }

    @Override
    public void update() {
        clearRemoved();
        getTopEntity().update();
    }

    /**
     * Khi render, ta chỉ render entity ở trên.
     */
    @Override
    public void render(Screen screen) {
        getTopEntity().render(screen);
    }

    public Entity getTopEntity() {
        return entity.getLast();
    }

    /**
     * Nếu Entity trên cùng đã bị remove thì xóa nó đi.
     */
    private void clearRemoved() {
        Entity top = getTopEntity();
        if (top.isRemove()) {
            entity.removeLast();
        }
    }

    /**
     * Kiểm tra va chạm.
     */
    @Override
    public boolean checkCollide(Entity e) {
        return getTopEntity().checkCollide(e);
    }
}
