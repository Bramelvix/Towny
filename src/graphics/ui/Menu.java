package graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Menu {
	private int x, y;
	private int width = 70;
	private int height = 50;
	public List<MenuItem> items;
	private boolean reset;
	private boolean visible;
	Color colour = new Color(91, 94, 99, 210);

	public void render(Graphics g) {
		if (visible) {
			g.setColor(colour);
			g.fillRect(x, y, width, height);
			for (MenuItem i : items) {
				// i.render(g);
			}
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean vis) {
		visible = vis;
	}
	
	public boolean isReset() {
		return reset;
	}

	public Menu() {
		x = 0;
		y = 0;
		reset = true;
		items = new ArrayList<MenuItem>();
		setVisible(false);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void addItem(MenuItem i) {
		reset = false;
		items.add(i);
		// height = height + 50;
	}

	public int getYLocForMenuItem() {
		return y + (items.size() * 50);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}
