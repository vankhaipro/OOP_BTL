package uet.oop.bomberman.Entities.Tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Entities.Tile.Item.Item;
import uet.oop.bomberman.Entities.Tile.Item.Portal;
import uet.oop.bomberman.Graphics.Sprite;

public class Brick extends Entity {
    private int timeRemove = 30;
    private Item entityBelow = null;

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Brick(int xUnit, int yUnit, Image img, Item entityBelow) {
        super(xUnit, yUnit, img);
        this.entityBelow = entityBelow;
    }

    public Brick(int xUnit, int yUnit, Image img, int timeRemove, Item entityBelow) {
        super(xUnit, yUnit, img);
        this.timeRemove = timeRemove;
        this.entityBelow = entityBelow;
    }

    public void setEntityBelow(Item entityBelow) {
        this.entityBelow = entityBelow;
    }

    //thay entity ben duoi
    public void update() {
        if (remove && timeRemove == 0) {
            if (entityBelow != null) {
                if (!(entityBelow instanceof Portal)) {
                    BombermanGame.board.addEntity(entityBelow);
                    Board.map[(int) this.y][(int) this.x] = ' ';
                    BombermanGame.board.removeEntityAt(this.x, this.y);
                } else {
                    int index = BombermanGame.board.index(this.x, this.y);
                    BombermanGame.board.getEntities().set(index, entityBelow);
                }
            } else {
                BombermanGame.board.removeEntityAt(this.x, this.y);
                Board.map[(int) this.y][(int) this.x] = ' ';
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!remove) {
            super.render(gc);
            //gc.drawImage(this.img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
        } else if (timeRemove > 0) {
            int time = timeRemove % 30;
            if (time >= 20) {
                setImg(Sprite.brick_exploded.getFxImage());
                super.render(gc);
                //gc.drawImage(this.img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
                timeRemove--;
            } else if (time >= 10) {
                setImg(Sprite.brick_exploded1.getFxImage());
                super.render(gc);
                //gc.drawImage(this.img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
                timeRemove--;
            } else {
                setImg(Sprite.brick_exploded2.getFxImage());
                super.render(gc);
                //gc.drawImage(this.img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
                timeRemove--;
            }
        }
    }
}