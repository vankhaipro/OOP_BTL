package uet.oop.bomberman.Entities.Tile.Item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;

public class Detonator extends Item {
    private boolean Activated = false;
    public Detonator(int xCanvas, int yCanvas, Image img) {
        super(xCanvas, yCanvas, img);
    }

    public Detonator(int xCanvas, int yCanvas, Image img, int TimeActivated, boolean Activated) {
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
            Board.getPlayer().setHealth(Board.getPlayer().getHealth() + 1);
            Board.getPlayer().updateStatus();
            BombermanGame.board.removeEntityAt(this.x, this.y);
            System.out.println(Board.getPlayer().getHealth());
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        if(!remove) {
            super.render(gc);
        }
    }
}

