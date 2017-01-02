package graphics.ui.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import input.Mouse;

public class MenuItem {
	private String text;
	public int x, y;
	public MenuItemType type;
	private int width;
	private boolean hover;

	public MenuItem(MenuItemType type, Menu menu) {
		this.type = type;
		text = type.toString();
		x = menu.getX();
		width = menu.getWidth();
		y = menu.getYLocForMenuItem();

	}

	public void render(Graphics g) {
		if (hover) {
			g.setColor(Color.red);
		} else {
			g.setColor(Color.black);
		}
		g.drawString(text, x, y + 10);
	}

	public void update(Mouse mouse) {
		hover = ((((mouse.getTrueXPixels()) >= x) && ((mouse.getTrueXPixels()) <= x + width)
				&& ((mouse.getTrueYPixels()) >= y) && ((mouse.getTrueYPixels()) <= y + 10)));
	}

	public boolean clicked(Mouse mouse) {
		return hover && mouse.getButton() == 1;
	}

	@Override
	public boolean equals(Object o) {
		return (type == ((MenuItem) o).type);

	}

	public boolean equals(MenuItemType i) {
		return type == i;
	}

}
