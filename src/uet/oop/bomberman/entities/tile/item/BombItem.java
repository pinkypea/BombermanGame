package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Player;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Item {

    /**
     * Constructor.
     */
    public BombItem(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public boolean checkCollide(Entity entity) {
        if (isRemove()) {
            return false;
        }
        if(entity instanceof Player) {
            Game.addBombRate(1);
            remove();
            return true;
        }
        return false;
    }
}
