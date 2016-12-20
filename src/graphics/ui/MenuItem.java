package graphics.ui;

import java.awt.Graphics;

public class MenuItem {
	private String text;
	public int x, y;

	public MenuItem(String text, Menu menu) {
		this.text = text;
		x = menu.getX();
		y = menu.getYLocForMenuItem();

	}

	public void render(Graphics g) {
		g.drawString(text, x, y);
	}

}
