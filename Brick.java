import acm.graphics.GImage;

import acm.program.GraphicsProgram;

public class Brick {

    private int numBrick = 0; //Number of bricks

    public int getNumBrick() {
        return numBrick;
    }

    public void decreaseNumBrick() {
        this.numBrick--;
    }

    public void setBricks(GraphicsProgram program) {
        for (int i = 0; i < Constans.NBRICK_ROWS; i++) {
            for (int y = 0; y < Constans.NBRICKS_PER_ROW; y++) {
                GImage brick = new GImage("курица.jpg");
                typeOfBrick(i,brick);
                brick.scale((Constans.BRICK_WIDTH/brick.getWidth()), (Constans.BRICK_HEIGHT/brick.getHeight()));
                double startRow = (Constans.APPLICATION_WIDTH - Constans.BRICK_WIDTH * Constans.NBRICKS_PER_ROW - Constans.BRICK_SEP * (Constans.NBRICKS_PER_ROW - 1)) / 2;
                double startX = startRow + Constans.BRICK_WIDTH * y + Constans.BRICK_SEP * y;
                double startY = Constans.BRICK_Y_OFFSET + Constans.BRICK_HEIGHT * i + Constans.BRICK_SEP * i;
                brick.setLocation(startX, startY);
                program.add(brick);
                numBrick++;
            }
        }
    }

    private void typeOfBrick(int i, GImage brick) { //pictures changes at each two lines
        if (i < 2) {
            brick.setImage("курица.jpg");
        } else if (i < 4) {
            brick.setImage("колбасень.jpg");
        } else if (i < 6) {
            brick.setImage("мясо.jpg");
        } else if (i < 8) {
            brick.setImage("гамбургер.jpg");
        } else {
            brick.setImage("сосиски.jpg");
        }
    }
}
