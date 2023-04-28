package uet.oop.bomberman.Level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Entities.Tile.Item.*;

import uet.oop.bomberman.Entities.Tile.Brick;
import uet.oop.bomberman.Entities.Tile.Grass;
import uet.oop.bomberman.Entities.Tile.Wall;
import uet.oop.bomberman.Entities.Character.Enemy.Balloon;
import uet.oop.bomberman.Entities.Character.Enemy.Doll;
import uet.oop.bomberman.Entities.Character.Enemy.Kondoria;
import uet.oop.bomberman.Entities.Character.Enemy.Oneal;
import uet.oop.bomberman.Graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
// Load bản đồ
public class Level {
    private Board boardGame;

    public Level(Board boardGame) {
        this.boardGame = boardGame;
    }

    public void createMapLevel(int level) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("res/levels/Level" + level + ".txt"));
        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                Board.map[row][i] = line.charAt(i);
            }
            row++;
        }

        for (int i = 0; i < Board.HEIGHT; i++) {
            for (int j = 0; j < Board.WIDTH; j++) {
                if (Board.map[i][j] == '#') {
                    Wall object = new Wall(j, i, Sprite.wall.getFxImage());

                    boardGame.addStillObject(object);
                } else {
                    Grass object = new Grass(j, i, Sprite.grass.getFxImage());
                    boardGame.addStillObject(object);
                }
                switch (Board.map[i][j]) {
                    case '*':
                        Brick brick = new Brick(j, i, Sprite.brick.getFxImage());
                        boardGame.addEntity(brick);
                        break;
                    case 's':
                        Brick bricks = new Brick(j, i, Sprite.brick.getFxImage());
                        SpeedItem objectBelow1 = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                        boardGame.addEntity(bricks);
                        bricks.setEntityBelow(objectBelow1);
                        break;
                    case 'b':
                        Brick brickb = new Brick(j, i, Sprite.brick.getFxImage());
                        BombItem objectBelow2 = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                        boardGame.addEntity(brickb);
                        brickb.setEntityBelow(objectBelow2);
                        break;
                    case 'f':
                        Brick object = new Brick(j, i, Sprite.brick.getFxImage());
                        FlameItem objectBelow3 = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                        boardGame.addEntity(object);
                        object.setEntityBelow(objectBelow3);
                        break;
                    case '1':
                        Board.map[i][j] = ' ';
                        Balloon balloon = new Balloon(j, i, Sprite.balloom_right1.getFxImage(), Board.speedOfEnemy);
                        boardGame.addEnemy(balloon);
                        break;
                    case '2':
                        Board.map[i][j] = ' ';
                        Oneal newOneal = new Oneal(j, i, Sprite.oneal_right1.getFxImage(), Board.speedOfEnemy * 1.25);
                        boardGame.addEnemy(newOneal);
                        break;
                    case '3':
                        Board.map[i][j] = ' ';
                        Doll doll = new Doll(j, i, Sprite.doll_right1.getFxImage(), Board.speedOfEnemy * 1.25);
                        boardGame.addEnemy(doll);
                        break;
                    case '4':
                        Board.map[i][j] = ' ';
                        Kondoria kondoria = new Kondoria(j, i, Sprite.kondoria_right1.getFxImage(), Board.speedOfEnemy);
                        boardGame.addEnemy(kondoria);
                        break;
                    case 'x':
                        Brick objectx = new Brick(j, i, Sprite.brick.getFxImage());
                        Portal objectBelow4 = new Portal(j, i, Sprite.portal.getFxImage());
                        boardGame.addEntity(objectx);
                        objectx.setEntityBelow(objectBelow4);
                        break;
                    case 'l':
                        Brick objectl = new Brick(j, i, Sprite.brick.getFxImage());
                        FlamePass objectBelow5 = new FlamePass(j, i, Sprite.powerup_flamepass.getFxImage());
                        boardGame.addEntity(objectl);
                        objectl.setEntityBelow(objectBelow5);
                        break;
                    case 'o':
                        Brick objecto = new Brick(j, i, Sprite.brick.getFxImage());
                        BombPass objectBelow6 = new BombPass(j, i, Sprite.powerup_bombpass.getFxImage());
                        boardGame.addEntity(objecto);
                        objecto.setEntityBelow(objectBelow6);
                        break;
                    case 'a':
                        Brick objecta = new Brick(j, i, Sprite.brick.getFxImage());
                        WallPass objectBelow7 = new WallPass(j, i, Sprite.powerup_wallpass.getFxImage());
                        boardGame.addEntity(objecta);
                        objecta.setEntityBelow(objectBelow7);
                        break;
                    case 'd':
                        Brick objectd = new Brick(j, i, Sprite.brick.getFxImage());
                        Detonator objectBelow8 = new Detonator(j, i, Sprite.powerup_detonator.getFxImage());
                        boardGame.addEntity(objectd);
                        objectd.setEntityBelow(objectBelow8);
                        break;
//                }
                }
            }
            Board.getPlayer().setX(1);
            Board.getPlayer().setY(1);
            Board.score = 0;
            BombermanGame.keyBoard.right = false;
            BombermanGame.keyBoard.left = false;
            BombermanGame.keyBoard.up = false;
            BombermanGame.keyBoard.down = false;
            Board.getPlayer().setImg(Sprite.player_right.getFxImage());
            boardGame.addEntity(Board.getPlayer());
        }
    }}