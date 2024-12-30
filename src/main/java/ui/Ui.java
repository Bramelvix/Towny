package ui;

import entity.dynamic.mob.Villager;
import entity.nondynamic.resources.Ore;
import entity.nondynamic.resources.Resource;
import entity.nondynamic.resources.Tree;
import main.Game;
import map.Level;
import map.Tile;
import ui.icon.UiIcons;
import ui.menu.Menu;
import ui.menu.MenuItem;
import util.TriFunction;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

//main class used to manage the ui
public class Ui {

	// different ui elements
	private Menu menu;
	private final TopBar top;
	private final Minimap minimap;
	private final BuildOutline outline;
	private final SelectionSquare selection;
	private TriFunction<Integer, Integer, Integer, Optional<? extends Resource>> activeResourceSelector;
	private final LayerLevelChanger layerLevelChanger;
	private final UiIcons icons;
	private int z;

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

	public Ui(Level[] levels, TriFunction<Integer, Integer, Integer, Optional<Tree>> treeSelector, TriFunction<Integer, Integer, Integer, Optional<Ore>> oreSelector, Game game) throws IOException {
		z = 0;
		icons = new UiIcons(0.176056338028169f);
		menu = new Menu();
		selection = new SelectionSquare();
		minimap = new Minimap(1290, 8, levels[0], game);
		top = new TopBar((Game.WIDTH - 270) / 2f, 5, 270, 85);
		outline = new BuildOutline(levels);
		layerLevelChanger = new LayerLevelChanger(1320, 210, 140, 40);

		getIcons().setAxeOnClick(() -> {
			showSelectionSquare();
			activeResourceSelector = treeSelector::apply;
		});

		getIcons().setMiningOnClick(() -> {
			showSelectionSquare();
			activeResourceSelector = oreSelector::apply;
		});
	}

	public void initSelectionSquareRelease(Consumer<Resource> resourceTaker) {
		selection.setOnMouseRelease(() -> {
			int x = (int) (getSelectionX() / Tile.SIZE);
			int y = (int) (getSelectionY() / Tile.SIZE);
			int width = Math.round(getSelectionWidth() / Tile.SIZE);
			int height = Math.round(getSelectionHeight() / Tile.SIZE);
			for (int xs = x; xs < (x + width); xs++) {
				for (int ys = y; ys < (y + height); ys++) {
					activeResourceSelector.apply(xs, ys, z).ifPresent(resourceTaker);
				}
			}
			resetSelection();
			deSelectIcons();
		}, () -> getIcons().hoverOnNoIcons());
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
		this.z = z;
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
