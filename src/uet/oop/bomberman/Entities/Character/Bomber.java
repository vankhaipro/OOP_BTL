package uet.oop.bomberman.Entities.Character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Entities.Bomb.Bomb;
import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Graphics.Sprite;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Bomber extends MovingObj  {

    private final int animate = 4;
    Image[] imgFrameUp;
    Image[] imgFrameDown;
    Image[] imgFrameDie;
    double countDownBomb = 0;
    private int left = 0;
    private int right = 0;
    private int up = 0;
    private int down = 0;
    private int time = 0; // time to die
    private int health;
    private boolean win = false;
    private boolean die = false;
    private boolean noDie = false;
    private int timeNoDie = 5 * 60;
    private List<Bomb> bombs = new ArrayList<>();

    public Bomber(int x, int y, Image img, double speed) {
        super(x, y, img, speed);
        setFrameRight();
        setFrameLeft();
        setFrameUp();
        setFrameDown();
        setFrameDie();
    }


    public void setAlive(boolean alive) {
        this.alive = alive;
    } // đã thêm

    private void setFrameRight() {
        Image right0 = Sprite.player_right.getFxImage();
        Image right1 = Sprite.player_right_1.getFxImage();
        Image right2 = Sprite.player_right_2.getFxImage();
        this.imgFrameRight = new Image[3];
        imgFrameRight[0] = right0;
        imgFrameRight[1] = right1;
        imgFrameRight[2] = right2;
    }

    private void setFrameLeft() {
        Image left0 = Sprite.player_left.getFxImage();
        Image left1 = Sprite.player_left_1.getFxImage();
        Image left2 = Sprite.player_left_2.getFxImage();
        this.imgFrameLeft = new Image[3];
        imgFrameLeft[0] = left0;
        imgFrameLeft[1] = left1;
        imgFrameLeft[2] = left2;
    }

    private void setFrameUp() {
        Image up0 = Sprite.player_up.getFxImage();
        Image up1 = Sprite.player_up_1.getFxImage();
        Image up2 = Sprite.player_up_2.getFxImage();
        this.imgFrameUp = new Image[3];
        imgFrameUp[0] = up0;
        imgFrameUp[1] = up1;
        imgFrameUp[2] = up2;
    }

    private void setFrameDown() {
        Image down0 = Sprite.player_down.getFxImage();
        Image down1 = Sprite.player_down_1.getFxImage();
        Image down2 = Sprite.player_down_2.getFxImage();
        this.imgFrameDown = new Image[3];
        imgFrameDown[0] = down0;
        imgFrameDown[1] = down1;
        imgFrameDown[2] = down2;
    }

    private void setFrameDie() {
        Image die0 = Sprite.player_dead1.getFxImage();
        Image die1 = Sprite.player_dead2.getFxImage();
        Image die2 = Sprite.player_dead3.getFxImage();
        this.imgFrameDie = new Image[3];
        imgFrameDie[0] = die0;
        imgFrameDie[1] = die1;
        imgFrameDie[2] = die2;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }


    @Override
    public void moveUp() {
        if (up < animate) {
            this.setImg(imgFrameUp[0]);
            up++;
        } else if (up < 2 * animate) {
            this.setImg(imgFrameUp[1]);
            up++;
        } else {
            this.setImg(imgFrameUp[2]);
            up++;
            if (up == 3 * animate) {
                up = 0;
            }
        }
        this.y -= speed;
    }

    @Override
    public void moveDown() {
        if (down < animate) {
            this.setImg(imgFrameDown[0]);
            down++;
        } else if (down < 2 * animate) {
            this.setImg(imgFrameDown[1]);
            down++;
        } else {
            this.setImg(imgFrameDown[2]);
            down++;
            if (down == 3 * animate) {
                down = 0;
            }
        }
        this.y += speed;
    }

    public void movingPlayer(){
        // cần code
    }

    public void updateStatus() {
        // cần code
    }

    public void collideToDie(Entity obj) {
        if (alive && !noDie) {
            HashSet<String> maskPlayer1 = getMask(this);
            HashSet<String> maskPlayer2 = getMask(obj);
            maskPlayer1.retainAll(maskPlayer2);
            if (maskPlayer1.size() > 0) {
                health--;
                updateStatus();
                if (health == 0) {
                    setAlive(false);

                    //Sound.play("endgame3"); // cần code SOUND
                } else {
                    //Sound.play("AA126_11");
                    setAlive(false);
                }
            }
        }
    }


    @Override
    public void update() {

    }
}
