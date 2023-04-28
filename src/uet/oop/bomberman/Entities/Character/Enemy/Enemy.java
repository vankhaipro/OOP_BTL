package uet.oop.bomberman.Entities.Character.Enemy;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Entities.Character.AI.AI;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.Character.MovingObj;
import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Graphics.Sprite;

import java.util.HashSet;


public abstract class Enemy extends MovingObj {
    protected Image[] imgFrameDie;
    protected int currentDirection = 1;
    protected int time = 0;
    protected AI AI = new AI();
    boolean alive = true;

    public Enemy(int x, int y, Image img, double speed) {
        super(x, y, img, speed);
    }

    public abstract int chooseDirection();
    public void checkToMapMoveRight() {

        double distance = 1;
        int xPos = (int) (x + speed);
        int xPos2 = (int) (x + speed + distance);

        int yPos = (int) y;
        int yPos2 = (int) (y + 1);

        if (xPos >= 0 && xPos2 < 31 && yPos >= 0 && yPos2 < 13) {
            if (Board.map[yPos][xPos2] != ' ' || Board.map[yPos2][xPos2] != ' ') {
                if (Board.map[(int) y][xPos2] != ' ') {
                    if (y == (int)y) {
                        this.x = (int) (xPos2 - distance);
                    } else {
                        if (this.y - (int) y >= 0.7) {
                            this.y = (int) y + 1;
                        } else {
                            this.x = (int) (xPos2 - distance);
                        }
                    }
                } else if (Board.map[(int) (y + 1)][xPos2] != 0) {
                    if (this.y - (int) y <= 0.3) {
                        this.y = (int) y;
                    } else {
                        this.x = (int) (xPos2 - distance);
                    }
                }
            }
        }
    }

    public void checkToMapMoveLeft() {
        int xPos = (int) (x - speed);

        int yPos = (int) y;
        int yPos2 = (int) (y + 1);

        if (xPos >= 0 && xPos < 31 && yPos >= 0 && yPos2 < 13) {
            if (Board.map[yPos][xPos] != ' ' || Board.map[yPos2][xPos] != ' ') {
                if (Board.map[(int) y][xPos] != ' ') {
                    if (this.y == (int) y) {
                        this.x = xPos + 1;
                    } else {
                        if (this.y - (int) y >= 0.7) {
                            this.y = (int) y + 1;
                        } else {
                            this.x = xPos + 1;
                        }
                    }
                } else if (Board.map[(int) (y + 1)][xPos] != ' ') {
                    if (this.y - (int) y <= 0.3) {
                        this.y = (int) y;
                    } else {
                        this.x = xPos + 1;
                    }
                }
            }
        }
    }

    public void checkToMapMoveUp() {

        double distance = 1;
        int xPos = (int) (x);
        int xPos2 = (int) (x + distance);

        int yPos = (int) (y);
        int yPos2 = (int) (y - speed);

        if (xPos >= 0 && xPos2 < 31 && yPos >= 0 && yPos2 < 13) {
            if (Board.map[yPos2][xPos] != ' ' || Board.map[yPos2][xPos2] != ' ') {
                if (Board.map[yPos2][xPos] != ' ') {
                    if (this.x - (int) x >= 0.7) {
                        this.x = (int) x + 1;
                    } else {
                        this.y = yPos2 + 1;
                    }
                } else if (Board.map[yPos2][xPos2] != ' ') {
                    if (this.x - (int) x <= 0.45) {
                        this.x = (int) ((int) x + 1 - distance);
                    } else {
                        this.y = yPos2 + 1;
                    }
                }

            }
        }
    }

    public void checkToMapMoveDown() {
        double distance = 1;
        int xPos = (int) x;
        int xPos2 = (int) (x + distance);

        int yPos = (int) (y + speed);
        int yPos2 = (int) (y + 1 + speed);

        if (xPos >= 0 && xPos2 < 31 && yPos >= 0 && yPos2 < 13) {
            if (Board.map[yPos2][xPos] != ' ' || Board.map[yPos2][xPos2] != ' ') {
                if (Board.map[(int) (y + 1)][xPos] != ' ') {
                    if (this.x - (int) x >= 0.7) {
                        this.x = (int) x + 1;
                    } else {
                        this.y = yPos;
                    }
                } else if (Board.map[(int) (y + 1)][xPos2] != ' ') {
                    if (this.x - (int) x <= 0.45) {
                        this.x = (int) (x + 1 - distance);
                    } else {
                        this.y = yPos;
                    }
                }
            }
        }
    }
    protected void setFrameDie() {
        Image die0 = Sprite.balloom_dead.getFxImage();
        Image die1 = Sprite.mob_dead1.getFxImage();
        Image die2 = Sprite.mob_dead2.getFxImage();
        Image die3 = Sprite.mob_dead3.getFxImage();
        this.imgFrameDie = new Image[4];
        imgFrameDie[0] = die0;
        imgFrameDie[1] = die1;
        imgFrameDie[2] = die2;
        imgFrameDie[3] = die3;
    }
    @Override
    public void moveUp() {
        if (up < animate) {
            up++;
        } else if (up < 2 * animate) {
            up++;
        } else {
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
            down++;
        } else if (down < 2 * animate) {
            down++;
        } else {
            down++;
            if (down == 3 * animate) {
                down = 0;
            }
        }
        this.y += speed;
    }

    public void movingPlayer() {
        currentDirection = chooseDirection();
        switch (currentDirection) {
            case 0 : {
                moveUp();
                checkToMapMoveUp();
                break;
            }
            case 1 : {
                moveRight();
                checkToMapMoveRight();
                break;
            }
            case 2 : {
                moveDown();
                checkToMapMoveDown();
                break;
            }
            case 3 : {
                moveLeft();
                checkToMapMoveLeft();
                break;
            }
        }
    }

    public void collideWithExplosion(Entity obj) {
        if (alive) {
            HashSet<String> maskPlayer1 = getMask(this);
            HashSet<String> maskPlayer2 = getMask(obj);
            maskPlayer1.retainAll(maskPlayer2);
            if (maskPlayer1.size() > 0) {
                alive = false;
            }
        }
    }

    public void update() {
        if (alive) {
            movingPlayer();
        } else {
            enemyDie();
        }
    }
    public void enemyDie() {
        if (time < 10) {
            this.setImg(imgFrameDie[0]);
            time++;
        } else if (time < 20) {
            this.setImg(imgFrameDie[1]);
            time++;
        } else if (time < 30) {
            this.setImg(imgFrameDie[2]);
            time++;
        } else if (time < 40) {
            this.setImg(imgFrameDie[3]);
            Board.score += 100;
            BombermanGame.board.removeEnemyAt(this.x, this.y);
        }
    }

}
