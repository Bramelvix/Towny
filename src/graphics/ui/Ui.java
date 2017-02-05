package graphics.ui;

import java.awt.Graphics;
import graphics.ui.icon.UiIcons;
import graphics.ui.menu.Menu;
import graphics.ui.menu.MenuItemType;
import input.Mouse;
import map.Map;

public class Ui {
	private Menu menu;
	private TopBar top;
	private Minimap map;
	public boolean paused;
	private BuildOutline outline;

	public void render(Graphics g) {
		UiIcons.render(g);
		menu.render(g);
		top.render(g);
		map.render(g);
		outline.render(g);

	}

	public void init(Map level) {
		UiIcons.init();
		menu = new Menu();
		map = new Minimap(1290, 8, level);
		top = new TopBar(700, 15);
		outline = new BuildOutline(level);
	}

	public Ui(Map level) {
		init(level);
	}

	public int[][] getOutlineCoords() {
		return outline.getSquareCoords();
	}

	public int getOutlineXS() {
		return outline.getTileXS();
	}

	public int getOutlineYS() {
		return outline.getTileYS();
	}

	public boolean outlineIsVisible() {
		return outline.isVisible();
	}

	public Menu getMenu() {
		return menu;
	}

	public void deSelectIcons() {
		UiIcons.deSelect();
	}

	public void showMenuOn(int x, int y, MenuItemType[] items) {
		if (!outline.isVisible()) {
			showMenuOn(x, y);
			menu.addItems(items);
			menu.show();
		}
	}

	private void showMenuOn(int x, int y) {
		if (!outline.isVisible()) {
			menu = new Menu();
			menu.setX(x);
			menu.setY(y);
		}
	}

	public void showMenuOn(int x, int y, MenuItemType item) {
		if (!outline.isVisible()) {
			showMenuOn(x, y);
			menu.addItem(item);
			menu.show();
		}
	}

	public boolean menuVisible() {
		return menu.isVisible();
	}

	public void setOffset(int x, int y) {
		map.setOffset(x, y);
	}

	public void showBuildSquare(Mouse mouse) {
		outline.show(mouse);
	}

	public void removeBuildSquare() {
		outline.remove();
	}

	public void update(Mouse mouse, int xOff, int yOff) {
		menu.update(mouse, outline.isVisible());
		UiIcons.update(mouse, outline.isVisible());
		// map.init();
		if (top.clickedOnPause(mouse)) {
			if (!paused) {
				paused = true;
			} else {
				paused = false;
			}
			top.setPaused(paused);
		}
		outline.update(mouse, xOff, yOff);

	}

	public void updateCounts(int solcount, int vilcount) {
		top.updateCounts(solcount, vilcount);
	}

}
