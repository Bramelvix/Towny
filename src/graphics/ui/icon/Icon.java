package graphics.ui.icon;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import graphics.OpenglUtils;

//icon on the bottom left of the screen (pickaxe, axe,...)
public class Icon {
    private int x, y; // x and y of the top left corner
    private boolean hover; // is the mouse hovering over the icon
    private int size; // width and length equal
    private boolean selected; // is the icon selected
    private int[] pixels; // pixels
    private int id;
    private static final float scaleValue = 0.176056338028169f;

    // constructor
    Icon(int x, int y, String path) {
        this.x = x;
        this.y = y;
        load(path);
        id = OpenglUtils.loadTexture(pixels, size);
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

    public int getSize() {
        return size;
    }

    // setters
    public void setSelect(boolean select) {
        this.selected = select;
    }

    public void setHoverOn(boolean hover) {
        this.hover = hover;

    }

    //render the icon on the screen
    public void render(int xoff, int yoff) {
		OpenglUtils.iconDraw(id,x+xoff,y+yoff,size,selected||hover);
    }

    //load the image
    private void load(String path) {
        try {
            BufferedImage before = ImageIO.read(Icon.class.getResource(path));
            int w = before.getWidth();
            int h = before.getHeight();

            AffineTransform at = new AffineTransform();
            at.scale(scaleValue, scaleValue); //this is to scale the images from 512px to 90 px
            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            BufferedImage after = new BufferedImage( (int)(w*scaleValue), (int)(h*scaleValue), BufferedImage.TYPE_INT_ARGB);
            after = scaleOp.filter(before, after);
            size = after.getWidth();
            pixels = new int[size * size];
            after.getRGB(0, 0, size, size, pixels, 0, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
