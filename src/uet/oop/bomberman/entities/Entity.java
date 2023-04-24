package uet.oop.bomberman.entities;


import uet.oop.bomberman.graphics.Render;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.MapLevel.Coordinates;

public abstract class Entity implements Render {
    protected double coordinateX, coordinateY;
    protected boolean remove = false;
    protected Sprite sprite;

    @Override
    public abstract void update();

    @Override
    public abstract void render(Screen screen);

    public void remove() {
        remove = true;
    }
    public double getX() {
        return coordinateX;
    }

    public double getY() {
        return coordinateY;
    }

    public boolean isRemove() {
        return remove;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public abstract boolean checkCollide(Entity entity);

    public int getXTile() {
        return Coordinates.pixelToTile(coordinateX + sprite.SIZE / 2);
    }

    public int getYTile() {
        return Coordinates.pixelToTile(coordinateY - sprite.SIZE / 2);
    }
}
