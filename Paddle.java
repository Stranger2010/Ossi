import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

import java.awt.event.MouseEvent;

public class Paddle {

    /**
     * instance variables
     */
    private GObject lastClickedObject;
    private GPoint lastClickPoint;
    private GImage paddle;

    public GImage getPaddle() {
        return paddle;
    }

    public void setPaddle(GraphicsProgram program) { //setPaddle with addMouseListeners - Pressed and Dragged to move paddle on the screen
        double xPaddle = (Constans.APPLICATION_WIDTH - Constans.PADDLE_WIDTH) / 2;
        double yPaddle = Constans.APPLICATION_HEIGHT - Constans.PADDLE_HEIGHT - Constans.PADDLE_Y_OFFSET;
        paddle = new GImage("миска2.jpg");
        paddle.scale((Constans.PADDLE_WIDTH/paddle.getWidth()), (Constans.PADDLE_HEIGHT/paddle.getHeight()));
        paddle.setLocation(xPaddle, yPaddle);
        program.add(paddle);
        program.addMouseListeners();
    }

    public void mousePressed(MouseEvent e, GraphicsProgram program) {
        lastClickPoint = new GPoint(e.getPoint());
        lastClickedObject = program.getElementAt(lastClickPoint);
    }

    public void mouseDragged(MouseEvent e) {
        if (!paddle.equals(lastClickedObject)) {
            return;
        }
        double dx = e.getX() - lastClickPoint.getX();
        lastClickPoint = new GPoint(e.getPoint());
        if (paddle.getX() + dx < 0) {
            dx = -paddle.getX();
        }
        if (paddle.getX() + Constans.PADDLE_WIDTH + dx > Constans.APPLICATION_WIDTH) {
            dx = Constans.APPLICATION_WIDTH - Constans.PADDLE_WIDTH - paddle.getX();
        }
        paddle.move(dx, 0);
    }
}
