package uet.oop.bomberman.entities.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.MapLevel.Coordinates;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Fire;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.Player;
import uet.oop.bomberman.entities.enemy.auto.Auto;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Character {
    protected int points;

    protected double speed;
    protected Auto auto;

    protected final double MAXSTEPS;
    protected final double rest;
    protected double steps;

    protected int finalAnimation = 30;
    protected Sprite deadSprite;

    public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
        super(x, y, board);

        this.points = points;
        this.speed = speed;

        MAXSTEPS = Game.TILES_SIZE / speed;
        rest = (MAXSTEPS - (int) MAXSTEPS) / MAXSTEPS;
        steps = MAXSTEPS;

        timeAfter = 20;
        deadSprite = dead;
    }

    @Override
    public void update() {
        animate();

        if(!alive) {
            afterKilled();
            return;
        }

        if(alive)
            calculateMove();
    }

    @Override
    public void render(Screen screen) {
        if(alive)
            chooseSprite();
        else {
            if(timeAfter > 0) {
                sprite = deadSprite;
                animate = 0;
            } else {
                sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 60);
            }

        }

        screen.renderEntity((int)coordinateX, (int)coordinateY - sprite.SIZE, this);
    }

    @Override
    public void calculateMove() {
        int xa = 0, ya = 0;
        if(steps <= 0){
            direction = auto.calculateDirection();
            steps = MAXSTEPS;
        }

        if(direction == 0) {
            ya--;
        }
        if(direction == 2) {
            ya++;
        }
        if(direction == 3) {
            xa--;
        }
        if(direction == 1) {
            xa++;
        }

        if(canMove(xa, ya)) {
            steps -= 1 + rest;
            move(xa * speed, ya * speed);
            moving = true;
        } else {
            steps = 0;
            moving = false;
        }
    }

    @Override
    public void move(double xa, double ya) {
        if(!alive) {
            return;
        }
        coordinateY += ya;
        coordinateX += xa;
    }

    @Override
    public boolean canMove(double x, double y) {
        int x1 = (int) x;
        int y1 = (int) y;
        if (direction == 0) {
            y1 += Coordinates.pixelToTile(-17 + sprite.getSize() + coordinateY);
            x1 += Coordinates.pixelToTile(sprite.getSize() / 2 + coordinateX);
        } else if (direction == 1) {
            y1 += Coordinates.pixelToTile(-16 + sprite.getSize() / 2 + coordinateY);
            x1 += Coordinates.pixelToTile(1 + coordinateX);
        } else if (direction == 2) {
            y1 += Coordinates.pixelToTile(-15 + coordinateY);
            x1 += Coordinates.pixelToTile(sprite.getSize() / 2 + coordinateX);
        } else if (direction == 3) {
            y1 += Coordinates.pixelToTile(-16 + sprite.getSize() / 2 + coordinateY);
            x1 += Coordinates.pixelToTile(-1 + sprite.getSize() + coordinateX);
        } else {
            x1 += Coordinates.pixelToTile(coordinateX) + (int) x;
            y1 += Coordinates.pixelToTile(-16 + coordinateY) + (int) y;
        }
        Entity a = board.getEntity(x1, y1, this);

        return a.checkCollide(this);
    }

    @Override
    public boolean checkCollide(Entity e) {
        if (e instanceof Fire) {
            killed();
            return false;
        }

        if (e instanceof Player) {
            ((Player) e).killed();
            return false;
        }

        return true;
    }

    @Override
    public void killed() {
        if(!alive) return;
        alive = false;

        board.addPoints(points);
    }


    @Override
    protected void afterKilled() {
        if(timeAfter > 0) timeAfter--;
        else {
            if(finalAnimation > 0) finalAnimation--;
            else
                remove();
        }
    }

    protected abstract void chooseSprite();
}
