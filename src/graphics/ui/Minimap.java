package graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import map.Map;

public class Minimap {

	private int width, height;
	private int x, y;
	public int[] pixels;
	private Map map;
	private Image img;
	private static final Color COL = new Color(91, 94, 99, 110);
	private int xoff, yoff;

	public Minimap(int x, int y, Map map) {
		this.x = x;
		this.y = y;
		width = 200;
		height = 200;
		pixels = new int[(width) * (height)];
		this.map = map;
		init();

	}

	public void init() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
					pixels[x + y * width] = 0;
				} else {
					pixels[y + x * width] = map.tiles[y / 2 + x / 2 * map.width].sprite.pixels[0];

				}

			}
		}
		img = getImageFromArray(pixels, width, height);

	}

	public void render(Graphics g) {
		g.drawImage(img, x, y, null);
		g.setColor(COL);
		float xloc = (x + (xoff / 3) / 3);
		float yloc = (y + (yoff / 3) / 3);
		g.fillRect((int) xloc+1, (int) yloc+1, 31*3, 17*3);

	}

	public void setOffset(int x, int y) {
		xoff = x;
		yoff = y;
	}

	public static Image getImageFromArray(int[] pixels, int width, int height) {
		DataBufferInt buffer = new DataBufferInt(pixels, pixels.length);
		int[] bandMasks = { 0xFF0000, 0xFF00, 0xFF, 0xFF000000 };
		WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, bandMasks, null);
		ColorModel cm = ColorModel.getRGBdefault();
		BufferedImage image = new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);
		return image;
	}

}
