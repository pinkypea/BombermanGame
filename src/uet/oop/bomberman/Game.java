package uet.oop.bomberman;

import uet.oop.bomberman.KeyBoard.Keyboard;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.gui.Frame;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas {

    public static final int TILES_SIZE = 16,
            WIDTH = TILES_SIZE * (31 / 2),
            HEIGHT = 13 * TILES_SIZE;

    public static int SCALE = 3;

    public static final double VERSION = 3.0;

    public static final String TITLE = "Bomberman" + VERSION;

    public static final int BOMBRATE = 1;
    public static final int BOMBRADIUS = 1;
    public static final double BOMBERSPEED = 1.0;

    public static final int TIME = 200;
    public static final int POINTS = 0;
    public static final int LIVES = 3;

    protected static int SCREENDELAY = 3;

    protected static int bombRate = BOMBRATE;
    protected static int bombRadius = BOMBRADIUS;
    protected static double bomberSpeed = BOMBERSPEED;

    protected int screenDelay = SCREENDELAY;

    private Keyboard input;
    private boolean running = false;
    private boolean paused = true;

    private Board board;
    private Screen screen;
    private Frame frame;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    /**
     * Constructor.
     */
    public Game(Frame frame) {
        this.frame = frame;
        this.frame.setTitle(TITLE);
        this.screen = new Screen(WIDTH, HEIGHT);
        this.input = new Keyboard();
        this.board = new Board(this, input, screen);
        addKeyListener(input);
    }

    private void renderGame() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        screen.clear();
        board.render(screen);
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }

    private void renderScreen() {
        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        screen.clear();
        Graphics g = bs.getDrawGraphics();
        board.drawScreen(g);
        g.dispose();
        bs.show();
    }

    private void update() {
        input.update();
        board.update();
    }

    public void start() {
        running = true;

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0; // nanos, 60 khung hinh moi giay
        double delta = 0;
        int frames = 0;
        int updates = 0;

        requestFocus();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            if (paused) {
                if (screenDelay <= 0) {
                    board.setShow(-1);
                    paused = false;
                }
                renderScreen();
            } else {
                renderGame();
            }
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                frame.setTime(board.subtractTime());
                frame.setPoints(board.getPoints());
                frame.setLives(board.getLives());
                timer += 1000;
                frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
                updates = 0;
                frames = 0;
                if (board.getShow() == 2) {
                    screenDelay--;
                }
            }
        }
    }
    /**
     * function to get bomber speed.
     * @return double speed
     */
    public static double getBomberSpeed() {
        return bomberSpeed;
    }

    /**
     * function to get bomb rate (so luong bom dat duoc trong 1 luc).
     * @return int bomb rate
     */
    public static int getBombRate() {
        return bombRate;
    }

    /**
     * function to get radius of bomb flame.
     * @return int radius
     */
    public static int getBombRadius() {
        return bombRadius;
    }

    /**
     * function to up speed of bomber.
     * @param i double speed
     */
    public static void addBomberSpeed(double i) {
        bomberSpeed += i;
    }

    /**
     * function to increase radius of bomb.
     * @param i int i
     */
    public static void addBombRadius(int i) {
        bombRadius += i;

    }

    /**
     * function to increase number of bombs in time.
     * @param i int number
     */
    public static void addBombRate(int i) {
        bombRate += i;
    }

    /**
     * function to reset screen delay.
     */
    public void resetScreenDelay() {
        screenDelay = SCREENDELAY;
    }

    /**
     * getter board.
     * @return board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * function to check if game is paused.
     * @return boolean true or false
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * function to pause game.
     */
    public void pause() {
        paused = true;
    }

}
