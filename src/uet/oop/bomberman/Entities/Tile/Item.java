package uet.oop.bomberman.Entities.Tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.Entities.Entity;

public abstract class Item extends Entity {
    protected int TimeActivated = 1200;
    protected boolean Activated = false;
    public Item(int xCanvas, int yCanvas, Image img) {
        super(xCanvas,yCanvas,img);
    }
    public Item(int xCanvas, int yCanvas, Image img, int TimeActivated, boolean Activated) {
        super(xCanvas,yCanvas,img);
        this.TimeActivated = TimeActivated;
        this.Activated = Activated;
    }

    public boolean getActivated() {
        return this.Activated;
    }
    public void setActivated(boolean Activated) {
        this.Activated = Activated;
    }
}
