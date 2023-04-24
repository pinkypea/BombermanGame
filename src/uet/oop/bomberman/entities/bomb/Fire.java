package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;

public class Fire extends Entity {
    private int radius;
    protected Board board;
    protected int direction;
    protected int x0, y0;
    protected DirecFire[] direcFires = new DirecFire[0];

    public Fire(int x, int y, int direction, int radius, Board board) {
        this.x0 = x;
        this.y0 = y;
        this.coordinateX = x;
        this.coordinateY = y;
        this.radius = radius;
        this.direction = direction;
        this.board = board;
        createSegments();
    }

    private void createSegments() {
        direcFires = new DirecFire[FireLength()];
        boolean last = false;

        int x = (int) coordinateX;
        int y = (int) coordinateY;
        for (int i = 0; i < direcFires.length; i++) {
            if (i == direcFires.length - 1) {
                last = true;
            }
            if (direction == 0) {
                y--;
            }
            if (direction == 1) {
                x++;
            }
            if (direction == 2) {
                y++;
            }
            if (direction == 3) {
                x--;
            }
            direcFires[i] = new DirecFire(x, y, direction, last);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {
        for (DirecFire temp : direcFires) {
            temp.render(screen);
        }
    }

    @Override
    public boolean checkCollide(Entity entity) {
        if (entity instanceof Character) {
            ((Character) entity).killed();
            return false;
        }
        return true;

    }

    public int FireLength() {
        int radius = 0;
        int x = (int) coordinateX;
        int y = (int) coordinateY;
        while (radius < this.radius) {
            if (direction == 0) {
                y--;
            }
            if (direction == 1) {
                x++;
            }
            if (direction == 2) {
                y++;
            }
            if (direction == 3) {
                x--;
            }
            Entity a = board.getEntity(x, y, null);

            if(a instanceof Character) {
                radius++;
            }

            if(a.checkCollide(this) == false) {
                break;
            }
            radius++;
        }
        return radius;
    }

    public DirecFire fireSegmentAt(int x, int y) {
        for (DirecFire temp : direcFires) {
            if (temp.getX() == x && temp.getY() == y) {
                return temp;
            }
        }
        return null;
    }
}
