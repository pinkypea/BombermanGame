package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public GamePanel gamePanel;
    private JPanel jPanel;
    private InfoPanel infoPanel;
    private Game game;

    /**
     * Constructor.
     */
    public Frame() {
        this.jPanel = new JPanel(new BorderLayout());
        this.gamePanel = new GamePanel(this);
        this.infoPanel = new InfoPanel(this.gamePanel.getGame());
        this.jPanel.add(this.infoPanel, BorderLayout.PAGE_START);
        this.jPanel.add(this.gamePanel, BorderLayout.PAGE_END);
        this.game = gamePanel.getGame();

        add(jPanel);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        game.start();
    }

    public void setTime(int time) {
        infoPanel.setTime(time);
    }

    public void setPoints(int points) {
        infoPanel.setPoints(points);
    }

    public void setLives(int lives) {
        infoPanel.setLives(lives);
    }


}
