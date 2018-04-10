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
import graphics.OpenglUtils;
import map.Level;

public class Minimap {

    private int width, height; // width and height of the minimap
    private int x, y; // x and y of the top left corner
    private int z;
    private static final Color COL = new Color(91, 94, 99, 110); // colour of the small rectangle on the minimap showing where the screen is
    private int xoff, yoff; // offset
    private int textureId;

    // constructor
    Minimap(int x, int y, Level map) {
        this.x = x;
        this.y = y;
        this.z = 0;
        width = 200;
        height = 200;
        init(map);

    }

    // intialise the image
    private void init(Level map) {
        int[] pixels = new int[width * height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x + y * width] = map.getTile(y / 2, x / 2).sprite.pixels[0];
                Entity e = map.getEntityOn((y * 16) / 2, (x * 16) / 2);
                if (e != null && e.sprite.pixels != null) { //TODO fix this with underground walls
                    pixels[x + y * width] = e.sprite.pixels[5];
                }

            }
        }
        textureId = OpenglUtils.loadTexture(pixels, width, height);

    }

    public void update(Level[] map, int z) {
        if (this.z != z) {
            this.z = z;
            init(map[z]);
        }
    }

    // render the minimap
    public void render() {
        float xloc = (x + (xoff * 0.125f));
        float yloc = (y + (yoff * 0.125f));
        OpenglUtils.drawTexturedQuad(x, y, width, height, textureId);
        OpenglUtils.drawFilledSquare((int) xloc, (int) yloc, (int) (31.25 * 2), (int) (17.578125 * 2), COL.getRed() / 255f, COL.getGreen() / 255f, COL.getBlue() / 255f, COL.getAlpha() / 255f);

    }

    // setter
    public void setOffset(int x, int y) {
        xoff = x;
        yoff = y;
    }

}
