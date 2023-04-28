package uet.oop.bomberman.Entities.Character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Entities.Bomb.Bomb;
import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Entities.Tile.Brick;
import uet.oop.bomberman.Entities.Tile.Item.Item;
import uet.oop.bomberman.Entities.Tile.Item.Portal;
import uet.oop.bomberman.Graphics.Sprite;
import uet.oop.bomberman.Sound.Sound;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Bomber extends MovingObj {

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
    }

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

    public void addBomb(Bomb bomb) {
        boolean check = true;
        for (Bomb temp : bombs) {
            if (temp.getX() == bomb.getX() && temp.getY() == bomb.getY()) {
                check = false;
                break;
            }
        }
        if (check) {
            bombs.add(bomb);
            Sound.play("BOM_SET");
        }
    }

    public void placeBomb() {
        if (BombermanGame.keyBoard.space && bombs.size() < Board.bombCount && countDownBomb <= 0)
            if (!(BombermanGame.board.getEntity(xBomb(), yBomb()) instanceof Brick)) {
                Bomb bomb = new Bomb(xBomb(), yBomb(), false, Sprite.bomb.getFxImage());
                addBomb(bomb);
                countDownBomb = 400 - ((speed - 0.0035) * 1000 * 50);
                CountDownBomb();
                System.out.println(speed);
            }
    }

    public void CountDownBomb() {
        countDownBomb--;

    }


    public void removeBombAt(double x, double y) {
        for (int i = 0; i < bombs.size(); i++) {
            Bomb temp = bombs.get(i);
            if (temp.getX() == x && temp.getY() == y) {
                bombs.remove(i);
                Board.map[(int) temp.getY()][(int) temp.getX()] = ' ';
                break;
            }
        }
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

    public void movingPlayer() {
        if (BombermanGame.keyBoard.left) {
            moveLeft();
            checkToMapMoveLeft();
        } else if (BombermanGame.keyBoard.right) {
            moveRight();
            checkToMapMoveRight();
        } else if (BombermanGame.keyBoard.down) {
            moveDown();
            checkToMapMoveDown();
        } else if (BombermanGame.keyBoard.up) {
            moveUp();
            checkToMapMoveUp();
        }
    }

    public int xBomb() {
        if (this.x == (int) this.x) return (int) this.x;
        double difference = this.x - (int) this.x;
        return (difference >= 0.64) ? (int) this.x + 1 : (int) this.x;

    }

    public int yBomb() {
        if (this.y == (int) this.y) return (int) this.y;
        double difference = this.y - (int) this.y;
        return (difference >= 0.64) ? (int) this.y + 1 : (int) this.y;
    }

    public void checkToMapMoveRight() {
        double widthFrameNow = 24;
        if (right < animate) {
            widthFrameNow = 20.0;
        } else if (right < 2 * animate) {
            widthFrameNow = 22.0;
        }

        double distance = widthFrameNow / (double) Sprite.SCALED_SIZE;
        int xPos = (int) (x + speed);
        int xPos2 = (int) (x + speed + distance);

        int yPos = (int) y;
        int yPos2 = (int) (y + 1);

        if (xPos >= 0 && xPos2 < 31 && yPos >= 0 && yPos2 < 13) {
            Entity temp1 = BombermanGame.board.getEntity(xPos2, yPos);
            Entity temp2 = BombermanGame.board.getEntity(xPos2, yPos2);
            boolean check1, check2, kt1, kt2;
            if (!Board.wallPass) {
                check1 = Board.map[yPos][xPos2] == '#' || temp1 instanceof Brick || Board.map[yPos][xPos2] == 'B';
                check2 = Board.map[yPos2][xPos2] == '#' || temp2 instanceof Brick || Board.map[yPos2][xPos2] == 'B';
                kt1 = Board.map[(int) y][xPos2] != ' ';
                kt2 = Board.map[(int) (y + 1)][xPos2] != ' ';
            } else {
                check1 = Board.map[yPos][xPos2] == '#' || Board.map[yPos][xPos2] == 'B';
                check2 = Board.map[yPos2][xPos2] == '#' || Board.map[yPos2][xPos2] == 'B';
                kt1 = Board.map[(int) y][xPos2] != ' ' && !(BombermanGame.board.getEntity(xPos2, (int) y) instanceof Brick);
                kt2 = Board.map[(int) (y + 1)][xPos2] != ' ' && !(BombermanGame.board.getEntity(xPos2, (int) (y + 1)) instanceof Brick);
            }
            if (check1 || check2) {
                //if (Board.map[yPos][xPos2] != ' ' || Board.map[yPos2][xPos2] != ' ') {
                if (kt1) {
                    if (y == (int) y) {
                        this.x = (int) (xPos2 - distance);
                    } else {
                        if (this.y - (int) y >= 0.7) {
                            this.y = (int) y + 1;
                        } else {
                            this.x = (int) (xPos2 - distance);
                        }
                    }
                } else if (kt2) {
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
            Entity temp1 = BombermanGame.board.getEntity(xPos, yPos);
            Entity temp2 = BombermanGame.board.getEntity(xPos, yPos2);
            boolean check1, check2, kt1, kt2;
            if (!Board.wallPass) {
                check1 = Board.map[yPos][xPos] == '#' || temp1 instanceof Brick || Board.map[yPos][xPos] == 'B';
                check2 = Board.map[yPos2][xPos] == '#' || temp2 instanceof Brick || Board.map[yPos2][xPos] == 'B';
                kt1 = Board.map[(int) y][xPos] != ' ';
                kt2 = Board.map[(int) (y + 1)][xPos] != ' ';
            } else {
                check1 = Board.map[yPos][xPos] == '#' || Board.map[yPos][xPos] == 'B';
                check2 = Board.map[yPos2][xPos] == '#' || Board.map[yPos2][xPos] == 'B';
                kt1 = Board.map[(int) y][xPos] != ' ' && !(BombermanGame.board.getEntity(xPos, (int) y) instanceof Brick);
                kt2 = Board.map[(int) (y + 1)][xPos] != ' ' && !(BombermanGame.board.getEntity(xPos, (int) (y + 1)) instanceof Brick);
            }
            if (check1 || check2) {
                if (kt1) {
                    if (this.y == (int) y) {
                        this.x = xPos + 1;
                    } else {
                        if (this.y - (int) y >= 0.7) {
                            this.y = (int) y + 1;
                        } else {
                            this.x = xPos + 1;
                        }
                    }
                } else if (kt2) {
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
        double widthFrameNow = 24;

        double distance = widthFrameNow / (double) Sprite.SCALED_SIZE;
        int xPos = (int) x;
        int xPos2 = (int) (x + distance);

        int yPos = (int) (y);
        int yPos2 = (int) (y - speed);

        if (xPos >= 0 && xPos2 < 31 && yPos >= 0 && yPos2 < 13) {
            Entity temp1 = BombermanGame.board.getEntity(xPos, yPos2);
            Entity temp2 = BombermanGame.board.getEntity(xPos2, yPos2);
            boolean check1, check2, kt1, kt2;
            if (!Board.wallPass) {
                check1 = Board.map[yPos2][xPos] == '#' || temp1 instanceof Brick || Board.map[yPos2][xPos] == 'B';
                check2 = Board.map[yPos2][xPos2] == '#' || temp2 instanceof Brick || Board.map[yPos2][xPos2] == 'B';
                kt1 = Board.map[yPos2][xPos] != ' ';
                kt2 = Board.map[yPos2][xPos2] != ' ';
            } else {
                check1 = Board.map[yPos2][xPos] == '#' || Board.map[yPos2][xPos] == 'B';
                check2 = Board.map[yPos2][xPos2] == '#' || Board.map[yPos2][xPos2] == 'B';
                kt1 = Board.map[yPos2][xPos] != ' ' && !(BombermanGame.board.getEntity(xPos, yPos) instanceof Brick);
                kt2 = Board.map[yPos2][xPos2] != ' ' && !(BombermanGame.board.getEntity(xPos2, yPos2) instanceof Brick);
            }
            if (check1 || check2) {
                if (kt1) {
                    if (this.x - (int) x >= 0.7) {
                        this.x = (int) x + 1;
                    } else {
                        this.y = yPos2 + 1;
                    }
                } else if (kt2) {
                    if (this.x - (int) x <= 0.45) {
                        this.x = (int) (x + 1 - distance);
                    } else {
                        this.y = yPos2 + 1;
                    }
                }

            }
        }
    }

    public void checkToMapMoveDown() {
        double widthFrameNow = 24.0;
        double distance = widthFrameNow / (double) Sprite.SCALED_SIZE;
        int xPos = (int) x;
        int xPos2 = (int) (x + distance);

        int yPos = (int) (y + speed);
        int yPos2 = (int) (y + 1 + speed);

        if (xPos >= 0 && xPos2 < 31 && yPos >= 0 && yPos2 < 13) {
            Entity temp1 = BombermanGame.board.getEntity(xPos, yPos2);
            Entity temp2 = BombermanGame.board.getEntity(xPos2, yPos2);
            boolean check1, check2, kt1, kt2;
            if (!Board.wallPass) {
                check1 = Board.map[yPos2][xPos] == '#' || temp1 instanceof Brick || Board.map[yPos2][xPos] == 'B';
                check2 = Board.map[yPos2][xPos2] == '#' || temp2 instanceof Brick || Board.map[yPos2][xPos2] == 'B';
                kt1 = Board.map[(int) (y + 1)][xPos] != ' ';
                kt2 = Board.map[(int) (y + 1)][xPos2] != ' ';
            } else {
                check1 = Board.map[yPos2][xPos] == '#' || Board.map[yPos2][xPos] == 'B';
                check2 = Board.map[yPos2][xPos2] == '#' || Board.map[yPos2][xPos2] == 'B';
                kt1 = Board.map[(int) (y + 1)][xPos] != ' ' && !(BombermanGame.board.getEntity(xPos, (int) (y + 1)) instanceof Brick);
                kt2 = Board.map[(int) (y + 1)][xPos2] != ' ' && !(BombermanGame.board.getEntity(xPos2, (int) (y + 1)) instanceof Brick);
            }
            if (check1 || check2) {
                if (kt1) {
                    if (this.x - (int) x >= 0.7) {
                        this.x = (int) x + 1;
                    } else {
                        this.y = yPos;
                    }
                } else if (kt2) {
                    if (this.x - (int) x <= 0.45) {
                        this.x = (int) ((int) x + 1 - distance);
                    } else {
                        this.y = yPos;
                    }
                }
            }
        }
    }
    // ĐÃ THÊM
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

                    Sound.play("endgame3");
                } else {
                    Sound.play("AA126_11");
                    setAlive(false);
                }
            }
        }
    }
// ĐÃ THÊM

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void updateStatus() {
        Board.file.delete();
        final String FILENAME = "res/levels/save.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true))) {
            int left = BombermanGame.board.getLeft();
            bw.write(BombermanGame.board.getLevel() + " " + left + " " + Board.scorePrevious);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isDie() {
        return die;
    }

    public boolean isWin() {
        return win;
    }

    public void collideWithItem(Item obj) {
        if (alive) {
            HashSet<String> maskPlayer1 = getMask(this);
            HashSet<String> maskPlayer2 = getMask(obj);
            maskPlayer1.retainAll(maskPlayer2);
            if (obj instanceof Portal) {
                Portal other = (Portal) obj;
                if (other.getActivated()) {
                    if (maskPlayer1.size() > 300) {
                        if (BombermanGame.board.getLevel() == Board.MAX_LEVEL) {
                            win = true;
                        } else {
                            Sound.play("CRYST_UP");
                            Board.countDownTime = 181 * 60;
                            int newLevel = BombermanGame.board.getLevel() + 1;
                            Board.scorePrevious += Board.score;
                            BombermanGame.board.changeLevel(newLevel);
                            BombermanGame.board.setLevel(newLevel);
                            updateStatus();
                        }
                        try {
                            TimeUnit.SECONDS.sleep(1);
                            this.setImg(Sprite.player_right.getFxImage());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            } else {
                if (!obj.getActivated()) {
                    if (maskPlayer1.size() > 0) {
                        obj.setActivated(true);
                        Sound.play("Item");
                    }
                }
            }
        }
    }

    public void collide() {
        for (int i = 0; i < BombermanGame.board.getEntities().size(); i++) {
            if (BombermanGame.board.getEntities().get(i) instanceof Item) {
                collideWithItem((Item) BombermanGame.board.getEntities().get(i));
            }
        }
        for (int i = 0; i < BombermanGame.board.getEnemies().size(); i++) {
            collideToDie(BombermanGame.board.getEnemies().get(i));
        }
    }

    public void render(GraphicsContext gc) {
        if (!noDie) {
            super.render(gc);
        } else {
            if (Board.countDownTime % 4 == 0 || Board.countDownTime % 4 == 1) {
                super.render(gc);
            }
        }
        for (Bomb bomb : bombs) {
            bomb.render(gc);
        }
    }

    @Override
    public void update() {
        collide();
        if (noDie) {
            timeNoDie--;
            if (timeNoDie == 0) {
                noDie = false;
                timeNoDie = 5 * 60;
            }
        }
        for (Bomb bomb : bombs) {
            updateWallFromBomb(bomb);
            bomb.update();
        }
        if (alive) {
            movingPlayer();
            placeBomb();
            CountDownBomb();
        } else {
            if (time < 1) {
                this.setImg(imgFrameDie[0]);
                time++;
            } else if (time < 2) {
                this.setImg(imgFrameDie[1]);
                time++;
            } else if (time < 3) {
                this.setImg(imgFrameDie[2]);
                time++;
            } else if (time < 4) {
                this.setImg(null);
                time++;
            } else {
                if (health == 0) {
                    die = true;
                    BombermanGame.board.removeEntityAt(this.x, this.y);
                    try {
                        TimeUnit.SECONDS.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    this.x = 1;
                    this.y = 1;
                    noDie = true;
                    this.setImg(Sprite.player_right.getFxImage());
                    alive = true;
                    time = 0;
                }
            }
        }
    }

    public void updateWallFromBomb(Bomb bomb) {
        if (alive) {
            HashSet<String> maskBomb = getMask(bomb);
            HashSet<String> maskPlayer = getMask(this);
            maskBomb.retainAll(maskPlayer);
            if (maskBomb.size() == 0 && !Board.bombPass) {
                Board.map[(int) bomb.getY()][(int) bomb.getX()] = 'B';
            }
        }
    }

}
