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
import java.util.Random;

import javax.swing.JFrame;

import entity.BuildAbleObjects;
import entity.Tree;
import entity.Wall;
import entity.item.Clothing;
import entity.item.ClothingType;
import entity.item.weapon.Weapon;
import entity.mob.Mob;
import entity.mob.Villager;
import entity.mob.Zombie;
import entity.mob.work.FightJob;
import entity.mob.work.MoveItemJob;
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

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static final int width = 500;
	private static final int height = width / 16 * 9;
	private Thread thread;
	private static final int SCALE = 3;
	private Level level;
	private JFrame frame;
	private boolean running = false;
	private Mouse mouse;
	private Keyboard keyboard;
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
	private int[] pixels;
	private Random rand;

	public static void main(String[] args) {
		new Game();

	}

	public Game() {
		Dimension size = new Dimension(width * SCALE, height * SCALE);
		rand = new Random();
		setPreferredSize(size);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		screen = new Screen(width, height, pixels);
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Towny");
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		Sound.initSound();
		keyboard = new Keyboard();
		mouse = new Mouse(this);
		level = new Level(100, 100);
		mobs = new ArrayList<Mob>();
		vills = new ArrayList<Villager>();
		sols = new ArrayList<Villager>();
		ui = new Ui(level);
		spawnvills();
		spawnZombies();

		addKeyListener(keyboard);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		start();
	}

	private void spawnvills() {
		Villager vil = new Villager(144, 144, level);
		vil.addClothing(new Clothing("Brown Shirt", vil, Sprite.brownShirt1, "A brown tshirt", ClothingType.SHIRT));
		addVillager(vil);
		Villager vil2 = new Villager(144, 160, level);
		vil2.addClothing(new Clothing("Green Shirt", vil, Sprite.greenShirt1, "A green tshirt", ClothingType.SHIRT));
		addVillager(vil2);
		Villager vil3 = new Villager(160, 160, level);
		vil3.addClothing(new Clothing("Green Shirt", vil3, Sprite.greenShirt2, "A green tshirt", ClothingType.SHIRT));
		addVillager(vil3);
	}

	private void spawnZombies() {
		int teller = rand.nextInt(5) + 1;
		for (int i = 0; i < teller; i++) {
			Zombie zomb = new Zombie(level, rand.nextInt(256) + 16, rand.nextInt(256) + 16);
			mobs.add(zomb);
		}
	}

	private void addVillager(Villager vil) {
		if (!vills.contains(vil)) {
			if (sols.contains(vil))
				sols.remove(vil);
			vills.add(vil);
			ui.updateCounts(sols.size(), vills.size());
		}
	}

	private void addSoldier(Villager vil) {
		if (!sols.contains(vil)) {
			if (vills.contains(vil))
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
			if (mouse.getButton() == 1) {
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
							idle.resetMove();
							idle.addJob(tree);
						}
					}
				}
				ui.resetSelection();
				ui.deSelectIcons();
				return;

			}
			return;
		}
		if (((UiIcons.isMiningSelected()) && !UiIcons.hoverOnAnyIcon() && mouse.getClicked())
				&& (level.selectOre(mouse.getX(), mouse.getY()) != null)) {
			Villager idle = getIdlestVil();
			idle.resetMove();
			deselectAllVills();
			idle.addJob(level.selectOre(mouse.getX(), mouse.getY()));
			ui.deSelectIcons();
			return;

		}
		if (((UiIcons.isSwordsSelected()) && !UiIcons.hoverOnAnyIcon() && mouse.getClicked())
				&& (anyMobHoverOn(mouse.getX(), mouse.getY()) != null)) {
			Villager idle = getIdlestVil();
			idle.resetMove();
			deselectAllVills();
			idle.addJob(new FightJob(idle, anyMobHoverOn(mouse.getX(), mouse.getY())));
			ui.deSelectIcons();
			return;

		}
		if (mouse.getClicked() && anyVillHoverOn(mouse.getX(), mouse.getY()) != null && !ui.outlineIsVisible()) {
			deselectAllVills();
			selectedvill = anyVillHoverOn(mouse.getX(), mouse.getY());
			selectedvill.setSelected(true);
			ui.deSelectIcons();
			return;
		}
		if (UiIcons.isSawHover() && !ui.menuVisible() && mouse.getClicked()) {
			deselectAllVills();
			ui.showMenuOn(mouse,
					new String[] { MenuItem.BUILD + " wooden wall", MenuItem.BUILD + " furnace", MenuItem.CANCEL });
			return;
		}

		if (mouse.getButton() == 3) {
			if (selectedvill != null) {
				List<String> options = new ArrayList<String>();
				if (selectedvill.holding != null) {
					options.add(MenuItem.getMenuItemText(MenuItem.DROP, selectedvill.holding));
				}
				if (level.selectTree(mouse.getX(), mouse.getY()) != null) {
					options.add(MenuItem.getMenuItemText(MenuItem.CHOP, level.selectTree(mouse.getX(), mouse.getY())));
				} else {
					if (level.selectOre(mouse.getX(), mouse.getY()) != null) {
						options.add(
								MenuItem.getMenuItemText(MenuItem.MINE, level.selectOre(mouse.getX(), mouse.getY())));
					} else {
						if (anyMobHoverOn(mouse.getX(), mouse.getY()) != null) {
							options.add(MenuItem.getMenuItemText(MenuItem.FIGHT,
									anyMobHoverOn(mouse.getX(), mouse.getY())));
						}
						if (level.getItemOn(mouse.getX(), mouse.getY()) != null) {
							if (level.getItemOn(mouse.getX(), mouse.getY()) instanceof Weapon) {
								options.add(MenuItem.getMenuItemText(MenuItem.EQUIP,
										level.getItemOn(mouse.getX(), mouse.getY())));
							} else {
								if (level.getItemOn(mouse.getX(), mouse.getY()) instanceof Clothing) {
									options.add(MenuItem.getMenuItemText(MenuItem.WEAR,
											level.getItemOn(mouse.getX(), mouse.getY())));
								} else {
									options.add(MenuItem.getMenuItemText(MenuItem.PICKUP,
											level.getItemOn(mouse.getX(), mouse.getY())));
								}
							}
						}
					}
				}
				options.add(MenuItem.MOVE);
				options.add(MenuItem.CANCEL);
				ui.showMenuOn(mouse, options.toArray(new String[0]));

			} else {
				ui.showMenuOn(mouse, MenuItem.CANCEL);
			}
		}
		if (ui.outlineIsVisible() && !ui.menuVisible() && mouse.getReleased() && !UiIcons.hoverOnAnyIcon()) {
			int[][] coords = ui.getOutlineCoords();
			for (int[] blok : coords) {
				if (!level.getTile(blok[0] >> 4, blok[1] >> 4).solid()) {
					Villager idle = getIdlestVil();
					idle.resetMove();
					idle.addBuildJob(blok[0], blok[1],ui.getBuildAbleObjectOutline());
				}
			}
			ui.removeBuildSquare();
			deselectAllVills();
			ui.deSelectIcons();
		}
		if (ui.menuVisible()) {
			if (ui.getMenu().clickedOnItem(MenuItem.CANCEL, mouse)) {
				ui.getMenu().hide();
				ui.deSelectIcons();
				if (selectedvill != null) {
					deselect(selectedvill);
				}
				return;
			}
			if (ui.getMenu().clickedOnItem(MenuItem.MOVE, mouse)) {
				selectedvill.resetAll();
				selectedvill.movement = selectedvill.getPath(mouse.getTileX(), mouse.getTileY());
				if (selectedvill.movement == null) {
					selectedvill.movement = selectedvill.getShortest(level.getEntityOn(mouse.getX(), mouse.getY()));
				}
				deselect(selectedvill);
				ui.deSelectIcons();
				ui.getMenu().hide();
				return;
			}
			if (ui.getMenu().clickedOnItem(MenuItem.CHOP, mouse)) {
				selectedvill.resetMove();
				selectedvill.addJob(level.selectTree(ui.getMenuIngameX(), ui.getMenuIngameY()));
				deselect(selectedvill);
				ui.deSelectIcons();
				ui.getMenu().hide();
				return;
			}
			if (ui.getMenu().clickedOnItem(MenuItem.FIGHT, mouse)) {
				selectedvill.resetMove();
				selectedvill
						.addJob(new FightJob(selectedvill, anyMobHoverOn(ui.getMenuIngameX(), ui.getMenuIngameY())));
				deselect(selectedvill);
				ui.deSelectIcons();
				ui.getMenu().hide();
				return;
			}
			if (ui.getMenu().clickedOnItem(MenuItem.MINE, mouse)) {
				selectedvill.resetMove();
				selectedvill.addJob(level.selectOre(ui.getMenuIngameX(), ui.getMenuIngameY()));
				deselect(selectedvill);
				ui.deSelectIcons();
				ui.getMenu().hide();
				return;
			}
			if (ui.getMenu().clickedOnItem(MenuItem.BUILD + " wooden wall", mouse) && !ui.outlineIsVisible()) {
				ui.showBuildSquare(mouse, xScroll, yScroll, false, BuildAbleObjects.WOODEN_WALL);
				ui.deSelectIcons();
				ui.getMenu().hide();
				return;
			}
			if (ui.getMenu().clickedOnItem(MenuItem.BUILD + " furnace", mouse) && !ui.outlineIsVisible()) {
				ui.showBuildSquare(mouse, xScroll, yScroll, true, BuildAbleObjects.FURNACE);
				ui.deSelectIcons();
				ui.getMenu().hide();
				return;
			}
			if ((ui.getMenu().clickedOnItem(MenuItem.PICKUP, mouse) || ui.getMenu().clickedOnItem(MenuItem.EQUIP, mouse)
					|| ui.getMenu().clickedOnItem(MenuItem.WEAR, mouse)) && !ui.outlineIsVisible()) {
				selectedvill.resetMove();
				if (level.getItemOn(ui.getMenuIngameX(), ui.getMenuIngameY()) != null)
					selectedvill.addJob(
							new MoveItemJob(level.getItemOn(ui.getMenuIngameX(), ui.getMenuIngameY()), selectedvill));
				ui.deSelectIcons();
				deselect(selectedvill);
				ui.getMenu().hide();
				return;
			}
			if (ui.getMenu().clickedOnItem(MenuItem.DROP, mouse) && !ui.outlineIsVisible()) {
				selectedvill.resetMove();
				selectedvill.addJob(new MoveItemJob(ui.getMenuIngameX(), ui.getMenuIngameY(), selectedvill));
				ui.deSelectIcons();
				deselect(selectedvill);
				ui.getMenu().hide();
				return;
			}
		}
		return;

	}

	private void moveCamera() {
		if (Keyboard.getKeyPressed(KeyEvent.VK_UP) && yScroll > 0)
			yScroll -= 2;
		if (Keyboard.getKeyPressed(KeyEvent.VK_DOWN) && yScroll < (level.height * Tile.SIZE) - 1 - height)
			yScroll += 2;
		if (Keyboard.getKeyPressed(KeyEvent.VK_LEFT) && xScroll > 0)
			xScroll -= 2;
		if (Keyboard.getKeyPressed(KeyEvent.VK_RIGHT) && xScroll < (level.width * Tile.SIZE) - width - 1)
			xScroll += 2;

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
			Mob i = iVill.next();
			i.update();
			if (i.getHealth() == 0) {
				i.die();
				iVill.remove();
			}
		}
		Iterator<Villager> iSoll = sols.iterator();
		while (iSoll.hasNext()) {
			Mob i = iSoll.next();
			i.update();
			if (i.getHealth() == 0) {
				i.die();
				iSoll.remove();
			}
		}
	}

	private void renderMobs() {
		mobs.forEach((Mob i) -> i.render(screen));
		vills.forEach((Villager i) -> i.render(screen));
		sols.forEach((Villager i) -> i.render(screen));
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		level.render(xScroll, yScroll, screen);
		level.renderSoftEntities(screen);
		renderMobs();
		level.renderHardEntites(screen);
		pixels = screen.pixels;
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		ui.setOffset(xScroll, yScroll);
		ui.render(g);
		g.dispose();
		bs.show();

	}

}
