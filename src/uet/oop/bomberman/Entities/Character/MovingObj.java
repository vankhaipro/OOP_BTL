package uet.oop.bomberman.Entities.Character;

import javafx.scene.image.Image;
import uet.oop.bomberman.Entities.Entity;

public abstract class MovingObj extends Entity {

    protected final int animate = 5;
    protected Image[] imgFrameRight;
    protected Image[] imgFrameLeft;
    protected double speed;
    protected int left = 0;
    protected int right = 0;
    protected int up = 0;
    protected int down = 0;
    protected boolean alive = true;

    public MovingObj(int x, int y, Image img, double speed) {
        super(x, y, img);
        this.speed = speed;
    }

    public abstract void movingPlayer();

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void update() {
        movingPlayer();
    }

    public abstract void moveUp();

    public abstract void moveDown();

    public void moveLeft() {
        if (left < animate) {
            this.setImg(imgFrameLeft[0]);
            left++;
        } else if (left < 2 * animate) {
            this.setImg(imgFrameLeft[1]);
            left++;
        } else {
            this.setImg(imgFrameLeft[2]);
            left++;
            if (left == 3 * animate) {
                left = 0;
            }
        }
        this.x -= speed;
    }


    public void moveRight() {
        if (right < animate) {
            this.setImg(imgFrameRight[0]);
            right++;
        } else if (right < 2 * animate) {
            this.setImg(imgFrameRight[1]);
            right++;
        } else {
            this.setImg(imgFrameRight[2]);
            right++;
            if (right == 3 * animate) {
                right = 0;
            }
        }
        this.x += speed;
    }

}
