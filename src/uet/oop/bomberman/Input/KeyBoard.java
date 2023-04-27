package uet.oop.bomberman.Input;

import javafx.scene.Scene;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Graphics.Sprite;

public class KeyBoard {
    public boolean right, left, up, down,
            space, stop, start,restart;

    public KeyBoard() {
        right = false;
        left = false;
        up = false;
        down = false;
        space = false;
        stop = false;
        start = true;
        restart = false;
    }

    public void status(Scene gameScene) {
        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                case A:
                    left = true;
                    break;
                case RIGHT:
                case D:
                    right = true;
                    break;
                case UP:
                case W:
                    up = true;
                    break;
                case DOWN:
                case S:
                    down = true;
                    break;
                case SPACE:
                    space = true;
                    break;

            }
        });

        gameScene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                case A:
                    left = false;
                    Board.getPlayer().setImg(Sprite.player_left.getFxImage());
                    break;
                case RIGHT:
                case D:
                    right = false;
                    Board.getPlayer().setImg(Sprite.player_right.getFxImage());
                    break;
                case UP:
                case W:
                    up = false;
                    Board.getPlayer().setImg(Sprite.player_up.getFxImage());
                    break;
                case DOWN:
                case S:
                    down = false;
                    Board.getPlayer().setImg(Sprite.player_down.getFxImage());
                    break;
                case SPACE:
                    space = false;
                    break;
            }
        });
    }

}

