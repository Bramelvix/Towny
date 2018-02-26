package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import com.sun.xml.internal.ws.util.StringUtils;
import entity.Entity;
import entity.Ore;
import entity.Tree;
import entity.item.Clothing;
import entity.item.ClothingType;
import entity.item.Item;
import entity.item.weapon.Weapon;
import entity.item.weapon.WeaponMaterial;
import entity.mob.Mob;
import entity.mob.Villager;
import entity.mob.Zombie;
import entity.mob.work.BuildingRecipe;
import entity.mob.work.CraftJob;
import entity.mob.work.FightJob;
import entity.mob.work.MoveItemJob;
import entity.mob.work.ItemRecipe;
import entity.workstations.Anvil;
import entity.workstations.Furnace;
import graphics.Screen;
import graphics.Sprite;
import graphics.ui.Ui;
import graphics.ui.icon.UiIcons;
import graphics.ui.menu.MenuItem;
import input.Keyboard;
import input.Mouse;
import map.Level;
import map.Tile;
import sound.Sound;

public class Game implements Runnable {

	public static final int width = 500;
	public static final int height = width / 16 * 9;
	private Thread thread;
	public static final int SCALE = 3;
	private Level level;
	private JFrame frame;
	private boolean running = false;
	private Mouse mouse;
	private List<Villager> vills;
	private List<Villager> sols;
	private List<Mob> mobs;
	private Ui ui;
	private boolean paused = false;
	private byte speed = 6;
	private double ns = 100000000.0 / speed;
	private Villager selectedvill;
	public int xScroll = 0;
	public int yScroll = 0;
	private Screen screen;
	private BufferedImage image;
	private Canvas canvas;

	public static void main(String[] args) {
		new Game();

	}

	public Game() {
		canvas = new Canvas();
		Dimension size = new Dimension(width * SCALE, height * SCALE);
		canvas.setPreferredSize(size);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		screen = new Screen(width, height, pixels);
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Towny");
		frame.add(canvas);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		Sound.initSound();
		mouse = new Mouse(this);
		level = new Level(100, 100);
		mobs = new ArrayList<Mob>();
		vills = new ArrayList<Villager>();
		sols = new ArrayList<Villager>();
		ui = new Ui(level);
		spawnvills();
		spawnZombies();

		canvas.addKeyListener(new Keyboard());
		canvas.addMouseListener(mouse);
		canvas.addMouseMotionListener(mouse);
		start();
	}

	private void spawnvills() {
		Villager vil1 = new Villager(144, 144, level);
		vil1.addClothing(new Clothing("Brown Shirt", vil1, Sprite.brownShirt1, "A brown tshirt", ClothingType.SHIRT));
		addVillager(vil1);
		Villager vil2 = new Villager(144, 160, level);
		vil2.addClothing(new Clothing("Green Shirt", vil2, Sprite.greenShirt1, "A green tshirt", ClothingType.SHIRT));
		addVillager(vil2);
		Villager vil3 = new Villager(160, 160, level);
		vil3.addClothing(new Clothing("Green Shirt", vil3, Sprite.greenShirt2, "A green tshirt", ClothingType.SHIRT));
		addVillager(vil3);

	}

	private void spawnZombies() {
		int teller = Entity.RANDOM.nextInt(5) + 1;
		for (int i = 0; i < teller; i++) {
			Zombie zomb = new Zombie(level, Entity.RANDOM.nextInt(256) + 16, Entity.RANDOM.nextInt(256) + 16);
			mobs.add(zomb);
		}
	}

	private void addVillager(Villager vil) {
		if (!vills.contains(vil)) {
			sols.remove(vil);
			vills.add(vil);
			ui.updateCounts(sols.size(), vills.size());
		}
	}

	private void addSoldier(Villager vil) {
		if (!sols.contains(vil)) {
			vills.remove(vil);
			sols.add(vil);
			ui.updateCounts(sols.size(), vills.size());
		}
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0;
		int updates = 0;
		long now = 0;
		int frames = 0;
		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				updateUI();
				if (!paused) {
					update();
					updates++;
				}
				delta--;
				mouse.reset();
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer = System.currentTimeMillis();
				frame.setTitle("Towny - fps: " + frames + " , updates: " + updates);
				updates = 0;
				frames = 0;
			}
		}

	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void update() {
		updateMobs();
		updateMouse();

	}

	private void updateUI() {
		ui.update(mouse, xScroll, yScroll);
		moveCamera();
		if (paused != ui.isPaused()) {
			paused = ui.isPaused();
		}
		if (speed != ui.getSpeed()) {
			speed = ui.getSpeed();
			ns = 100000000.0 / speed;
		}

	}

	private Villager getIdlestVil() {
		Villager lowest = vills.get(0);
		for (Villager i : vills) {
			if (i.getJobSize() < lowest.getJobSize())
				return i;
		}
		return lowest;
	}

	private Villager anyVillHoverOn(int x, int y) {
		for (Villager i : vills) {
			if (i.hoverOn(x, y))
				return i;
		}
		return null;
	}

	private Villager anyVillHoverOn(Mouse mouse) {
		return anyVillHoverOn(mouse.getX(), mouse.getY());
	}

	private Mob anyMobHoverOn(Mouse mouse) {
		return anyMobHoverOn(mouse.getX(), mouse.getY());
	}

	private Mob anyMobHoverOn(int x, int y) {
		for (Mob i : mobs) {
			if (i.hoverOn(x, y))
				return i;
		}
		return null;
	}

	private void deselectAllVills() {
		vills.forEach((Villager i) -> i.setSelected(false));
		selectedvill = null;
	}

	private void deselect(Villager vill) {
		vill.setSelected(false);
		selectedvill = null;
	}

	private void updateMouse() {
		if ((UiIcons.isWoodSelected()) && !UiIcons.hoverOnAnyIcon()) {
			if (mouse.getMouseB() == 1) {
				ui.showSelectionSquare(mouse);
				int x = ui.getSelectionX();
				int y = ui.getSelectionY();
				int width = ui.getSelectionWidth();
				int height = ui.getSelectionHeight();
				for (int xs = x; xs < (x + width); xs += 16) {
					for (int ys = y; ys < (y + height); ys += 16) {
						Tree tree = level.selectTree(xs, ys);
						if (tree != null) {
							tree.setSelected(true);
						}
					}
				}
				return;
			}
			if (mouse.getReleased()) {
				int x = ui.getSelectionX();
				int y = ui.getSelectionY();
				int width = ui.getSelectionWidth();
				int height = ui.getSelectionHeight();
				for (int xs = x; xs < (x + width); xs += 16) {
					for (int ys = y; ys < (y + height); ys += 16) {
						Tree tree = level.selectTree(xs, ys, false);
						if (tree != null) {
							Villager idle = getIdlestVil();
							idle.addJob(tree);
						}
					}
				}
				ui.resetSelection();
				ui.deSelectIcons();
				return;

			}
			return;
		} else if (((UiIcons.isMiningSelected()) && !UiIcons.hoverOnAnyIcon() && mouse.getClickedLeft())
				&& (level.selectOre(mouse.getX(), mouse.getY()) != null)) {
			Villager idle = getIdlestVil();
			idle.setMovement(null);
			deselectAllVills();
			idle.addJob(level.selectOre(mouse.getX(), mouse.getY()));
			ui.deSelectIcons();
			return;

		} else if (((UiIcons.isSwordsSelected()) && !UiIcons.hoverOnAnyIcon() && mouse.getClickedLeft())
				&& (anyMobHoverOn(mouse) != null)) {
			Villager idle = getIdlestVil();
			idle.setMovement(null);
			deselectAllVills();
			idle.addJob(new FightJob(idle, anyMobHoverOn(mouse)));
			ui.deSelectIcons();
			return;

		} else if (mouse.getClickedLeft() && anyVillHoverOn(mouse) != null && !ui.outlineIsVisible()) {
			deselectAllVills();
			selectedvill = anyVillHoverOn(mouse);
			selectedvill.setSelected(true);
			ui.deSelectIcons();
			return;
		} else if (UiIcons.isSawHover() && !ui.menuVisible() && mouse.getClickedLeft()) {
			deselectAllVills();
			MenuItem[] items = new MenuItem[BuildingRecipe.RECIPES.length + 1];
			for (int i = 0; i < BuildingRecipe.RECIPES.length; i++) {
				items[i] = new MenuItem(BuildingRecipe.RECIPES[i]);
			}
			items[items.length - 1] = new MenuItem(MenuItem.CANCEL);
			ui.showMenuOn(mouse, items);
			return;
		} else if (mouse.getClickedRight()) {
			if (selectedvill != null) {
				List<MenuItem> options = new ArrayList<MenuItem>();
				if (selectedvill.getHolding() != null)
					options.add(new MenuItem((MenuItem.DROP + " " + selectedvill.getHolding().getName())));
				Tree boom = level.selectTree(mouse.getX(), mouse.getY());
				if (boom != null) {
					options.add(new MenuItem((MenuItem.CHOP), boom));
				}
				Ore ore = level.selectOre(mouse.getX(), mouse.getY());
				if (ore != null) {
					options.add(new MenuItem((MenuItem.MINE), ore));
				}
				Mob mob = anyMobHoverOn(mouse);
				if (mob != null) {
					options.add(new MenuItem((MenuItem.FIGHT), mob));
				}
				Item item = level.getItemOn(mouse.getX(), mouse.getY());
				if (item != null) {
					if (item instanceof Weapon) {
						options.add(new MenuItem((MenuItem.EQUIP), item));
					} else if (item instanceof Clothing) {
						options.add(new MenuItem((MenuItem.WEAR), item));
					} else {
						options.add(new MenuItem((MenuItem.PICKUP), item));
					}

				}
				options.add(new MenuItem(MenuItem.MOVE));
				options.add(new MenuItem(MenuItem.CANCEL));
				ui.showMenuOn(mouse, options.toArray(new MenuItem[0]));
			} else {
				if (level.getHardEntityOn(mouse.getX(), mouse.getY()) instanceof Furnace) {
					ui.showMenuOn(mouse, new MenuItem(MenuItem.SMELT), new MenuItem(MenuItem.CANCEL));
				} else if (level.getHardEntityOn(mouse.getX(), mouse.getY()) instanceof Anvil) {
					ui.showMenuOn(mouse, new MenuItem(MenuItem.SMITH), new MenuItem(MenuItem.CANCEL));
				} else {
					ui.showMenuOn(mouse, new MenuItem(MenuItem.CANCEL));
				}

			}
		}
		if (ui.outlineIsVisible() && !ui.menuVisible() && mouse.getReleased() && !UiIcons.hoverOnAnyIcon()) {
			int[][] coords = ui.getOutlineCoords();
			for (int[] blok : coords) {
				if (!level.getTile(blok[0] / 16, blok[1] / 16).solid()) {
					Villager idle = getIdlestVil();
					idle.setMovement(null);
					idle.addBuildJob(blok[0], blok[1], ui.getBuildRecipeOutline().getProduct(),
							ui.getBuildRecipeOutline().getResources()[0]);
				}
			}
			ui.removeBuildSquare();
			deselectAllVills();
			ui.deSelectIcons();
			return;
		}
		if (ui.menuVisible()) {
			MenuItem item = ui.getMenu().clickedItem(mouse);
			if (item != null) {
				if (item.getText().contains(MenuItem.CANCEL)) {
					ui.getMenu().hide();
					ui.deSelectIcons();
					if (selectedvill != null) {
						deselect(selectedvill);
					}
					return;
				} else if (item.getText().contains(MenuItem.MOVE)) {
					selectedvill.resetAll();
					selectedvill.setMovement(selectedvill.getPath(mouse.getTileX(), mouse.getTileY()));
					deselect(selectedvill);
					ui.deSelectIcons();
					ui.getMenu().hide();
					return;
				} else if (item.getText().contains(MenuItem.CHOP)) {
					selectedvill.setMovement(null);
					selectedvill.addJob((Tree) item.getEntity());
					deselect(selectedvill);
					ui.deSelectIcons();
					ui.getMenu().hide();
					return;
				} else if (item.getText().contains(MenuItem.FIGHT)) {
					selectedvill.setMovement(null);
					selectedvill.addJob(new FightJob(selectedvill, (Mob) item.getEntity()));
					deselect(selectedvill);
					ui.deSelectIcons();
					ui.getMenu().hide();
					return;
				} else if (item.getText().contains(MenuItem.MINE)) {
					selectedvill.setMovement(null);
					selectedvill.addJob((Ore) item.getEntity());
					deselect(selectedvill);
					ui.deSelectIcons();
					ui.getMenu().hide();
					return;
				} else if (item.getText().contains(MenuItem.BUILD) && !ui.outlineIsVisible()) {
					ui.showBuildSquare(mouse, xScroll, yScroll, false, item.getRecipe());
					ui.deSelectIcons();
					ui.getMenu().hide();
					return;

				} else if ((item.getText().contains(MenuItem.PICKUP) || item.getText().contains(MenuItem.EQUIP)
						|| item.getText().contains(MenuItem.WEAR)) && !ui.outlineIsVisible()) {
					selectedvill.setMovement(null);
					Item e = (Item) item.getEntity();
					if (level.getItemOn(ui.getMenuIngameX(), ui.getMenuIngameY()) != null)
						selectedvill.addJob(new MoveItemJob(e, selectedvill));
					ui.deSelectIcons();
					deselect(selectedvill);
					ui.getMenu().hide();
					return;
				} else if (item.getText().contains(MenuItem.DROP) && !ui.outlineIsVisible()) {
					selectedvill.setMovement(null);
					selectedvill.addJob(new MoveItemJob(ui.getMenuIngameX(), ui.getMenuIngameY(), selectedvill));
					ui.deSelectIcons();
					deselect(selectedvill);
					ui.getMenu().hide();
					return;
				} else if (item.getText().contains(MenuItem.SMELT)) {
					MenuItem[] craftingOptions = new MenuItem[ItemRecipe.FURNACE_RECIPES.length + 1];
					for (int i = 0; i < ItemRecipe.FURNACE_RECIPES.length; i++) {
						craftingOptions[i] = new MenuItem(ItemRecipe.FURNACE_RECIPES[i]);
					}
					craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL);
					ui.showMenuOn(mouse, craftingOptions);
					return;
				} else if (ui.getMenu().clickedOnItem(MenuItem.CRAFT, mouse)) {
					Villager idle = getIdlestVil();
					ItemRecipe recipe = ui.getMenu().recipeFromMenuOption(mouse, MenuItem.CRAFT);
					if (recipe != null) {
						idle.setMovement(null);
						Item[] res = new Item[recipe.getResources().length];
						for (int i = 0; i < res.length; i++) {
							res[i] = idle.getNearestItemOfType(recipe.getResources()[i]);
						}
						idle.addJob(new CraftJob(idle, res, recipe.getProduct(),
								level.getNearestWorkstation(recipe.getWorkstationClass())));
						ui.deSelectIcons();
						ui.getMenu().hide();
					}
					return;

				} else if (ui.getMenu().clickedOnItem(MenuItem.SMITH, mouse)) {
					MenuItem[] craftingOptions = new MenuItem[WeaponMaterial.values().length + 1];
					for (int i = 0; i < WeaponMaterial.values().length; i++) {
						craftingOptions[i] = new MenuItem(
								StringUtils.capitalize(WeaponMaterial.values()[i].toString().toLowerCase()));
					}
					craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL);
					ui.showMenuOn(mouse, craftingOptions);
					return;
				} else if (ui.getMenu().clickedOnItem(MenuItem.WOOD, mouse)) { // TODO This is retarded and needs to be
																				// done
																				// better
					ItemRecipe[] recipes = ItemRecipe.smithingRecipesFromWeaponMaterial(WeaponMaterial.WOOD);
					MenuItem[] craftingOptions = new MenuItem[recipes.length + 1];
					for (int i = 0; i < WeaponMaterial.values().length; i++) {
						craftingOptions[i] = new MenuItem(recipes[i]);
					}
					craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL);
					ui.showMenuOn(mouse, craftingOptions);
					return;
				} else if (ui.getMenu().clickedOnItem(MenuItem.IRON, mouse)) {
					ItemRecipe[] recipes = ItemRecipe.smithingRecipesFromWeaponMaterial(WeaponMaterial.IRON);
					MenuItem[] craftingOptions = new MenuItem[recipes.length + 1];
					for (int i = 0; i < WeaponMaterial.values().length; i++) {
						craftingOptions[i] = new MenuItem(recipes[i]);
					}
					craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL);
					ui.showMenuOn(mouse, craftingOptions);
					return;
				} else if (ui.getMenu().clickedOnItem(MenuItem.GOLD, mouse)) {
					ItemRecipe[] recipes = ItemRecipe.smithingRecipesFromWeaponMaterial(WeaponMaterial.GOLD);
					MenuItem[] craftingOptions = new MenuItem[recipes.length + 1];
					for (int i = 0; i < WeaponMaterial.values().length; i++) {
						craftingOptions[i] = new MenuItem(recipes[i]);
					}
					craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL);
					ui.showMenuOn(mouse, craftingOptions);
					return;
				} else if (ui.getMenu().clickedOnItem(MenuItem.COPPER, mouse)) {
					ItemRecipe[] recipes = ItemRecipe.smithingRecipesFromWeaponMaterial(WeaponMaterial.COPPER);
					MenuItem[] craftingOptions = new MenuItem[recipes.length + 1];
					for (int i = 0; i < WeaponMaterial.values().length; i++) {
						craftingOptions[i] = new MenuItem(recipes[i]);
					}
					craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL);
					ui.showMenuOn(mouse, craftingOptions);
					return;
				} else if (ui.getMenu().clickedOnItem(MenuItem.CRYSTAL, mouse)) {
					ItemRecipe[] recipes = ItemRecipe.smithingRecipesFromWeaponMaterial(WeaponMaterial.CRYSTAL);
					MenuItem[] craftingOptions = new MenuItem[recipes.length + 1];
					for (int i = 0; i < WeaponMaterial.values().length; i++) {
						craftingOptions[i] = new MenuItem(recipes[i]);
					}
					craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL);
					ui.showMenuOn(mouse, craftingOptions);
					return;

				}
			}
		}
	}

	private void moveCamera() {
		if (Keyboard.getKeyPressed(KeyEvent.VK_UP) && yScroll > 0) {
			yScroll -= 2;
		}
		if (Keyboard.getKeyPressed(KeyEvent.VK_DOWN) && yScroll < (level.height * Tile.SIZE) - 1 - height) {
			yScroll += 2;
		}
		if (Keyboard.getKeyPressed(KeyEvent.VK_LEFT) && xScroll > 0) {
			xScroll -= 2;
		}
		if (Keyboard.getKeyPressed(KeyEvent.VK_RIGHT) && xScroll < (level.width * Tile.SIZE) - width - 1) {
			xScroll += 2;
		}

	}

	private void updateMobs() {
		Iterator<Mob> iMob = mobs.iterator();
		while (iMob.hasNext()) {
			Mob i = iMob.next();
			i.update();
			if (i.getHealth() == 0) {
				i.die();
				iMob.remove();
			}
		}
		Iterator<Villager> iVill = vills.iterator();
		while (iVill.hasNext()) {
			Villager i = iVill.next();
			i.update();
			if (i.getHealth() == 0) {
				i.die();
				iVill.remove();
			}
		}
		Iterator<Villager> iSoll = sols.iterator();
		while (iSoll.hasNext()) {
			Villager i = iSoll.next();
			i.update();
			if (i.getHealth() == 0) {
				i.die();
				iSoll.remove();
			}
		}
	}

	private void renderMobs() {
		int x1 = (xScroll + screen.width + Sprite.SIZE);
		int y1 = (yScroll + screen.height + Sprite.SIZE);
		for (Mob i : mobs) {
			if (i.getX() + 16 >= xScroll && i.getX() - 16 <= x1 && i.getY() + 16 >= yScroll && i.getY() - 16 <= y1) {
				i.render(screen);
			}
		}
		for (Villager i : vills) {
			if (i.getX() + 16 >= xScroll && i.getX() - 16 <= x1 && i.getY() + 16 >= yScroll && i.getY() - 16 <= y1) {
				i.render(screen);
			}
		}
		for (Villager i : sols) {
			if (i.getX() + 16 >= xScroll && i.getX() - 16 <= x1 && i.getY() + 16 >= yScroll && i.getY() - 16 <= y1) {
				i.render(screen);
			}
		}
	}

	private void render() {
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}
		screen.clear();
		level.render(xScroll, yScroll, screen);
		renderMobs();
		level.renderHardEntities(xScroll, yScroll, screen);
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		ui.setOffset(xScroll, yScroll);
		ui.render(g);
		g.dispose();
		bs.show();

	}

}
