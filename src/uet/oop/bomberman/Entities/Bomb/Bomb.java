package uet.oop.bomberman.Entities.Bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;

import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Graphics.Sprite;
import uet.oop.bomberman.Sound.Sound;

import java.util.HashSet;
import java.util.stream.IntStream;

public class Bomb extends Entity {

    protected double timeToExplode =1800;
    protected int timeAfter = 80;
    protected boolean exploded = false;
    protected DirectionExplosion[] explosions = new DirectionExplosion[5];
    private int animate = 0;


    public Bomb() {
        remove = true;
    }

    public Bomb(double x, double y, boolean remove, Image img) {
        super(x, y, img);
        this.remove = remove;
    }

    @Override
    public void update() {
        if (!remove) {
            if (timeToExplode > 0)
                timeToExplode--;
            else {
                if (!exploded)
                    explosion();
                if (timeAfter > 0) {
                    timeAfter--;
                    updateExplosions();
                } else {
                    remove = true;
                    Sound.play("BOM_11_M");
                }
            }
        }
        if (remove && timeAfter == 0) {
            Board.getPlayer().removeBombAt(this.x, this.y);
        }
    }

    protected void explosion() {

        exploded = true;
        for (int i = 0; i < explosions.length; i++) {
            explosions[i] = new DirectionExplosion((int) x, (int) y, i);
        }
    }

    public void updateExplosions() {
        for (DirectionExplosion explosion : explosions) {
            explosion.update(timeAfter);
        }
    }

    public void collideWithBombOther(Entity obj) {
        HashSet<String> maskBomb = getMask(this);
        HashSet<String> maskBombOther = getMask(obj);
        maskBomb.retainAll(maskBombOther);
        if (maskBomb.size() > 0) {
            timeToExplode = 0;
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        if (!exploded && !remove) {
            Sprite sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60);
            setImg(sprite.getFxImage());
            animate++;
            super.render(gc);
        } else if (!remove) {
            int time = timeAfter % 40 ;
            if (time >=20) {
                setImg(Sprite.bomb_exploded2.getFxImage());
            }
            else if (time >= 10) {
                setImg(Sprite.bomb_exploded1.getFxImage());
            }
            else {
                setImg(Sprite.bomb_exploded.getFxImage());
            }
            super.render(gc);
            IntStream.range(0, explosions.length).forEach(i -> explosions[i].render(gc));
        }
    }
}
