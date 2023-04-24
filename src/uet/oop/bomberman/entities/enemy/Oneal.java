package uet.oop.bomberman.entities.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.enemy.auto.AutoMedium;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    public Oneal(int x, int y, Board board) {
        super(x, y, board, Sprite.oneal_dead, Game.getBomberSpeed(), 200);

        sprite = Sprite.oneal_left1;

        auto = new AutoMedium(board.getBomber(), this);
        direction  = auto.calculateDirection();
    }

    @Override
    protected void chooseSprite() {
        switch(direction) {
            case 0:

            case 1:
                if(moving)
                    sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 60);
                else
                    sprite = Sprite.oneal_left1;
                break;
            case 2:
            case 3:
                if(moving)
                    sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 60);
                else
                    sprite = Sprite.oneal_left1;
                break;
        }
    }
}
