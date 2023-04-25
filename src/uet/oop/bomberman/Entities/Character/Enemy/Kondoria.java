package uet.oop.bomberman.Entities.Character.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Graphics.Sprite;

public class Kondoria extends Enemy {
    public Kondoria(int x, int y, Image img, double speed) {
        super(x, y, img, speed);
        setFrameRight();
        setFrameLeft();
        setFrameDie();
    }

    private void setFrameRight() {
        Image right0 = Sprite.kondoria_right1.getFxImage();
        Image right1 = Sprite.kondoria_right2.getFxImage();
        Image right2 = Sprite.kondoria_right3.getFxImage();
        this.imgFrameRight = new Image[3];
        imgFrameRight[0] = right0;
        imgFrameRight[1] = right1;
        imgFrameRight[2] = right2;
    }

    private void setFrameLeft() {
        Image left0 = Sprite.kondoria_left1.getFxImage();
        Image left1 = Sprite.kondoria_left2.getFxImage();
        Image left2 = Sprite.kondoria_left3.getFxImage();
        this.imgFrameLeft = new Image[3];
        imgFrameLeft[0] = left0;
        imgFrameLeft[1] = left1;
        imgFrameLeft[2] = left2;
    }

    @Override
    public int chooseDirection() {
        return AI.chooseDirectionGoThroughBrick(Board.getPlayer(), this, currentDirection);
    }


    @Override
    public void movingPlayer() {
        currentDirection = chooseDirection();
        switch (currentDirection) {
            case 0:
                moveUp();
                break;
            case 1:
                moveRight();
                break;
            case 2:
                moveDown();
                break;
            case 3:
                moveLeft();
                break;
        }
    }
}
