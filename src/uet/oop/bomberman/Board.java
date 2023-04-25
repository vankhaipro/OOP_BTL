package uet.oop.bomberman;

import uet.oop.bomberman.Entities.Character.Bomber;
import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Graphics.Sprite;

public class Board {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static final int MAX_LEVEL = 3;
    public static char[][] map = new char[HEIGHT][WIDTH];

    public static int bombCount =1;
    public static int bombRadius = 1;
    private static Bomber player;

    public static boolean flamePass = false;

    public static Bomber getPlayer() {
        return player;
    }
}
