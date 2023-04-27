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
        //loadLevel(); // code nốt board
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
}
