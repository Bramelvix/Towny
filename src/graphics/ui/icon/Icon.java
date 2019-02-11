package graphics.ui.icon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import graphics.OpenglUtils;
import input.MousePosition;

//icon on the bottom left of the screen (pickaxe, axe,...)
public class Icon {
    private int x, y; // x and y of the top left corner
    private boolean hover; // is the mouse hovering over the icon
    private int width,height; // width and length
    private boolean selected; // is the icon selected
    private int id;

    // constructor
    public Icon(int x, int y, String path, float scale) {
        this.x = x;
        this.y = y;
        int[] pixels = load(path, scale);
        id = OpenglUtils.loadTexture(pixels, width,height);
    }

    // getters
    public boolean isSelected() {
        return selected;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean hoverOn() {
        return hover;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight () {
        return height;
    }

    // setters
    public void setSelect(boolean select) {
        this.selected = select;
    }

    private void setHover(boolean hover) {
        this.hover = hover;

    }

    //render the icon on the screen
    public void render() {
        OpenglUtils.iconDraw(id, x, y, width,height, selected || hover);
    }

    //load the image
    private int[] load(String path, float scaleValue) {
        try {
            BufferedImage before = ImageIO.read(Icon.class.getResource(path));
            BufferedImage after = OpenglUtils.getScaledBufferedImage(before, scaleValue, scaleValue);
            width = after.getWidth();
            height = after.getHeight();
            int[] pixels = new int[width * height];
            after.getRGB(0, 0, width, height, pixels, 0, width);
            return pixels;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update() {
        setHover((((MousePosition.getTrueX()) >= getX())
                && ((MousePosition.getTrueX()) <= getX() + (getWidth())) && ((MousePosition.getTrueY()) >= getY())
                && ((MousePosition.getTrueY()) <= getY() + (getHeight()))));
    }

}
