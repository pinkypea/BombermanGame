package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class DirecFire extends Entity {
    protected boolean last;

    public DirecFire(int x, int y, int direction, boolean last) {
        this.coordinateX = x;
        this.coordinateY = y;
        this.last = last;

        if (direction == 0) {
            if (!last) {
                sprite = Sprite.explosion_vertical2;
            } else {
                sprite = Sprite.explosion_vertical_top_last2;
            }
        }

        if (direction == 1) {
            if (!last) {
                sprite = Sprite.explosion_horizontal2;
            } else {
                sprite = Sprite.explosion_horizontal_right_last2;
            }
        }

        if (direction == 2) {
            if (!last) {
                sprite = Sprite.explosion_vertical2;
            } else {
                sprite = Sprite.explosion_vertical_down_last2;
            }
        }

        if (direction == 3) {
            if (!last) {
                sprite = Sprite.explosion_horizontal2;
            } else {
                sprite = Sprite.explosion_horizontal_left_last2;
            }
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity((int) coordinateX * 16, (int) coordinateY * 16, this);
    }

    @Override
    public boolean checkCollide(Entity entity) {
        if (entity instanceof Character) {
            ((Character) entity).killed();
            return false;
        }
        return true;
    }

}

