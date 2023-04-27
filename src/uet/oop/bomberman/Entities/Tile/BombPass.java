package uet.oop.bomberman.Entities.Tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

public class BombPass extends Item {
    public BombPass(int xCanvas, int yCanvas, Image img) {
        super(xCanvas, yCanvas, img);
    }

    public BombPass(int xCanvas, int yCanvas, Image img, int TimeActivated, boolean Activated) {
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
            //Board.bombPass = true;
            //BombermanGame.board.removeEntityAT(this.x, this.y);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!remove) {
            super.render(gc);
        }
    }
}
