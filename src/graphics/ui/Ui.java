package graphics.ui;

import java.awt.Graphics;
import java.util.List;

import entity.mob.Villager;
import graphics.ui.icon.UiIcons;
import input.Mouse;
import map.Map;

public class Ui {
	private Menu menu;
	private TopBar top;
	private Minimap map;
	public boolean paused;

	public void render(Graphics g) {
		UiIcons.render(g);
		menu.render(g);
		top.render(g);
		map.render(g);

	}

	public void init(Map level) {
		UiIcons.init();
		menu = new Menu();
		map = new Minimap(1290, 8, level);
		top = new TopBar(700, 15);
	}

	public Ui(Map level) {
		init(level);
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

	public void setOffset(int x, int y) {
		map.setOffset(x, y);
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
		map.init();
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
