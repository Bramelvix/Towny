package graphics.ui;

import java.awt.Graphics;

import entity.dynamic.mob.work.BuildingRecipe;
import graphics.ui.icon.UiIcons;
import graphics.ui.menu.Menu;
import graphics.ui.menu.MenuItem;
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

	public byte getSpeed() {
		return top.getSpeed();
	}

    private void init(Level level) {
		UiIcons.init();
		menu = new Menu();
		selection = new SelectionSquare();
		map = new Minimap(1290, 8, level);
		top = new TopBar(250, 5);
		outline = new BuildOutline(level);
	}

	public Ui(Level level) {
		init(level);
	}

	public int[][] getOutlineCoords() {
		return outline.getSquareCoords();
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

	public void showMenuOn(Mouse mouse, MenuItem... items) {
		if (!outline.isVisible()) {
			showMenuOn(mouse.getTrueXPixels(), mouse.getTrueYPixels());
			menu.addItems(items);
			menu.show(mouse);
		}
	}
	public BuildingRecipe getBuildRecipeOutline() {
		return outline.getBuildRecipe();
	}

	public int getMenuIngameY() {
		return menu.getIngameY();
	}

	public int getMenuIngameX() {
		return menu.getIngameX();
	}

	private void showMenuOn(int x, int y) {
		if (!outline.isVisible()) {
			menu = new Menu();
			menu.setX(x);
			menu.setY(y);
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

	public void showBuildSquare(Mouse mouse, int xoff, int yoff, boolean lockedSize, BuildingRecipe build) {
		outline.show(mouse, xoff, yoff, lockedSize, build);
	}

	public void removeBuildSquare() {
		outline.remove();
	}

	public void update(Mouse mouse, int xOff, int yOff) {
		menu.update(mouse, outline.isVisible());
        UiIcons.update(mouse);
		outline.update(mouse, xOff, yOff);
		selection.update(mouse);
		top.update(mouse);

	}

	public boolean isPaused() {
		return top.isPaused();
	}

	public void updateCounts(int solcount, int vilcount) {
		top.updateCounts(solcount, vilcount);
	}

    public void updateMinimap(Level level) {
        map.update(level);
    }

}
