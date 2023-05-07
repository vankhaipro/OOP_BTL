package uet.oop.bomberman.Entities.Bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Entities.Entity;

import java.util.stream.IntStream;

public class DirectionExplosion extends Entity {

    protected int direction;
    protected Explosion[] explosion;
    boolean last;
    private boolean remove = false;

    //Contrustor
    public DirectionExplosion(double x, double y, int direction) {
        this.direction = direction;
        this.x = x;
        this.y = y;
        explosion = new Explosion[radiusInOneDirection()];
        createExplosions();
    }

    // tính bán kính bom nổ theo một hướng
    private int radiusInOneDirection() {
        int radius1 = 0;
        int x1 = (int) x;
        int y1 = (int) y;
        while (radius1 < Board.bombRadius) {
            if (direction == 0) y1--;
            if (direction == 1) x1++;
            if (direction == 2) y1++;
            if (direction == 3) x1--;
            if (Board.map[y1][x1] == ' ') radius1++;
            if (Board.map[y1][x1] == '#') break;
            if (Board.map[y1][x1] == 'B') radius1++;
            if (Board.map[y1][x1] != '#' && Board.map[y1][x1] != ' ') {
                for (Entity temp : BombermanGame.board.getEntities()) {
                    if (temp.getX() == x1 && temp.getY() == y1) {
                        temp.setRemove(true);
                        break;
                    }
                }
                break;
            }
        }

        return radius1;

    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }
// lưu vụ nổ trên một hướng
    private void createExplosions() {

        int x1 = (int) x;
        int y1 = (int) y;
        for (int i = 0; i < explosion.length; i++) {
            last = i == explosion.length - 1;
            switch (direction) {
                case 0:
                    y1--;
                    break;
                case 1:
                    x1++;
                    break;
                case 2:
                    y1++;
                    break;
                case 3:
                    x1--;
                    break;
                case 4:
                    break;
            }
            explosion[i] = new Explosion(x1, y1, direction, last);
        }
    }

    @Override
    public void update() {

    }

    public void update(int time) {
        for (Explosion value : explosion) {
            value.update(direction, time);
        }
    }

    @Override
    public void render(GraphicsContext gc) {

        IntStream.range(0, explosion.length).filter(i -> !remove).forEach(i -> explosion[i].render(gc));
    }
}
