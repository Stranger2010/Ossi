import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Ball {

    /**
     * instance variables
     */
    private RandomGenerator rgen = RandomGenerator.getInstance();
    private double vx = 0.0; //coordinates for first loop
    private double vy = 0.0; //coordinates for first loop
    private GImage ball;
    private boolean ballVisible = false;


    public Ball() {
        ball = new GImage("аусси.jpg");
        ball.scale(((Constans.BALL_RADIUS * 2) / ball.getWidth()), ((Constans.BALL_RADIUS * 2) / ball.getHeight()));
        ball.setLocation(Constans.APPLICATION_WIDTH / 2 - Constans.BALL_RADIUS, Constans.APPLICATION_HEIGHT / 2 - Constans.BALL_RADIUS);

    }

    public double getVy() {
        return vy;
    }

    public GImage getBall() {
        return ball;
    }

    public boolean isBallVisible() {
        return ballVisible;
    }

    public void setBallVisible(boolean ballVisible) {
        this.ballVisible = ballVisible;
    }

    public double getY() {
        return ball.getY();
    }

    public void moveBall() {

        if (vy == 0.0) { //checking first loop
            vy = rgen.nextDouble(2.0, 3.0); // speed of ball, x coordinate is random
            if (rgen.nextBoolean(0.5)) vy = -vy; // speed of ball, y coordinate is constant
            vx = rgen.nextDouble(2.0, 3.0); // speed of ball, x coordinate is random
            if (rgen.nextBoolean(0.5)) vx = -vx;
        }

        if (((ball.getX() + 2 * Constans.BALL_RADIUS + vx) < Constans.APPLICATION_WIDTH) && (ball.getX() + vx > 0)) {
            ball.move(vx, vy); //movement of the ball to wall

        }
        if (((Constans.APPLICATION_WIDTH - ball.getX() - 2 * Constans.BALL_RADIUS) < vx) || (ball.getX() <-vx)) {
            vx = -vx;  // The ball bounces off left or right walls
            ball.move(vx, vy);
        }

        if (ball.getY() < -vy) {
            vy = -vy; // The ball bounces off left or right walls
            ball.move(vx, vy);// The ball bounces off the top
        }
    }

    public GObject getCollidingObject(GraphicsProgram program) {
        GObject collObj = program.getElementAt(ball.getX() + Constans.BALL_RADIUS,ball.getY()+ Constans.BALL_RADIUS + Math.signum(vy)*Constans.BALL_RADIUS + vy);
        if (collObj != null) {
            return collObj;
        } else {
            collObj = program.getElementAt(ball.getX() + Constans.BALL_RADIUS + Math.signum(vx) * Constans.BALL_RADIUS + vx, ball.getY() + Constans.BALL_RADIUS);
            if (collObj != null) {
                return collObj;
            } return null;
        }
    }

    public void choiseObject(GraphicsProgram program, Brick brick, Paddle paddle) {
        GObject collider = getCollidingObject(program);
        if (collider != null) {
            if (collider.equals(paddle.getPaddle())) {
                vy = -vy ;

                ball.move(vx, vy);
            } else if ( (-(collider.getY()+Constans.PADDLE_HEIGHT-ball.getY())<-vy) || ((ball.getY()- collider.getY()) <vy) ) {
                vy = -vy ;

                program.remove(collider);
                ball.move(vx, vy);
                brick.decreaseNumBrick();
                if (brick.getNumBrick() == 0) {
                    return;
                }

            } else {
                vx = -vx ;
                program.remove(collider);
                ball.move(vx, vy);
                brick.decreaseNumBrick();
                if (brick.getNumBrick() == 0) {
                    return;
                }
            }
        }return;
    }
}
