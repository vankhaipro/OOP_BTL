package uet.oop.bomberman.Entities.Tile.Item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.oop.bomberman.BombermanGame;

import static uet.oop.bomberman.Board.bombCount;

public class BombItem extends Item {
    public BombItem(int xCanvas, int yCanvas, Image img) {
        super(xCanvas, yCanvas, img);
    }

    public BombItem(int xCanvas, int yCanvas, Image img, int TimeActivated, boolean Activated) {
        super(xCanvas, yCanvas, img, TimeActivated, Activated);
    }

    @Override
    public boolean getActivated() {
        return super.getActivated();
    }

    @Override
    public void setActivated(boolean Activated) {
        super.setActivated(Activated);
    }

    @Override
    public void update() {
        if (Activated) {
            remove = true;
            bombCount++;
            BombermanGame.board.removeEntityAt(this.x, this.y);
            System.out.println(bombCount);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!remove) {
            super.render(gc);
        }
    }
}
