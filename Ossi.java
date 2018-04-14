import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Ossi extends GraphicsProgram {
    /**
     * Dimensions of game board
     */
    private static final int WIDTH = Constans.APPLICATION_WIDTH;
    private static final int HEIGHT = Constans.APPLICATION_HEIGHT;

    private Brick brick = new Brick(); //	system of bricks at the top of app
    private Paddle paddle = new Paddle(); //paddle at the bottom of the screen
    private Ball ball; //ball is face of little dog


    public void run() {
        setup(); //set initial objects to the screen

        for (int i = 0; i < Constans.NTURNS; i++) { //game begin when user click button at the keyboard
            while (!gameOver()) { //condition to continue the game
                if ((ball != null) && (ball.isBallVisible()) && (brick != null)) {
                    ball.moveBall();
                    checkForCollisions();
                }
                pause(Constans.DELAY);
            }
        }
        finish(); //result of the game
    }

    public void setup() {
        setBackground(Color.white);
        brick.setBricks(this); //set bricks at the top part of the screen
        paddle.setPaddle(this); //set paddle with MouseListeners at the bottom of the screen
        addKeyListeners(); //set ball with key event
    }

    private boolean gameOver() {
        boolean condition = ((ball != null) && ((HEIGHT - ball.getY() - 2 * Constans.BALL_RADIUS) < ball.getVy())) || (brick.getNumBrick() == 0);

        if ((ball != null) && (condition)) {
            ball.setBallVisible(false);
            remove(ball.getBall());
            ball = null;
        }
        return condition;     //if all bricks are remove or if ball touch the bottom wall
    }

    public void mousePressed(MouseEvent e) {
        paddle.mousePressed(e, this);
    }

    public void mouseDragged(MouseEvent e) {
        paddle.mouseDragged(e);
    }

    public void keyPressed(KeyEvent e) {
        if ((ball != null) && (ball.isBallVisible())) { //check for existing another ball. Is ball is exist, second ball is not add
            return;
        }
        if ((ball == null) && (brick != null)) {
            ball = new Ball();
            add(ball.getBall());
            ball.setBallVisible(true);
        }
    }

    public void checkForCollisions() {
        ball.choiseObject(this, brick, paddle);
    }

    public void finish() {
        if (brick.getNumBrick() == 0) { //if you win
            removeAll();
            GImage vict = new GImage("фон1.jpg");
            add(vict, 0, 0);
            vict.scale((WIDTH / vict.getWidth()), (HEIGHT / vict.getHeight()));
            GLabel victory = new GLabel("You win");
            victory.setLocation((WIDTH - victory.getWidth()) / 2, (HEIGHT - victory.getHeight()) / 2);
            victory.setColor(Color.ORANGE);
            add(victory);
        } else { //if you lose
            removeAll();
            GLabel lose = new GLabel("You lose");
            lose.setLocation((Constans.APPLICATION_WIDTH - lose.getWidth()) / 2, (Constans.APPLICATION_HEIGHT - lose.getHeight()) / 2);
            add(lose);
        }
    }
}
