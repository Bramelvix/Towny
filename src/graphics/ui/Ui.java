package graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import entity.mob.Villager;
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

	public int getOutlineX() {
		return outline.getTileX();
	}

	public int getOutlineY() {
		return outline.getTileY();
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
		showMenuOn(x, y);
		menu.addItems(items);
		menu.show();
	}

	private void showMenuOn(int x, int y) {
		menu = new Menu();
		menu.setX(x);
		menu.setY(y);
	}

	public void showMenuOn(int x, int y, MenuItemType item) {
		showMenuOn(x, y);
		menu.addItem(item);
		menu.show();
	}

	public boolean menuVisible() {
		return menu.isVisible();
	}

	public void setOffset(int x, int y) {
		map.setOffset(x, y);
	}

	public void showBuildSquare() {
		outline.show();
	}

	public void removeBuildSquare() {
		outline.remove();
	}

	public void update(Mouse mouse) {
		menu.update(mouse);
		UiIcons.update(mouse);
		// map.init();
		if (top.clickedOnPause(mouse)) {
			if (!paused) {
				paused = true;
			} else {
				paused = false;
			}
			top.setPaused(paused);
		}
		outline.update(mouse.getTileX(), mouse.getTileY());

	}

	public void updateCounts(int solcount, int vilcount) {
		top.updateCounts(solcount, vilcount);
	}

}
