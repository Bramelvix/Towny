package graphics.ui.icon;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import graphics.Screen;

public class Icon {
	private int x, y;
	private boolean hover;
	private String path;
	public final int WIDTH;
	public final int HEIGHT;
	public int[] pixels;
	private boolean selected;

	public Icon(int x, int y, String path, int width, int height) {
		this.path = path;
		WIDTH = width;
		HEIGHT = height;
		this.x = x;
		this.y = y;
		pixels = new int[WIDTH * HEIGHT];
		load();
	}

	public boolean selected() {
		return selected;
	}

	public void setSelect(boolean select) {
		this.selected = select;
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

	public void setHoverOn(boolean hover) {
		this.hover = hover;

	}

	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	public void render(Screen screen) {
		screen.renderIcon(x, y, this);
	}

	private void load() {
		try {
			BufferedImage image = toBufferedImage(
					ImageIO.read(Icon.class.getResource(path)).getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
