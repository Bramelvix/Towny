package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import entity.Entity;
import entity.dynamic.mob.work.*;
import entity.dynamic.mob.work.recipe.BuildingRecipe;
import entity.dynamic.mob.work.recipe.ItemRecipe;
import entity.nonDynamic.building.container.Chest;
import entity.nonDynamic.building.container.Container;
import entity.nonDynamic.building.container.Workstation;
import entity.nonDynamic.building.farming.TilledSoil;
import entity.nonDynamic.resources.Ore;
import entity.nonDynamic.resources.Tree;
import entity.dynamic.item.Clothing;
import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.dynamic.item.weapon.Weapon;
import entity.dynamic.item.weapon.WeaponMaterial;
import entity.dynamic.mob.Mob;
import entity.dynamic.mob.Villager;
import entity.dynamic.mob.Zombie;
import entity.pathfinding.PathFinder;
import graphics.*;
import graphics.opengl.OpenGLUtils;
import graphics.opengl.FontUtils;
import graphics.ui.Ui;
import graphics.ui.menu.MenuItem;
import input.Keyboard;
import input.PointerInput;
import map.Level;
import map.Tile;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.StringUtils;
import graphics.TextureInfo;
import util.vectors.Vec2f;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL11.*;
import static events.EventListener.onlyWhen;

public class Game {

	private final Logger logger = LoggerFactory.getLogger(Game.class);
	public static final int width = 1536;
	public static final int height = (int)(width / 16f * 9f); //864
	private Level[] map;
	private ArrayList<Villager> vills;
	private ArrayList<Villager> sols;
	private ArrayList<Mob> mobs;
	private Ui ui;
	private boolean paused = false;
	private byte speed = 60;
	private double ns = 1000000000.0 / speed;
	private Villager selectedvill;
	public float xScroll = 0;
	public float yScroll = 0;
	private float dragOffsetX;
	private float dragOffsetY;
	private int currentLayerNumber = 0;
	private long window;
	private PointerInput pointer;

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
	}

	private void init() throws Exception {
		if (!glfwInit()) {
			logger.error("GLFW failed to initialize");
			return;
		}

		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);

		window = glfwCreateWindow(width, height, "Towny by Bramelvix", 0, 0);

		if (window == 0) {
			logger.error("Window failed to be created");
			return;
		}
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		if (vidmode == null) {
			logger.error("Vidmode is null");
			return;
		}
		glfwSetWindowPos(window, (vidmode.width() - (width)) / 2, (vidmode.height() - (height )) / 2);
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GL.createCapabilities();
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glfwSwapInterval(0); //0 = VSYNC OFF, 1= VSYNC ON
		setIcon();
		glfwSetKeyCallback(window, new Keyboard());
		PointerInput.configure (this);
		this.pointer = PointerInput.getInstance ();
		glfwSetMouseButtonCallback(window, pointer.buttonsCallback ());
		glfwSetCursorPosCallback(window, pointer.positionCallback ());
		glfwSetScrollCallback(window, this::scroll);



		SpritesheetHashtable.registerSpritesheets();
		SpriteHashtable.registerSprites();
		ItemHashtable.registerItems();

		OpenGLUtils.init();

		//Sound.initSound();
		FontUtils.init();
		generateLevel();
		mobs = new ArrayList<>();
		vills = new ArrayList<>();
		sols = new ArrayList<>();
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
				int newScrollX = (int)(- event.x + dragOffsetX);
				int newScrollY = (int)(- event.y + dragOffsetY);
				float maxScrollX = (map[currentLayerNumber].width * Tile.SIZE) - (width+1);
				float maxScrollY = (map[currentLayerNumber].height * Tile.SIZE) - (height+1);
				xScroll = newScrollX < 0 ? 0 : Math.min(newScrollX, maxScrollX);
				yScroll = newScrollY < 0 ? 0 : Math.min(newScrollY, maxScrollY);
			}
		));

		initUi();

		PathFinder.init(100, 100);
		spawnvills();
		spawnZombies();
	}

	private void setIcon() {
		TextureInfo textureInfo = OpenGLUtils.loadTexture("/icons/soldier.png");
		GLFWImage image = GLFWImage.malloc();
		image.set(textureInfo.width, textureInfo.height, textureInfo.buffer);
		GLFWImage.Buffer images = GLFWImage.malloc(1);
		images.put(0, image);
		glfwSetWindowIcon(window, images);

		images.free();
		image.free();
	}

	private void generateLevel() {
		map = new Level[20];
		for (int i = 0; i < map.length; i++) {
			map[i] = new Level(100, 100, i);
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
		if (vills.contains(vil)) { return; }
		sols.remove(vil);
		vills.add(vil);
		ui.updateCounts(sols.size(), vills.size());

	}

	private void addSoldier(Villager vil) {
		if (sols.contains(vil)) { return; }
		vills.remove(vil);
		sols.add(vil);
		ui.updateCounts(sols.size(), vills.size());

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
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		FontUtils.release();
		SpritesheetHashtable.destroy();
		ui.destroy();
		OpenGLUtils.destroy();
		glfwTerminate();
	}

	private void draw() {
		OpenGLUtils.clearOutlines();
		OpenGLUtils.clearAllInstanceData();

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.2f, 0.2f, 0.2f, 0.2f);
		Vec2f scroll = new Vec2f(xScroll, yScroll);
		map[currentLayerNumber].render(scroll);
		renderMobs();

		OpenGLUtils.drawInstanced(OpenGLUtils.instanceData, Sprite.SIZE, scroll);
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
		ui = new Ui(map, pointer);
		ui.initLayerLevelChangerActions(pointer, this::onClickLayerUp, this::onClickLayerDown);
		ui.initTopBarActions(pointer, this::onClickPause, this::onClickupSpeed, this::onClickdownSpeed);
		ui.getIcons().setShovelOnClick(pointer, this::onClickShovel);
		ui.getIcons().setPlowOnclick(pointer, this::onClickPlow);
		ui.getIcons().setSawOnClick(pointer, this::onClickSaw);
		ui.updateSpeed(speed);
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
			speed+=10;
			ui.updateSpeed(speed);
			ns = 1000000000.0 / speed;
		}
	}

	private void onClickdownSpeed() {
		if (speed > 30) {
			speed-=10;
			ui.updateSpeed(speed);
			ns = 1000000000.0 / speed;
		}
	}

	private void onClickShovel() {
		if (ui.menuInvisible()) {
			deselectAllVills();
			ui.showBuildSquare(
				true, currentLayerNumber, xScroll, yScroll, pointer,
				pos -> onClickBuildOutline(pos, BuildingRecipe.STAIRSDOWN)
			);
			ui.deSelectIcons();
		}
	}

	private void onClickPlow() {
		if (ui.menuInvisible()){
			ui.showBuildSquare(
				false, currentLayerNumber, xScroll, yScroll, pointer,
				pos -> onClickBuildOutline(pos, BuildingRecipe.TILLED_SOIL)
			);
			ui.deSelectIcons();
		}
	}

	private void onClickBuild(MenuItem item) {
		ui.showBuildSquare(
			false, currentLayerNumber, xScroll, yScroll, pointer,
			pos -> onClickBuildOutline(pos, item.getRecipe())
		);
		ui.deSelectIcons();
		ui.getMenu().hide();
	}

	private void onClickSaw() {
		if (ui.menuInvisible()) {
			deselectAllVills();
			MenuItem[] items = new MenuItem[BuildingRecipe.RECIPES.length + 1];
			for (int i = 0; i < BuildingRecipe.RECIPES.length; i++) {
				items[i] = new MenuItem(BuildingRecipe.RECIPES[i], this::onClickBuild, pointer);
			}
			items[items.length - 1] = new MenuItem(MenuItem.CANCEL, in -> onClickCancel(), pointer);
			ui.showMenu(pointer, items);
		}
	}

	private void onClickMove() {
		selectedvill.resetAll();
		selectedvill.addJob(new MoveJob(pointer.getTileX(), pointer.getTileY(), currentLayerNumber, selectedvill));
		deselect(selectedvill);
		ui.deSelectIcons();
		ui.getMenu().hide();
	}

	private void onClickPickup(MenuItem item) {
		selectedvill.setPath(null);
		Item e = (Item) item.getEntity();
		selectedvill.addJob(new MoveItemJob(e, selectedvill));
		ui.deSelectIcons();
		deselect(selectedvill);
		ui.getMenu().hide();

	}

	private void onClickChop(MenuItem item) {
		selectedvill.setPath(null);
		selectedvill.addJob((Tree) item.getEntity());
		deselect(selectedvill);
		ui.deSelectIcons();
		ui.getMenu().hide();
	}

	private void onClickFarm(MenuItem item) {
		selectedvill.setPath(null);
		selectedvill.addJob((TilledSoil) item.getEntity());
		deselect(selectedvill);
		ui.deSelectIcons();
		ui.getMenu().hide();
	}

	private void onClickSmelt() {
		MenuItem[] craftingOptions = new MenuItem[ItemRecipe.FURNACE_RECIPES.length + 1];
		for (int i = 0; i < ItemRecipe.FURNACE_RECIPES.length; i++) {
			craftingOptions[i] = new MenuItem(ItemRecipe.FURNACE_RECIPES[i], this::onClickCraft, pointer);
		}
		craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL, in -> onClickCancel(), pointer);
		ui.showMenu(pointer, craftingOptions);

	}

	private void onClickCraft(MenuItem item) {
		Villager idle = getIdlestVil();
		item.getRecipe(ItemRecipe.class).ifPresent(recipe -> {
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
		});
	}

	private void onClickSmith() {
		MenuItem[] craftingOptions = new MenuItem[WeaponMaterial.values().length + 1];
		for (int i = 0; i < WeaponMaterial.values().length; i++) {
			craftingOptions[i] = new MenuItem(StringUtils.capitalise(WeaponMaterial.values()[i].toString().toLowerCase()), this::showMaterials, pointer);
		}
		craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL, in -> onClickCancel(), pointer);
		ui.showMenu(pointer, craftingOptions);
	}

	private void showMaterials(MenuItem item) {
		ItemRecipe[] recipes = ItemRecipe.smithingRecipesFromWeaponMaterial(WeaponMaterial.valueOf(item.getText().toUpperCase()));
		MenuItem[] craftingOptions = new MenuItem[recipes.length + 1];
		for (int i = 0; i < WeaponMaterial.values().length; i++) {
			craftingOptions[i] = new MenuItem(recipes[i], this::onClickCraft, pointer);
		}
		craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL, in -> onClickCancel(), pointer);
		ui.showMenu(pointer, craftingOptions);

	}

	private void onClickMine(MenuItem item) {
		selectedvill.setPath(null);
		selectedvill.addJob((Ore) item.getEntity());
		deselect(selectedvill);
		ui.deSelectIcons();
		ui.getMenu().hide();
	}

	private void onClickDrop() {
		selectedvill.setPath(null);
		selectedvill.addJob(new MoveItemJob(
			(int) ((ui.getMenu().getX() + xScroll) / Tile.SIZE),
			(int) ((ui.getMenu().getY() + yScroll) / Tile.SIZE),
			currentLayerNumber,
			selectedvill
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
		selectedvill.addJob(new FightJob(selectedvill, (Mob) item.getEntity()));
		deselect(selectedvill);
		ui.deSelectIcons();
		ui.getMenu().hide();
	}

	private void onClickBuildOutline(float[][] coords, BuildingRecipe recipe) {
		if (ui.getIcons().hoverOnNoIcons()) {
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
	}

	private void scroll(long window, double v, double v1) {
		if ((currentLayerNumber <= map.length - 1 && v1 > 0) || (currentLayerNumber >= 0 && v1 < 0)) {
			currentLayerNumber -= v1;
			if (currentLayerNumber < 0) {
				currentLayerNumber = 0;
			} else if (currentLayerNumber > map.length - 1) {
				currentLayerNumber = map.length - 1;
			}
			ui.updateMinimap(map, currentLayerNumber);
		}
	}

	private Villager getIdlestVil() {
		Villager lowest = vills.get(0);
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
		if ((ui.getIcons().isAxeSelected()) && ui.getIcons().hoverOnNoIcons()) {
			if (pointer.wasPressed(GLFW_MOUSE_BUTTON_LEFT)) {
				ui.showSelectionSquare();
				return;
			}
			if (pointer.wasReleased(GLFW_MOUSE_BUTTON_LEFT)) {
				int x = (int) (ui.getSelectionX() / Tile.SIZE);
				int y = (int) (ui.getSelectionY() / Tile.SIZE);
				int width = Math.round(ui.getSelectionWidth() / Tile.SIZE);
				int height = Math.round(ui.getSelectionHeight() / Tile.SIZE);
				for (int xs = x; xs < (x + width); xs ++) {
					for (int ys = y; ys < (y + height); ys++) {
						map[currentLayerNumber].selectTree(xs, ys).ifPresent(tree -> getIdlestVil().addJob(tree));
					}
				}
				ui.resetSelection();
				ui.deSelectIcons();
			}
			return;
		}
		if (pointer.wasPressed(GLFW_MOUSE_BUTTON_LEFT)) {
			if (ui.getIcons().isMiningSelected() && ui.getIcons().hoverOnNoIcons()) {
				map[currentLayerNumber].selectOre(pointer.getTileX(), pointer.getTileY()).ifPresent(ore -> {
					Villager idle = getIdlestVil();
					deselectAllVills();
					idle.addJob(ore);
					ui.deSelectIcons();
				});
			}
			if (((ui.getIcons().isSwordsSelected()) && ui.getIcons().hoverOnNoIcons())) {
				Villager idle = getIdlestVil();
				deselectAllVills();
				anyMobHoverOn().ifPresent(mob -> idle.addJob(new FightJob(idle, mob)));
				ui.deSelectIcons();
				return;

			}
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
					options.add(new MenuItem(MenuItem.defaultText(MenuItem.DROP, selectedvill.getHolding()), in -> onClickDrop(), pointer));
				}

				map[currentLayerNumber].selectTree(pointer.getTileX(), pointer.getTileY()).ifPresent(
					tree -> options.add(new MenuItem(MenuItem.defaultText(MenuItem.CHOP, tree), tree, this::onClickChop, pointer))
				);

				map[currentLayerNumber].selectTilledSoil(pointer.getTileX(), pointer.getTileY()).ifPresent(
					plot -> {
						if (!plot.isPlanted()) {
							options.add(new MenuItem((MenuItem.SOW), plot, this::onClickFarm, pointer));
						} else if (plot.isReadyToHarvest()) {
							options.add(new MenuItem((MenuItem.HARVEST), plot, this::onClickFarm, pointer));
						}
					}
				);

				map[currentLayerNumber].selectOre(pointer.getTileX(), pointer.getTileY()).ifPresent(
					ore ->  options.add(new MenuItem(MenuItem.defaultText(MenuItem.MINE, ore), ore, this::onClickMine, pointer))
				);

				anyMobHoverOn().ifPresent(mob -> options.add(new MenuItem(MenuItem.defaultText(MenuItem.FIGHT, mob), mob, this::onClickFight, pointer)));

				map[currentLayerNumber].getItemOn(pointer.getTileX(), pointer.getTileY()).ifPresent(item -> {
					if (item instanceof Weapon) {
						options.add(new MenuItem(MenuItem.defaultText(MenuItem.EQUIP, item), item, this::onClickPickup, pointer));
					} else if (item instanceof Clothing) {
						options.add(new MenuItem(MenuItem.defaultText(MenuItem.WEAR, item), item, this::onClickPickup, pointer));
					} else {
						options.add(new MenuItem(MenuItem.defaultText(MenuItem.PICKUP, item), item, this::onClickPickup, pointer));
					}
				});

				Optional<Container> container = map[currentLayerNumber].getEntityOn(pointer.getTileX(), pointer.getTileY(), Container.class);
				if (container.isPresent()) {
					container.get().getItemList().forEach(item ->
						options.add(new MenuItem(MenuItem.defaultText(MenuItem.PICKUP, item), item, this::onClickPickup, pointer))
					);
				} else {
					options.add(new MenuItem(MenuItem.MOVE, in -> onClickMove(), pointer));
				}
				options.add(new MenuItem(MenuItem.CANCEL, in -> onClickCancel(), pointer));
				ui.showMenu(pointer, options.toArray(new MenuItem[0]));
			} else {
				if (map[currentLayerNumber].getWorkstationOn(pointer.getTileX(), pointer.getTileY(), Workstation.Type.FURNACE).isPresent()) {
					ui.showMenu(
						pointer,
						new MenuItem(MenuItem.SMELT, in -> onClickSmelt(), pointer),
						new MenuItem(MenuItem.CANCEL, in -> onClickCancel(), pointer)
					);
				} else if (map[currentLayerNumber].getWorkstationOn(pointer.getTileX(), pointer.getTileY(), Workstation.Type.ANVIL).isPresent()) {
					ui.showMenu(
						pointer,
						new MenuItem(MenuItem.SMITH, in -> onClickSmith(), pointer),
						new MenuItem(MenuItem.CANCEL, in -> onClickCancel(), pointer)
					);
				} else {
					ui.showMenu(pointer, new MenuItem(MenuItem.CANCEL, in -> onClickCancel(), pointer));
				}
			}
		}
	}

	private void moveCamera(int xScroll, int yScroll) {
		this.xScroll += xScroll;
		this.yScroll += yScroll;
		ui.setOffset(this.xScroll, this.yScroll);
	}

	private void moveCamera() {
		int _yScroll = 0;
		int _xScroll = 0;
		if (Keyboard.isKeyDown(GLFW_KEY_UP) && yScroll > 1) {
			_yScroll -= 6;
		}
		if (Keyboard.isKeyDown(GLFW_KEY_DOWN) && yScroll < ((map[currentLayerNumber].height * Tile.SIZE) - 1 - height)) {
			_yScroll += 6;
		}
		if (Keyboard.isKeyDown(GLFW_KEY_LEFT) && xScroll > 1) {
			_xScroll -= 6;
		}
		if (Keyboard.isKeyDown(GLFW_KEY_RIGHT) && xScroll < ((map[currentLayerNumber].width * Tile.SIZE) - width - 1)) {
			_xScroll += 6;
		}
		moveCamera(_xScroll, _yScroll);
	}

	private <T extends Mob> void update(T mob, Iterator<T> iterator) {
		mob.update();
		if (mob.getHealth() == 0) {
			mob.die();
			iterator.remove();
		}
	}

	private void updateMobs() {
		Iterator<Mob> iMob = mobs.iterator();
		while (iMob.hasNext()) {
			update(iMob.next(), iMob);
		}
		Iterator<Villager> iVill = vills.iterator();
		while (iVill.hasNext()) {
			update(iVill.next(), iVill);
		}
		Iterator<Villager> iSoll = sols.iterator();
		while (iSoll.hasNext()) {
			update(iSoll.next(), iSoll);
		}
	}

	private void renderMobs() {
		float x1 = (xScroll + width + Sprite.SIZE);
		float y1 = (yScroll + height + Sprite.SIZE);

		mobs.forEach(mob -> mob.renderIf(
			inBounds(mob.getX(), mob.getY(), mob.getZ(), currentLayerNumber, xScroll, x1, yScroll , y1)
		));

		vills.forEach(vil -> vil.renderIf(
			inBounds(vil.getX(), vil.getY(), vil.getZ(), currentLayerNumber, xScroll, x1, yScroll , y1)
		));

		sols.forEach(sol -> sol.renderIf(
			inBounds(sol.getX(), sol.getY(), sol.getZ(), currentLayerNumber, xScroll, x1, yScroll , y1)
		));

	}

	private boolean inBounds(float x, float y, int z, int layer, float xScroll, float x1, float yScroll, float y1) {
		return z == layer && x + Tile.SIZE >= xScroll && x - Tile.SIZE <= x1 && y + Tile.SIZE >= yScroll && y - Tile.SIZE <= y1;
	}

}
