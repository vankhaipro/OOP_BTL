package uet.oop.bomberman.Entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import uet.oop.bomberman.Graphics.Sprite;
import java.util.HashSet;
public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected double x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected double y;
    protected Image img;
    //xét xem vật ở trên đã bị bỏ hay chưa
    protected boolean remove = false;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(double xUnit, double yUnit, Image img) {
        this.x = xUnit;
        this.y = yUnit;
        this.img = img;
    }

    protected Entity() {
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }


    /**
     * tạo hình ảnh game
     */
    public void render(GraphicsContext gc) {
        gc.drawImage(this.img, x * Sprite.SCALED_SIZE, (y + 2) * Sprite.SCALED_SIZE);
    }

    public abstract void update();
    public HashSet<String> getMask(Entity go) {
        Image imgObj = go.getImg();
        HashSet<String> mask = null;
        if (imgObj != null) {
            mask = new HashSet<>();
            int W = (int) imgObj.getWidth();
            int H = (int) imgObj.getHeight();

            PixelReader reader = imgObj.getPixelReader();

            int a;
            for (int y = 0; y < H; y++) {
                for (int x = 0; x < W; x++) {
                    final int argb = reader.getArgb(x, y);
                    a = (argb >> 24) & 0xff;
                    if (a != 0) {
                        mask.add((int) (go.getX() * 32) + x + "," + ((int) (go.getY() * 32) - y)); // add the absolute x and absolute y coordinates to our set
                    }
                }
            }
        }
        return mask;
    }
}
