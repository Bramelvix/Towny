package graphics.ui;

import entity.dynamic.mob.work.BuildingRecipe;
import graphics.ui.icon.UiIcons;
import graphics.ui.menu.Menu;
import graphics.ui.menu.MenuItem;
import input.PointerInput;
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
	public void render(float xOffset, float yOffset) {
		UiIcons.render();
		selection.render(xOffset,yOffset);
		menu.render();
		map.render();
		outline.render(0, 0);
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

	public void showMenu(PointerInput pointer, MenuItem... items) {
		if (!outline.isVisible()) {
			showMenu(pointer.getTrueX(), pointer.getTrueY());
			menu.addItems(items);
			menu.show(pointer);
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

	public void showSelectionSquare(PointerInput pointer) {
		selection.init(pointer);
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

	public void showBuildSquare(PointerInput pointer, int xoff, int yoff, boolean lockedSize, BuildingRecipe build, int z) {
		outline.show(pointer, xoff, yoff, z, lockedSize, build);
	}

	public void removeBuildSquare() {
		outline.remove();
	}

	public void update(PointerInput pointer, int xOff, int yOff, int z) {
		menu.update(pointer, outline.isVisible());
		UiIcons.update(pointer);
		outline.update(pointer, xOff, yOff, z);
		selection.update(pointer);
		top.update(pointer);
		layerLevelChanger.update(pointer, z);
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
