package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    private JLabel timeLabel;
    private JLabel pointsLabel;
    private JLabel livesLabel;

    /**
     * Constructor.
     */
    public InfoPanel(Game game) {
        setLayout(new GridLayout());
        timeLabel = new JLabel("Time: " + game.getBoard().getTime());
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        pointsLabel = new JLabel("Points: " + game.getBoard().getPoints());
        pointsLabel.setForeground(Color.YELLOW);
        pointsLabel.setHorizontalAlignment(JLabel.CENTER);
        livesLabel = new JLabel("Lives: " + game.getBoard().getLives());
        livesLabel.setForeground(Color.RED);
        livesLabel.setHorizontalAlignment(JLabel.CENTER);
        add(timeLabel);
        add(pointsLabel);
        add(livesLabel);
        setBackground(Color.black);
        setPreferredSize(new Dimension(0, 40));

    }

    public void setTime(int time) {
        this.timeLabel.setText("Time: " + time);
    }

    public void setLives(int lives) {
        this.livesLabel.setText("Lives: " + lives);
    }

    public void setPoints(int points) {
        this.pointsLabel.setText("Score: " + points);
    }

}
