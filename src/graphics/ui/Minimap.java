package graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import entity.Entity;
import map.Level;

public class Minimap {

    private int width, height; // width and height of the minimap
    private int x, y; // x and y of the top left corner
    private int[] pixels; // pixels array
    private static Level map; // map being shown
    private Image img; // image being rendered
    private static final Color COL = new Color(91, 94, 99, 110); // colour of the small rectangle on the minimap showing where the screen is
    private int xoff, yoff; // offset

    // constructor
    Minimap(int x, int y, Level map) {
        this.x = x;
        this.y = y;
        width = 200;
        height = 200;
        pixels = new int[(width) * (height)];
        init(map);

    }

    // intialise the image
    private void init(Level map) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y + x * width] = map.getTile(y / 2, x / 2).sprite.pixels[0];
                Entity e = map.getHardEntityOn((y * 16) / 2, (x * 16) / 2);
                if (e != null) {
                    pixels[y + x * width] = e.sprite.pixels[10];
                }

            }
        }
        img = getImageFromArray(pixels, width, height);

    }

    public void update(Level map) {
        init(map);
    }

    // render the minimap
    public void render(Graphics g) {
        g.drawImage(img, x, y, null);
        g.setColor(COL);
        float xloc = (x + (xoff * 0.125f));
        float yloc = (y + (yoff * 0.125f));
        g.fillRect((int) xloc + 1, (int) yloc + 1, (int) (31.25 * 2), (int) (17.578125 * 2));

    }

    // setter
    public void setOffset(int x, int y) {
        xoff = x;
        yoff = y;
    }

    // gets an image from an array of pixels
    private static Image getImageFromArray(int[] pixels, int width, int height) {
        DataBufferInt buffer = new DataBufferInt(pixels, pixels.length);
        int[] bandMasks = {0xFF0000, 0xFF00, 0xFF, 0xFF000000};
        WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, bandMasks, null);
        ColorModel cm = ColorModel.getRGBdefault();
        return new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);
    }

}
