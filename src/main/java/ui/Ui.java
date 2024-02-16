package ui;

import entity.dynamic.mob.Villager;
import main.Game;
import map.Level;
import ui.icon.UiIcons;
import ui.menu.Menu;
import ui.menu.MenuItem;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Consumer;

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

	public Ui(Level[] levels, Game game) throws IOException {
		icons = new UiIcons(0.176056338028169f);
		menu = new Menu();
		selection = new SelectionSquare();
		minimap = new Minimap(1290, 8, levels[0], game);
		top = new TopBar((Game.WIDTH - 270) / 2f, 5, 270, 85);
		outline = new BuildOutline(levels);
		layerLevelChanger = new LayerLevelChanger(1320, 210, 140, 40);
	}

	public void initLayerLevelChangerActions(Runnable actionup, Runnable actionDown) {
		layerLevelChanger.init(actionup, actionDown);
	}

	public void initTopBarActions(Runnable toggle, Runnable upSpeed, Runnable downSpeed) {
		top.init(toggle, upSpeed, downSpeed);
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

	public void showMenu(MenuItem... items) {
		menu = new Menu();
		menu.addItems(items);
		menu.setVisible(true);
	}

	public boolean menuInvisible() {
		return !menu.isVisible();
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

	public void showBuildSquare(boolean lockedSize, int z, float xScroll, float yScroll, Consumer<float[][]> consumer) {
		outline.show(z, xScroll, yScroll, lockedSize, consumer, event -> icons.hoverOnNoIcons());
	}

	public void removeBuildSquare() {
		outline.remove();
	}

	public void update(int z, float xScroll, float yScroll) {
		selection.update(xScroll, yScroll);
		outline.update(z, xScroll, yScroll);
		minimap.update();
		layerLevelChanger.setZ(z);
	}

	public void updateCounts(int solcount, int vilcount) {
		top.updateCounts(solcount, vilcount);
	}

	public void updateCounts(Collection<Villager> villagers) {
		updateCounts((int) villagers.stream().filter(Villager::isSoldier).count(), villagers.size());
	}

	public void updateMinimap(Level[] level, int z) {
		minimap.update(level, z);
	}

	public UiIcons getIcons() {
		return icons;
	}

	public void destroy() {
		icons.destroy();
		layerLevelChanger.destroy();
		minimap.destroy();
		top.destroy();
	}

}
