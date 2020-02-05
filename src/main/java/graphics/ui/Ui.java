package graphics.ui;

import entity.dynamic.mob.work.BuildingRecipe;
import graphics.ui.icon.UiIcons;
import graphics.ui.menu.Menu;
import graphics.ui.menu.MenuItem;
import input.MousePosition;
import main.Game;
import map.Level;

//main class used to manage the ui
public class Ui {

	// different ui elements
	private Menu menu;
	private TopBar top;
	private Minimap map;
	private BuildOutline outline;
	private SelectionSquare selection;
	private LayerLevelChanger layerLevelChanger;

	// rendering the ui
	public void render() {
		UiIcons.render();
		selection.render();
		menu.render();
		map.render();
		outline.render();
		layerLevelChanger.render();
		top.render();
	}

	public byte getSpeed() {
		return top.getSpeed();
	}

	private void init(Level[] levels) {
		UiIcons.init();
		menu = new Menu();
		selection = new SelectionSquare();
		map = new Minimap(1290, 8, levels[0]);
		top = new TopBar((Game.width - 270) / 2,5,270,85);
		outline = new BuildOutline(levels);
		layerLevelChanger = new LayerLevelChanger(1320, 210,140,40,levels.length);
	}

	public Ui(Level[] levels) {
		init(levels);
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

	public void showMenu(MenuItem... items) {
		if (!outline.isVisible()) {
			showMenu(MousePosition.getTrueX(), MousePosition.getTrueY());
			menu.addItems(items);
			menu.show();
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

	private void showMenu(int x, int y) {
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

	public void showSelectionSquare() {
		selection.init();
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

	public void showBuildSquare(int xoff, int yoff, boolean lockedSize, BuildingRecipe build, int z) {
		outline.show(xoff, yoff, z, lockedSize, build);
	}

	public void removeBuildSquare() {
		outline.remove();
	}

	public void update(int xOff, int yOff, int z) {
		menu.update(outline.isVisible());
		UiIcons.update();
		outline.update(xOff, yOff, z);
		selection.update();
		top.update();
		layerLevelChanger.update(z);
	}

	public void updateCounts(int solcount, int vilcount) {
		top.updateCounts(solcount, vilcount);
	}

	public void updateMinimap(Level[] level, int z) {
		map.update(level, z);
	}

	public int getZFromLevelChanger() {
		return layerLevelChanger.getZ();
	}

}
