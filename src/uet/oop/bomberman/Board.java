package uet.oop.bomberman;

import uet.oop.bomberman.Entities.Character.Bomber;
import uet.oop.bomberman.Entities.Character.Enemy.Enemy;
import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Graphics.Sprite;
import uet.oop.bomberman.Level.Level;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static final int MAX_LEVEL = 3;
    public static char[][] map = new char[HEIGHT][WIDTH];

    public static int bombCount =1;
    public static int bombRadius = 1;
    public static int score = 0;
    public static boolean flamePass = false;
    public static boolean bombPass = false;
    public static boolean wallPass = false;
    public static int countDownTime = 180 * 60;
    public static int scorePrevious;
    public static double speedOfEnemy = 0.025;
    private static double speedOfPlayer = 0.0035;
    public static File file = new File("res/levels/save.txt");
    private static List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();
    private static List<Enemy> enemies = new ArrayList<>();
    private static Bomber player;

    private Level gameLevel;
    private int level;

    /**
     * Khởi tạo game
     */
    public Board() {
        player = new Bomber(1, 1, Sprite.player_right.getFxImage(), speedOfPlayer);
        loadLevel();
        gameLevel = new Level(this);
    }

    public static Bomber getPlayer() {
        return player;
    }

    /**
     * khởi tạo level
     */
    public void changeLevel(int level) {
        entities.clear();
        enemies.clear();
        stillObjects.clear();
        try {
            gameLevel.createMapLevel(level);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bombCount = 1;
        bombRadius = 1;
        flamePass = false;
        bombPass = false;
        wallPass = false;
        this.level = level;
    }

    /**
     * Đưa quái vật vào vị trí trong bản đồ
     */
    public Entity getEntity(double x, double y) {
        for (Entity temp : entities) {
            if (temp.getX() == x && temp.getY() == y) {
                return temp;
            }
        }
        return null;
    }

    public void removeEntityAt(double x, double y) {
        for (int i = 0; i < entities.size(); i++) {
            Entity temp = entities.get(i);
            if (temp.getX() == x && temp.getY() == y) {
                entities.remove(i);
                break;
            }
        }
    }

    public void addEntity(Entity object) {
        entities.add(object);
    }

    public void addStillObject(Entity object) {
        stillObjects.add(object);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Entity> getStillObjects() {
        return stillObjects;
    }

    public int getLeft() {
        return getPlayer().getHealth();
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void removeEnemyAt(double x, double y) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy temp = enemies.get(i);
            if (temp.getX() == x && temp.getY() == y) {
                enemies.remove(temp);
            }
        }
    }

    public void addEnemy(Enemy newEnemy) {
        enemies.add(newEnemy);
    }

    public int countDown() {
        countDownTime--;
        return countDownTime;
    }


    public int index(double x, double y) {
        for (int i = 0; i < entities.size(); i++) {
            Entity temp = entities.get(i);
            if (temp.getX() == x && temp.getY() == y) {
                return i;
            }
        }
        return 0;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Level getGameLevel() {
        return this.gameLevel;
    }

    public void render() {
        BombermanGame.gc.clearRect(0, 0, BombermanGame.canvas.getWidth(), BombermanGame.canvas.getHeight());
        BombermanGame.gcForPlayer.clearRect(0, 0, BombermanGame.canvas.getWidth(), BombermanGame.canvas.getHeight());
        BombermanGame.board.getStillObjects().forEach(g -> g.render(BombermanGame.gc));
        BombermanGame.board.getEntities().forEach(g -> g.render(BombermanGame.gcForPlayer));
        BombermanGame.board.getEnemies().forEach(g -> g.render(BombermanGame.gcForPlayer));

    }

    public void update() {
        for (Entity entity : entities) {
            entity.update();
        }
        for (Enemy enemy : enemies) {
            enemy.update();
        }
    }

    /**
     * load thông tin level game
     */
    public void loadLevel() {
        try {
            Scanner scanner = new Scanner(file);
            level = scanner.nextInt();
            int left = scanner.nextInt();
            scorePrevious = scanner.nextInt();
            if (left == 0) {
                left = 3;
                level = 1;
                Board.scorePrevious = 0;
            }
            player.setHealth(left);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
