package graphics.ui;

import entity.dynamic.mob.work.recipe.BuildingRecipe;
import graphics.ui.icon.UiIcons;
import graphics.ui.menu.Menu;
import graphics.ui.menu.MenuItem;
import input.PointerInput;
import main.Game;
import map.Level;

import java.io.IOException;

//main class used to manage the ui
public class Ui {

	// different ui elements
	private Menu menu;
	private final TopBar top;
	private final Minimap minimap;
	private final BuildOutline outline;
	private final SelectionSquare selection;
	private final LayerLevelChanger layerLevelChanger;
	private final UiIcons icons;

	// rendering the ui
	public void render() {
		icons.render(); //texShader
		selection.render(); //colShader
		minimap.render();  //texShader + colShader
		outline.render(); //colShader
		menu.render(); //colShader + fontShader
		layerLevelChanger.render(); //colShader + fontShader
		top.render(); //colShader + texShader + fontShader
	}

	public Ui(Level[] levels, PointerInput pointer) throws IOException {
		icons = new UiIcons( 0.176056338028169f, pointer);
		menu = new Menu(pointer);
		selection = new SelectionSquare(pointer);
		minimap = new Minimap(1290, 8, levels[0]);
		top = new TopBar((Game.width - 270) / 2f,5,270,85, pointer);
		outline = new BuildOutline(levels, pointer);
		layerLevelChanger = new LayerLevelChanger(1320, 210,140,40, pointer);
	}

	public void initLayerLevelChangerActions(PointerInput pointer, Runnable actionup, Runnable actionDown) {
		layerLevelChanger.init(pointer, actionup, actionDown);
	}

	public void initTopBarActions(PointerInput pointer, Runnable toggle, Runnable upSpeed, Runnable downSpeed) {
		top.init(pointer, toggle, upSpeed, downSpeed);
	}

	public float[][] getOutlineCoords() {
		return outline.getSquareCoords();
	}

	public boolean outlineIsVisible() {
		return outline.isVisible();
	}

	public Menu getMenu() {
		return menu;
	}

	public void updateSpeed(int speed) {
		top.updateSpeed(speed);
	}

	public void deSelectIcons() {
		icons.deSelect();
	}

	public void showMenu(PointerInput pointer, MenuItem... items) {
		showMenu(pointer);
		menu.addItems(items);
		menu.setVisible(true);
	}

	public BuildingRecipe getBuildRecipeOutline() {
		return outline.getBuildRecipe();
	}

	private void showMenu(PointerInput pointer) {
		menu = new Menu(pointer);
	}

	public boolean menuInvisible() {
		return !menu.isVisible();
	}

	public void setOffset(float x, float y) {
		minimap.setOffset(x, y);
	}

	public void showSelectionSquare() {
		selection.show();
	}

	public void resetSelection() {
		selection.reset();
	}

	public float getSelectionX() {
		return selection.getX();
	}

	public float getSelectionY() {
		return selection.getY();
	}

	public float getSelectionWidth() {
		return selection.getWidth();
	}

	public float getSelectionHeight() {
		return selection.getHeight();
	}

	public void showBuildSquare(boolean lockedSize, BuildingRecipe build, int z, float xScroll, float yScroll) {
		outline.show(z, xScroll, yScroll, lockedSize, build);
	}

	public void removeBuildSquare() {
		outline.remove();
	}

	public void update(int z, float xScroll, float yScroll) {
		selection.update(xScroll, yScroll);
		outline.update(z, xScroll, yScroll);
		layerLevelChanger.setZ(z);
	}

	public void updateCounts(int solcount, int vilcount) {
		top.updateCounts(solcount, vilcount);
	}

	public void updateMinimap(Level[] level, int z) {
		minimap.update(level, z);
	}

	public UiIcons getIcons () {
		return icons;
	}

	public void destroy() {
		icons.destroy();
		layerLevelChanger.destroy();
		minimap.destroy();
		top.destroy();
	}

}
