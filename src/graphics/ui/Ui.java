package graphics.ui;

import java.awt.Graphics;
import graphics.ui.icon.UiIcons;
import graphics.ui.menu.Menu;
import input.Mouse;
import map.Level;

//main class used to manage the ui
public class Ui {
	// different ui elements
	private Menu menu;
	private TopBar top;
	private Minimap map;
	private BuildOutline outline;
	private SelectionSquare selection;

	// rendering the ui
	public void render(Graphics g) {
		UiIcons.render(g);
		menu.render(g);
		top.render(g);
		map.render(g);
		outline.render(g);
		selection.render(g);

	}

	public void init(Level level) {
		UiIcons.init();
		menu = new Menu();
		selection = new SelectionSquare();
		map = new Minimap(1290, 8, level);
		top = new TopBar(700, 15);
		outline = new BuildOutline(level);
	}

	public Ui(Level level) {
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

	public void showMenuOn(int x, int y, String[] items) {
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

	public void showMenuOn(int x, int y, String item) {
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

	public void showSelectionSquare(Mouse mouse) {
		selection.show(mouse);
	}
	public void resetSelection() {
		selection.reset();
	}

	public int getSelectionX() {
		return selection.getX();
	}

	public int getSelectionY() {
		return selection.getY();
	}

	public int getSelectionWidth() {
		return selection.getWidth();
	}

	public int getSelectionHeight() {
		return selection.getHeight();
	}

	public void showBuildSquare(Mouse mouse, int xoff, int yoff) {
		outline.show(mouse, xoff, yoff);
	}

	public void removeBuildSquare() {
		outline.remove();
	}

	public void update(Mouse mouse, int xOff, int yOff) {
		menu.update(mouse, outline.isVisible());
		UiIcons.update(mouse, outline.isVisible());
		// map.init();
		if (top.clickedOnPause(mouse)) {
			top.togglePause();
		}
		outline.update(mouse, xOff, yOff);
		selection.update(mouse);

	}

	public boolean getPaused() {
		return top.getPaused();
	}

	public void updateCounts(int solcount, int vilcount) {
		top.updateCounts(solcount, vilcount);
	}

}
