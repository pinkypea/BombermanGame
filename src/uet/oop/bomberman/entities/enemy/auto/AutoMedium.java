package uet.oop.bomberman.entities.enemy.auto;

import uet.oop.bomberman.entities.character.Player;
import uet.oop.bomberman.entities.enemy.Enemy;

public class AutoMedium extends Auto {
    Player player;
    Enemy enemy;

    public AutoMedium(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    @Override
    public int calculateDirection() {
        if(player == null) {
            return random.nextInt(4);
        }

        int vertical = random.nextInt(2);

        if (vertical == 1) {
            int v = calculateRowDirection();
            if (v != -1) {
                return v;
            } else {
                return calculateColDirection();
            }
        } else {
            int h = calculateColDirection();

            if (h != -1) {
                return h;
            } else {
                return calculateRowDirection();
            }
        }
    }

    protected int calculateColDirection() {
        if (player.getXTile() < enemy.getXTile()) {
            return 3;
        } else if (player.getXTile() > enemy.getXTile())  {
            return 1;
        }
        return -1;
    }

    protected int calculateRowDirection() {
        if (player.getYTile() < enemy.getYTile()) {
            return 0;
        } else if (player.getYTile() > enemy.getYTile()) {
            return 2;
        }
        return -1;
    }
}
