package graphics.ui.icon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.Game;

//icon on the bottom left of the screen (pickaxe, axe,...)
public class Icon {
	private int x, y; // x and y of the top left corner
	private boolean hover; // is the mouse hovering over the icon
	private String path; // path to the image
	private final int WIDTH; // width
	private final int HEIGHT; // height
	private boolean selected; // is the icon selected
	private Image image; // image

	// constructor
	public Icon(int x, int y, String path, int width, int height) {
		this.path = path;
		this.x = x*Game.SCALE;
		WIDTH = width*Game.SCALE;
		HEIGHT = height*Game.SCALE;
		this.y = y*Game.SCALE;
		load();
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

	public int getHeight() {
		return HEIGHT;
	}

	public int getWidth() {
		return WIDTH;
	}

	// setters
	public void setSelect(boolean select) {
		this.selected = select;
	}

	public void setHoverOn(boolean hover) {
		this.hover = hover;

	}

	//render the icon on the screen
	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
		if (selected || hover) {
			g.setColor(Color.red);
			g.drawRect(x, y, WIDTH, HEIGHT);
			g.drawRect(x + 1, y + 1, WIDTH - 2, HEIGHT - 2); //rendering the red square around the icon
			g.drawRect(x + 2, y + 2, WIDTH - 4, HEIGHT - 4);
		}
	}

	//load the image
	private void load() {
		try {
			image = ImageIO.read(Icon.class.getResource(path)).getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
