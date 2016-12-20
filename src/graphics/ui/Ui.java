package graphics.ui;

import java.awt.Graphics;

import graphics.ui.icon.Icon;
import graphics.ui.icon.UiIcons;
import input.Mouse;

public class Ui {
	private static Menu menu;

	public static void render(Graphics g) {
		UiIcons.render(g);
		menu.render(g);

	}

	public static void init() {
		UiIcons.init();
		menu = new Menu();
	}

	public static void showMenuOn(int x, int y) {
		menu.setX(x);
		menu.setY(y);
		MenuItem item = new MenuItem("Test", menu);
		menu.addItem(item);
		menu.setVisible(true);
	}

	public static void hoverOnMenu(Mouse mouse) {
		menu.setVisible((((mouse.getTrueXPixels()) >= menu.getX())
				&& ((mouse.getTrueXPixels()) <= menu.getX() + (menu.getWidth()))
				&& ((mouse.getTrueYPixels()) >= menu.getY())
				&& ((mouse.getTrueYPixels()) <= menu.getY() + (menu.getHeight()))));
	}

}
