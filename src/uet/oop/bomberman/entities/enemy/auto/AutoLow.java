package uet.oop.bomberman.entities.enemy.auto;

public class AutoLow extends Auto {
    @Override
    public int calculateDirection() {
        return random.nextInt(4);
    }
}
