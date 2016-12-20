package graphics.ui.icon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Icon {
	private int x, y;
	private boolean hover;
	private String path;
	private final int WIDTH;
	private final int HEIGHT;
	public int[] pixels;
	private boolean selected;
	private Image image;

	public Icon(int x, int y, String path, int width, int height) {
		this.path = path;
		this.x = x;
		WIDTH = width;
		HEIGHT = height;
		this.y = y;
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

	public int getHeight() {
		return HEIGHT;
	}

	public int getWidth() {
		return WIDTH;
	}

	public void setHoverOn(boolean hover) {
		this.hover = hover;

	}

	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
		if (selected || hover) {
			g.setColor(Color.red);
			g.drawRect(x, y, WIDTH, HEIGHT);
			g.drawRect(x+1, y+1, WIDTH-2, HEIGHT-2);
			g.drawRect(x+2, y+2, WIDTH-4, HEIGHT-4);
		}
	}

	private void load() {
		try {
			image = ImageIO.read(Icon.class.getResource(path)).getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
