package graphics.ui.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import input.Mouse;

public class Menu {
	private int x, y;
	private int width = 70;
	private int height = 20;
	public List<MenuItem> items;
	private boolean visible;
	Color colour = new Color(91, 94, 99, 210);

	public void render(Graphics g) {
		if (visible) {
			g.setColor(colour);
			g.fillRect(x, y, width, height);
			for (MenuItem i : items) {
				i.render(g);
			}
		}
	}

	public void show() {
		visible = true;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean vis) {
		if (visible) {
			visible = vis;
		}
	}

	public void hide() {
		setVisible(false);
		this.width = 0;
		this.height = 0;
	}

	public void addItems(MenuItemType[] items) {
		for (MenuItemType i : items) {
			addItem(i);
		}
	}

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

	public Menu() {
		x = 0;
		y = 0;
		items = new ArrayList<MenuItem>();
		setVisible(false);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean clickedOnItem(MenuItemType type, Mouse mouse) {
		for (MenuItem i : items) {
			if (i.equals(type)) {
				return i.clicked(mouse);
			}
		}
		return false;
	}

	public void addItem(MenuItemType i) {
		if (i != null) {
			MenuItem o = new MenuItem(i, this);
			if (!(items.contains(o))) {
				items.add(o);
				height = height + 10;
			}
		}
	}

	public int getYLocForMenuItem() {
		return y + (items.size() * 15);
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
