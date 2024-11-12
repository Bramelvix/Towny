package main;

import entity.Entity;
import entity.dynamic.item.Clothing;
import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.dynamic.item.weapon.Weapon;
import entity.dynamic.item.weapon.WeaponMaterial;
import entity.dynamic.mob.Mob;
import entity.dynamic.mob.Villager;
import entity.dynamic.mob.Zombie;
import entity.dynamic.mob.work.*;
import entity.dynamic.mob.work.recipe.BuildingRecipe;
import entity.dynamic.mob.work.recipe.ItemRecipe;
import entity.non_dynamic.building.container.Chest;
import entity.non_dynamic.building.container.Container;
import entity.non_dynamic.building.container.Workstation;
import entity.non_dynamic.building.farming.TilledSoil;
import entity.non_dynamic.resources.Ore;
import entity.non_dynamic.resources.Tree;
import entity.pathfinding.PathFinder;
import graphics.Sprite;
import graphics.SpriteHashtable;
import graphics.SpritesheetHashtable;
import graphics.TextureInfo;
import graphics.opengl.FontUtils;
import graphics.opengl.OpenGLUtils;
import input.Keyboard;
import input.PointerInput;
import map.Level;
import map.Tile;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.Ui;
import ui.menu.MenuItem;
import util.StringUtils;
import util.vectors.Vec2f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static events.EventListener.onlyWhen;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Game {

	private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
	public static final int WIDTH = 1536;
	public static final int HEIGHT = (int) (WIDTH / 16f * 9f); //864
	public static final int LEVEL_SIZE = 100;
	private Level[] map;
	private ArrayList<Villager> vills;
	private ArrayList<Mob> mobs;
	private Ui ui;
	private boolean paused = false;
	private byte speed = 60;
	private double ns = 1000000000.0 / speed;
	private Villager selectedvill;
	private float xScroll = 0;
	private float yScroll = 0;
	private float dragOffsetX;
	private float dragOffsetY;
	private int currentLayerNumber = 0;
	private long window;
	private PointerInput pointer;

	private GLFWKeyCallback keyCallback;
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWCursorPosCallback cursorPosCallback;
	private GLFWScrollCallback scrollCallback;

	private final Keyboard keyboard = new Keyboard();

	public static void main(String[] args) {
		try {
			new Game();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Game() throws Exception {
		init();
		loop();
		destroy();
	}

	private void init() throws IOException {
		if (!glfwInit()) {
			LOGGER.error("GLFW failed to initialize");
			return;
		}

		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

		window = glfwCreateWindow(WIDTH, HEIGHT, "Towny by Bramelvix", 0, 0);

		if (window == 0) {
			LOGGER.error("Window failed to be created");
			return;
		}
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		if (vidmode == null) {
			LOGGER.error("Vidmode is null");
			return;
		}
		glfwSetWindowPos(window, (vidmode.width() - (WIDTH)) / 2, (vidmode.height() - (HEIGHT)) / 2);
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GL.createCapabilities();
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glfwSwapInterval(0); //0 = VSYNC OFF, 1= VSYNC ON
		setIcon();
		keyCallback = glfwSetKeyCallback(window, keyboard);
		PointerInput.configure(this);
		this.pointer = PointerInput.getInstance();
		mouseButtonCallback = glfwSetMouseButtonCallback(window, pointer.buttonsCallback());
		cursorPosCallback = glfwSetCursorPosCallback(window, pointer.positionCallback());
		scrollCallback = glfwSetScrollCallback(window, this::scroll);


		SpritesheetHashtable.registerSpritesheets();
		SpriteHashtable.registerSprites();
		ItemHashtable.registerItems();

		OpenGLUtils.init();

		//Sound.initSound();
		FontUtils.init();
		generateLevel();
		mobs = new ArrayList<>();
		vills = new ArrayList<>();
		pointer.on(PointerInput.EType.DRAG_START, onlyWhen(
				event -> event.button == GLFW_MOUSE_BUTTON_MIDDLE,
				event -> {
					dragOffsetX = (float) event.x + xScroll;
					dragOffsetY = (float) event.y + yScroll;
				}
		));

		pointer.on(PointerInput.EType.DRAG, onlyWhen(
				event -> event.button == GLFW_MOUSE_BUTTON_MIDDLE,
				event -> {
					int newScrollX = (int) (-event.x + dragOffsetX);
					int newScrollY = (int) (-event.y + dragOffsetY);
					float maxScrollX = (map[currentLayerNumber].width * Tile.SIZE) - (WIDTH + 1);
					float maxScrollY = (map[currentLayerNumber].height * Tile.SIZE) - (HEIGHT + 1);
					xScroll = newScrollX < 0 ? 0 : Math.min(newScrollX, maxScrollX);
					yScroll = newScrollY < 0 ? 0 : Math.min(newScrollY, maxScrollY);
				}
		));

		initUi();

		PathFinder.init(LEVEL_SIZE, LEVEL_SIZE);
		spawnvills();
		spawnZombies();
	}

	private void setIcon() {
		TextureInfo textureInfo = OpenGLUtils.loadTexture("/icons/soldier.png");
		GLFWImage image = GLFWImage.malloc();
		image.set(textureInfo.width(), textureInfo.height(), textureInfo.buffer());
		GLFWImage.Buffer images = GLFWImage.malloc(1);
		images.put(0, image);
		glfwSetWindowIcon(window, images);

		images.free();
		image.free();
	}

	private void generateLevel() {
		map = new Level[20];
		for (int i = 0; i < map.length; i++) {
			map[i] = new Level(LEVEL_SIZE, LEVEL_SIZE, i);
		}
	}

	private void spawnvills() {
		Villager vill = new Villager(432, 432, 0, map);
		vill.addClothing((Clothing) ItemHashtable.get(61));
		vill.addClothing((Clothing) ItemHashtable.get(75));
		addVillager(vill);
		Villager vil2 = new Villager(432, 480, 0, map);
		vil2.addClothing((Clothing) ItemHashtable.get(65));
		vil2.addClothing((Clothing) ItemHashtable.get(74));
		addVillager(vil2);
		Villager vil3 = new Villager(480, 480, 0, map);
		vil3.addClothing((Clothing) ItemHashtable.get(70));
		vil3.addClothing((Clothing) ItemHashtable.get(73));
		addVillager(vil3);
	}

	private void spawnZombies() {
		for (int i = 0; i < Entity.RANDOM.nextInt(5) + 1; i++) {
			mobs.add(new Zombie(map,
					Entity.RANDOM.nextInt(10) * Tile.SIZE,
					Entity.RANDOM.nextInt(10) * Tile.SIZE,
					0
			));
		}
	}

	private void addVillager(Villager vil) {
		if (vills.contains(vil)) {
			return;
		}
		vil.setSoldier(false);
		vills.add(vil);
		ui.updateCounts(vills);

	}

	private void addSoldier(Villager vil) {
		vil.setSoldier(true);
		ui.updateCounts(vills);
	}

	private void loop() {
		long lastTime = System.nanoTime();
		double delta = 0;
		long now;
		GL.createCapabilities();
		while (!glfwWindowShouldClose(window)) {
			now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				glfwPollEvents();
				updateUI();
				if (!paused) {
					updateMobs();
				}
				delta--;
			}

			draw();
		}
	}

	private void destroy() {
		destroyCallbacks();
		glfwDestroyWindow(window);
		FontUtils.destroy();
		SpritesheetHashtable.destroy();
		ui.destroy();
		OpenGLUtils.destroy();
		glfwTerminate();
	}

	private void destroyCallbacks() {
		if (mouseButtonCallback != null) {
			mouseButtonCallback.free();
		}
		if (scrollCallback != null) {
			scrollCallback.free();
		}
		if (keyCallback != null) {
			keyCallback.free();
		}
		if (cursorPosCallback != null) {
			cursorPosCallback.free();
		}
		glfwFreeCallbacks(window);
	}

	private void draw() {
		OpenGLUtils.clearOutlines();
		OpenGLUtils.clearAllInstanceData();

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.2f, 0.2f, 0.2f, 0.2f);
		Vec2f scroll = new Vec2f(xScroll, yScroll);
		map[currentLayerNumber].render(scroll);
		renderMobs();

		OpenGLUtils.drawInstanced(OpenGLUtils.getInstanceData(), scroll);
		OpenGLUtils.drawOutlines(scroll);
		ui.render();
		glfwSwapBuffers(window);
		OpenGLUtils.checkGLError();
	}

	private void updateUI() {
		ui.update(currentLayerNumber, xScroll, yScroll);
		updateMouse();
		moveCamera();
		pointer.resetLeftAndRight();
	}

	private void initUi() throws IOException {
		ui = new Ui(map, (xs, ys, z) -> map[z].selectTree(xs, ys), (xs, ys, z) -> map[z].selectOre(xs, ys), this);
		ui.initLayerLevelChangerActions(this::onClickLayerUp, this::onClickLayerDown);
		ui.initTopBarActions(this::onClickPause, this::onClickupSpeed, this::onClickdownSpeed);
		ui.getIcons().setShovelOnClick(this::onClickShovel);
		ui.getIcons().setPlowOnclick(this::onClickPlow);
		ui.getIcons().setSawOnClick(this::onClickSaw);
		ui.updateSpeed(speed);
		ui.initSelectionSquareRelease(resource -> getIdlestVil().addJob(resource));
		ui.getIcons().setSwordsOnMouseReleaseWhenSelected(this::onClickWhileSwordSelected);
	}

	private void onClickLayerUp() {
		if (this.currentLayerNumber != 0) {
			this.currentLayerNumber--;
			ui.updateMinimap(map, currentLayerNumber);
		}
	}

	private void onClickLayerDown() {
		if (this.currentLayerNumber != map.length - 1) {
			this.currentLayerNumber++;
			ui.updateMinimap(map, currentLayerNumber);
		}
	}

	private void onClickPause() {
		if (paused) {
			paused = false;
			speed = 60;
		} else {
			paused = true;
			speed = 20;
		}
		ui.updateSpeed(speed);

	}

	private void onClickupSpeed() {
		if (speed < 90) {
			speed += 10;
			ui.updateSpeed(speed);
			ns = 1000000000.0 / speed;
		}
	}

	private void onClickdownSpeed() {
		if (speed > 30) {
			speed -= 10;
			ui.updateSpeed(speed);
			ns = 1000000000.0 / speed;
		}
	}

	private void onClickShovel() {
		if (ui.menuInvisible()) {
			deselectAllVills();
			ui.showBuildSquare(
					true, currentLayerNumber, xScroll, yScroll,
					pos -> onClickBuildOutline(pos, BuildingRecipe.STAIRS_DOWN)
			);
		}
	}

	private void onClickPlow() {
		if (ui.menuInvisible()) {
			ui.showBuildSquare(
					false, currentLayerNumber, xScroll, yScroll,
					pos -> onClickBuildOutline(pos, BuildingRecipe.TILLED_SOIL)
			);
		}
	}

	private void onClickBuild(MenuItem item) {
		ui.showBuildSquare(
				false, currentLayerNumber, xScroll, yScroll,
				pos -> onClickBuildOutline(pos, item.getRecipe(BuildingRecipe.class))
		);
		ui.getMenu().hide();
	}

	private void onClickSaw() {
		if (ui.menuInvisible()) {
			deselectAllVills();
			MenuItem[] items = new MenuItem[BuildingRecipe.RECIPES.length + 1];
			for (int i = 0; i < BuildingRecipe.RECIPES.length; i++) {
				items[i] = new MenuItem(BuildingRecipe.RECIPES[i], this::onClickBuild);
			}
			items[items.length - 1] = new MenuItem(MenuItem.CANCEL, this::onClickCancel);
			ui.showMenu(items);
		}
	}

	private void onClickWhileSwordSelected() {
		Villager idle = getIdlestVil();
		deselectAllVills();
		anyMobHoverOn().ifPresent(mob -> idle.addJob(new FightJob(idle, mob)));
		ui.deSelectIcons();
	}

	private void onClickMove() {
		selectedvill.resetAll();
		selectedvill.addJob(new MoveJob(selectedvill, pointer.getTileX(), pointer.getTileY(), currentLayerNumber));
		deselect(selectedvill);
		ui.deSelectIcons();
		ui.getMenu().hide();
	}

	private void onClickPickup(MenuItem item) {
		selectedvill.setPath(null);
		Item e = item.getEntity(Item.class);
		selectedvill.addJob(new PickUpItemJob(selectedvill, e));
		ui.deSelectIcons();
		deselect(selectedvill);
		ui.getMenu().hide();

	}

	private void onClickChop(MenuItem item) {
		selectedvill.setPath(null);
		selectedvill.addJob(item.getEntity(Tree.class));
		deselect(selectedvill);
		ui.deSelectIcons();
		ui.getMenu().hide();
	}

	private void onClickFarm(MenuItem item) {
		selectedvill.setPath(null);
		selectedvill.addJob(item.getEntity(TilledSoil.class));
		deselect(selectedvill);
		ui.deSelectIcons();
		ui.getMenu().hide();
	}

	private void onClickSmelt() {
		MenuItem[] craftingOptions = new MenuItem[ItemRecipe.FURNACE_RECIPES.length + 1];
		for (int i = 0; i < ItemRecipe.FURNACE_RECIPES.length; i++) {
			craftingOptions[i] = new MenuItem(ItemRecipe.FURNACE_RECIPES[i], this::onClickCraft);
		}
		craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL, this::onClickCancel);
		ui.showMenu(craftingOptions);

	}

	private void onClickCraft(MenuItem item) {
		Villager idle = getIdlestVil();
		ItemRecipe recipe = item.getRecipe(ItemRecipe.class);
		idle.setPath(null);
		Item[] res = new Item[recipe.getResources().length];
		for (int i = 0; i < res.length; i++) {
			res[i] = idle.getNearestItemOfType(recipe.getResources()[i]).orElse(null);
		}
		map[currentLayerNumber].getNearestWorkstation(
				recipe.getWorkstationType(), idle.getTileX(), idle.getTileY()
		).ifPresent(station -> idle.addJob(new CraftJob(idle, res, recipe.getProduct(), station)));
		ui.deSelectIcons();
		ui.getMenu().hide();
	}

	private void onClickSmith() {
		MenuItem[] craftingOptions = new MenuItem[WeaponMaterial.values().length + 1];
		for (int i = 0; i < WeaponMaterial.values().length; i++) {
			craftingOptions[i] = new MenuItem(StringUtils.capitalise(WeaponMaterial.values()[i].toString().toLowerCase()), this::showMaterials);
		}
		craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL, this::onClickCancel);
		ui.showMenu(craftingOptions);
	}

	private void showMaterials(MenuItem item) {
		ItemRecipe[] recipes = ItemRecipe.smithingRecipesFromWeaponMaterial(WeaponMaterial.valueOf(item.getText().toUpperCase()));
		MenuItem[] craftingOptions = new MenuItem[recipes.length + 1];
		for (int i = 0; i < WeaponMaterial.values().length; i++) {
			craftingOptions[i] = new MenuItem(recipes[i], this::onClickCraft);
		}
		craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL, this::onClickCancel);
		ui.showMenu(craftingOptions);

	}

	private void onClickMine(MenuItem item) {
		selectedvill.setPath(null);
		selectedvill.addJob(item.getEntity(Ore.class));
		deselect(selectedvill);
		ui.deSelectIcons();
		ui.getMenu().hide();
	}

	private void onClickDrop() {
		selectedvill.setPath(null);
		selectedvill.addJob(new DropItemJob(selectedvill,
				(int) ((ui.getMenu().getX() + xScroll) / Tile.SIZE),
				(int) ((ui.getMenu().getY() + yScroll) / Tile.SIZE),
				currentLayerNumber
		));
		ui.deSelectIcons();
		deselect(selectedvill);
		ui.getMenu().hide();
	}

	private void onClickCancel() {
		ui.getMenu().hide();
		ui.deSelectIcons();
		ui.removeBuildSquare();
		if (selectedvill != null) {
			deselect(selectedvill);
		}
	}

	private void onClickFight(MenuItem item) {
		selectedvill.setPath(null);
		selectedvill.addJob(new FightJob(selectedvill, item.getEntity(Mob.class)));
		deselect(selectedvill);
		ui.deSelectIcons();
		ui.getMenu().hide();
	}

	private void onClickBuildOutline(float[][] coords, BuildingRecipe recipe) {
		for (float[] blok : coords) {
			if (map[currentLayerNumber].tileIsEmpty((int) (blok[0] / Tile.SIZE), (int) (blok[1] / Tile.SIZE))) {
				Villager idle = getIdlestVil();
				idle.addBuildJob(
						(int) (blok[0] / Tile.SIZE),
						(int) (blok[1] / Tile.SIZE),
						currentLayerNumber,
						recipe.getProduct(),
						recipe.getResources()[0]
				);
			}
		}
		ui.removeBuildSquare();
		deselectAllVills();
		ui.deSelectIcons();
	}

	private void scroll(long window, double v, double v1) {
		if ((currentLayerNumber <= map.length - 1 && v1 > 0) || (currentLayerNumber >= 0 && v1 < 0)) {
			currentLayerNumber -= (int) v1;
			if (currentLayerNumber < 0) {
				currentLayerNumber = 0;
			} else if (currentLayerNumber > map.length - 1) {
				currentLayerNumber = map.length - 1;
			}
			ui.updateMinimap(map, currentLayerNumber);
		}
	}

	private Villager getIdlestVil() {
		Villager lowest = vills.getFirst();
		for (Villager i : vills) {
			if (i.getJobSize() < lowest.getJobSize()) {
				lowest = i;
			}
		}
		return lowest;
	}

	private Optional<Villager> anyVillHoverOn() {
		return vills.stream().filter(villager -> villager.hoverOn(pointer, currentLayerNumber)).findAny();
	}

	private Optional<Mob> anyMobHoverOn() {
		return mobs.stream().filter(mob -> mob.hoverOn(pointer, currentLayerNumber)).findAny();
	}

	private void deselectAllVills() {
		vills.forEach(this::deselect);
	}

	private void deselect(Villager vill) {
		vill.setSelected(false);
		selectedvill = null;
	}

	private void updateMouse() {
		if (pointer.wasPressed(GLFW_MOUSE_BUTTON_LEFT)) {
			if (!ui.outlineIsVisible()) {
				anyVillHoverOn().ifPresent(villager -> {
					deselectAllVills();
					selectedvill = villager;
					selectedvill.setSelected(true);
					ui.deSelectIcons();
				});
			}
		} else if (pointer.wasPressed(GLFW_MOUSE_BUTTON_RIGHT)) {
			if (selectedvill != null) {
				List<MenuItem> options = new ArrayList<>();
				if (selectedvill.getHolding() != null && (map[currentLayerNumber].tileIsEmpty(pointer.getTileX(), pointer.getTileY()) || map[currentLayerNumber].getEntityOn(pointer.getTileX(), pointer.getTileY(), Chest.class).isPresent())) {
					options.add(new MenuItem(MenuItem.defaultText(MenuItem.DROP, selectedvill.getHolding()), this::onClickDrop));
				}

				map[currentLayerNumber].selectTree(pointer.getTileX(), pointer.getTileY()).ifPresent(
						tree -> options.add(new MenuItem(MenuItem.defaultText(MenuItem.CHOP, tree), tree, this::onClickChop))
				);

				map[currentLayerNumber].selectTilledSoil(pointer.getTileX(), pointer.getTileY()).ifPresent(
						plot -> {
							if (!plot.isPlanted()) {
								options.add(new MenuItem((MenuItem.SOW), plot, this::onClickFarm));
							} else if (plot.isReadyToHarvest()) {
								options.add(new MenuItem((MenuItem.HARVEST), plot, this::onClickFarm));
							}
						}
				);

				map[currentLayerNumber].selectOre(pointer.getTileX(), pointer.getTileY()).ifPresent(
						ore -> options.add(new MenuItem(MenuItem.defaultText(MenuItem.MINE, ore), ore, this::onClickMine))
				);

				anyMobHoverOn().ifPresent(mob -> options.add(new MenuItem(MenuItem.defaultText(MenuItem.FIGHT, mob), mob, this::onClickFight)));

				map[currentLayerNumber].getItemOn(pointer.getTileX(), pointer.getTileY()).ifPresent(item -> {
					if (item instanceof Weapon) {
						options.add(new MenuItem(MenuItem.defaultText(MenuItem.EQUIP, item), item, this::onClickPickup));
					} else if (item instanceof Clothing) {
						options.add(new MenuItem(MenuItem.defaultText(MenuItem.WEAR, item), item, this::onClickPickup));
					} else {
						options.add(new MenuItem(MenuItem.defaultText(MenuItem.PICKUP, item), item, this::onClickPickup));
					}
				});

				Optional<Container> container = map[currentLayerNumber].getEntityOn(pointer.getTileX(), pointer.getTileY(), Container.class);
				if (container.isPresent()) {
					container.get().getItemList().forEach(item ->
							options.add(new MenuItem(MenuItem.defaultText(MenuItem.PICKUP, item), item, this::onClickPickup))
					);
				} else {
					options.add(new MenuItem(MenuItem.MOVE, this::onClickMove));
				}
				options.add(new MenuItem(MenuItem.CANCEL, this::onClickCancel));
				ui.showMenu(options.toArray(new MenuItem[0]));
			} else {
				if (map[currentLayerNumber].getWorkstationOn(pointer.getTileX(), pointer.getTileY(), Workstation.Type.FURNACE).isPresent()) {
					ui.showMenu(
							new MenuItem(MenuItem.SMELT, this::onClickSmelt),
							new MenuItem(MenuItem.CANCEL, this::onClickCancel)
					);
				} else if (map[currentLayerNumber].getWorkstationOn(pointer.getTileX(), pointer.getTileY(), Workstation.Type.ANVIL).isPresent()) {
					ui.showMenu(
							new MenuItem(MenuItem.SMITH, this::onClickSmith),
							new MenuItem(MenuItem.CANCEL, this::onClickCancel)
					);
				} else {
					ui.showMenu(new MenuItem(MenuItem.CANCEL, this::onClickCancel));
				}
			}
		}
	}

	private void moveCamera(int xScroll, int yScroll) {
		this.xScroll += xScroll;
		this.yScroll += yScroll;
	}

	private float getMaxYScrolLValue() {
		return ((LEVEL_SIZE * Tile.SIZE) - 1 - HEIGHT);
	}

	private float getMaxXScrollValue() {
		return ((LEVEL_SIZE * Tile.SIZE) - WIDTH - 1);
	}

	private void moveCamera() {
		int yScroll = 0;
		int xScroll = 0;
		if (keyboard.isKeyDown(GLFW_KEY_UP) && this.yScroll > 0) {
			yScroll = Math.max(-6, -(int) this.yScroll);
		}
		float maxYScrollValue = getMaxYScrolLValue();
		if (keyboard.isKeyDown(GLFW_KEY_DOWN) && this.yScroll < maxYScrollValue) {
			yScroll = Math.min(6, (int) maxYScrollValue - (int) this.yScroll);
		}

		if (keyboard.isKeyDown(GLFW_KEY_LEFT) && this.xScroll > 0) {
			xScroll = Math.max(-6, -(int) this.xScroll);
		}

		float maxXScrollValue = getMaxXScrollValue();
		if (keyboard.isKeyDown(GLFW_KEY_RIGHT) && this.xScroll < maxXScrollValue) {
			xScroll = Math.min(6, (int) maxXScrollValue - (int) this.xScroll);
		}
		moveCamera(xScroll, yScroll);
	}

	private <T extends Mob> void update(Iterator<T> iterator) {
		T mob = iterator.next();
		mob.update();
		if (mob.getHealth() <= 0) {
			mob.die();
			iterator.remove();
		}
	}

	private void updateMobs() {
		Iterator<Mob> iMob = mobs.iterator();
		while (iMob.hasNext()) {
			update(iMob);
		}
		Iterator<Villager> iVill = vills.iterator();
		while (iVill.hasNext()) {
			update(iVill);
		}
	}

	private void renderMobs() {
		float x1 = (xScroll + WIDTH + Sprite.SIZE);
		float y1 = (yScroll + HEIGHT + Sprite.SIZE);

		mobs.stream().filter(mob -> inBounds(mob, x1, y1)).forEach(Entity::render);
		vills.stream().filter(mob -> inBounds(mob, x1, y1)).forEach(Entity::render);
	}

	private boolean inBounds(Entity e, float x1, float y1) {
		return e.getZ() == currentLayerNumber && e.getX() + Tile.SIZE >= xScroll && e.getX() - Tile.SIZE <= x1 && e.getY() + Tile.SIZE >= yScroll && e.getY() - Tile.SIZE <= y1;
	}

	public float getxScroll() {
		return xScroll;
	}

	public float getyScroll() {
		return yScroll;
	}

	public void setXScroll(float value) {
		xScroll = Math.clamp(value, 0, getMaxXScrollValue());
	}

	public void setYScroll(float value) {
		yScroll = Math.clamp(value, 0, getMaxYScrolLValue());
	}
}
