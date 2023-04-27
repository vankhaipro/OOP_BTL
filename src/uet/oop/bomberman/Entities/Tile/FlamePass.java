package uet.oop.bomberman.Entities.Tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;

public class FlamePass extends Item{
    private boolean Activated = false;

    public FlamePass(int xCanvas, int yCanvas, Image img) {
        super(xCanvas, yCanvas, img);
    }

    public FlamePass(int xCanvas, int yCanvas, Image img, int TimeActivated, boolean Activated) {
        super(xCanvas, yCanvas, img, TimeActivated, Activated);
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
            Board.flamePass = true;
            //BombermanGame.board.removeEntityAt(this.x, this.y);
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        if(!remove) {
            super.render(gc);
        }
    }
}
