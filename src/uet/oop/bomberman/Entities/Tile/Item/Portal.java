package uet.oop.bomberman.Entities.Tile.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;

public class Portal extends Item {
    private boolean Activated = false;

    public Portal(int xCanvas, int yCanvas, Image img) {
        super(xCanvas,yCanvas,img);
        remove = false;
    }

    public boolean getActivated() {
        return Activated;
    }

    public void setActivated(boolean Activated) {
        this.Activated = Activated;
    }

    /**
     * Tạo cổng qua màn, chỉ qua màn khi win
     */
    @Override
    public void update() {
        if(BombermanGame.board.getEnemies().size() == 0) {
            Activated = true;
        }
        if(Activated) {
            Board.map[(int) this.y][(int) this.x] = ' ';
        } else {
            Board.map[(int) this.y][(int) this.x] = '#';
        }
    }
}
