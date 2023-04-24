package uet.oop.bomberman.KeyBoard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    private boolean[] keys = new boolean[120];
    public boolean down, up, right, left, space;

    public void update() {
        down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
        left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        space = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_X];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}
