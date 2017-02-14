package graphics.ui.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import input.Mouse;

public class Menu { // the menu is the little options menu that shows up when
					// you right click
	private int x, y; // x and y of the top left corner
	private int ingameX, ingameY;
	private int width = 70; // width and height hardcoded
	private int height = 20;
	public List<MenuItem> items; // list of items on the menu
	private boolean visible; // is the item visible
	private Color colour = new Color(91, 94, 99, 210); // the grey-blue colour
														// of the background of
														// the menu

	// render method
	public void render(Graphics g) {
		if (visible) {
			g.setColor(colour);
			for (MenuItem i : items) {
				if (i.getText().length() > 10) {
					width = 100;
				}
			}
			g.fillRect(x, y, width, height);
			for (MenuItem i : items) {
				i.render(g);
			}
		}
	}

	// showing the menu
	public void show(Mouse mouse) {
		visible = true;
		ingameX = mouse.getX();
		ingameY = mouse.getY();
	}

	// getter
	public boolean isVisible() {
		return visible;
	}

	// setter
	public void setVisible(boolean vis) {
		if (visible) {
			visible = vis;
		}
	}

	// hiding the menu
	public void hide() {
		setVisible(false);
		this.width = 0;
		this.height = 0;
	}

	// adding items to the menu
	public void addItems(String[] items) {
		for (String i : items) {
			addItem(i);
		}
	}

	// updating the menu
	public void update(Mouse mouse, boolean forceinvisible) {
		if (forceinvisible) {
			hide();
			return;
		} else {
			for (MenuItem i : items) {
				i.update(mouse);
			}
			setVisible((((mouse.getTrueXPixels()) >= getX() - 10)
					&& ((mouse.getTrueXPixels()) <= getX() + (getWidth() + 10))
					&& ((mouse.getTrueYPixels()) >= getY() - 10)
					&& ((mouse.getTrueYPixels()) <= getY() + (getHeight() + 10))));
		}
	}

	// constructor
	public Menu() {
		x = 0;
		y = 0;
		items = new ArrayList<MenuItem>();
		setVisible(false);
	}

	// getters
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getYLocForMenuItem() {
		return y + (items.size() * 15);
	}
	public int getIngameX() {
		return ingameX;
	}
	public int getIngameY() {
		return ingameY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean clickedOnItem(String type, Mouse mouse) {
		for (MenuItem i : items) {
			if (i.equals(type)) {
				return i.clicked(mouse);
			}
		}
		return false;
	}

	// adding an item to the menu
	public void addItem(String i) {
		if (i != null) {
			MenuItem o = new MenuItem(i, this);
			if (!(items.contains(o))) {
				items.add(o);
				height = height + 15;
			}
		}
	}

	// setter
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}
