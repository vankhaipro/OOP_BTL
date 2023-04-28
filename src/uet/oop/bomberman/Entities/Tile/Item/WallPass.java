package uet.oop.bomberman.Entities.Tile.Item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;

public class WallPass extends Item {
    private boolean Activated = false;

    public WallPass(int xCanvas, int yCanvas, Image img) {
        super(xCanvas, yCanvas, img);
    }

    public boolean getActivated() {
        return Activated;
    }

    public void setActivated(boolean Activated) {
        this.Activated = Activated;
    }
    @Override
    public void update() {
        if(Activated) {
            remove = true;
            Board.wallPass = true;
            BombermanGame.board.removeEntityAt(this.x, this.y);
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        if(!remove) {
            super.render(gc);
        }
    }
}
