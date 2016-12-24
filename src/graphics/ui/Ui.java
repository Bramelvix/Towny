package graphics.ui;

import java.awt.Graphics;

import graphics.ui.icon.UiIcons;
import input.Mouse;

public class Ui {
	private Menu menu;
	private TopBar top;
	public boolean paused;

	public void render(Graphics g) {
		UiIcons.render(g);
		menu.render(g);
		top.render(g);

	}

	public void init() {
		UiIcons.init();
		menu = new Menu();
		top = new TopBar(540, 15);
	}

	public Ui() {
		init();
	}

	public void deSelectIcons() {
		UiIcons.deSelect();
	}

	public void showMenuOn(int x, int y) {
		menu.setX(x);
		menu.setY(y);
		MenuItem item = new MenuItem("Test", menu);
		// menu.addItem(item);
		menu.setVisible(true);
	}

	private void hoverOnMenu(Mouse mouse) {
		menu.setVisible((((mouse.getTrueXPixels()) >= menu.getX())
				&& ((mouse.getTrueXPixels()) <= menu.getX() + (menu.getWidth()))
				&& ((mouse.getTrueYPixels()) >= menu.getY())
				&& ((mouse.getTrueYPixels()) <= menu.getY() + (menu.getHeight()))));
	}

	public void update(Mouse mouse) {
		UiIcons.update(mouse);
		hoverOnMenu(mouse);
		updateMenu();
		if (top.clickedOnPause(mouse)) {
			if (!paused) {
				paused = true;
			} else {
				paused = false;
			}
			top.setPaused(paused);
		}

	}

	public void updateCounts(int solcount, int vilcount) {
		top.updateCounts(solcount, vilcount);
	}

	private void updateMenu() {
		if (!menu.isVisible() && !menu.isReset()) {
			menu = new Menu();
		}
	}

}
