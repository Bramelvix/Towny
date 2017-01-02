package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import entity.item.Clothing;
import entity.mob.Mob;
import entity.mob.Villager;
import graphics.Screen;
import graphics.Sprite;
import graphics.ui.Ui;
import graphics.ui.icon.UiIcons;
import graphics.ui.menu.MenuItemType;
import input.Keyboard;
import input.Mouse;
import map.Map;
import map.Tile;
import sound.Sound;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static int width = 500;
	private static int height = width / 16 * 9;
	private Thread thread;
	private static final int SCALE = 3;
	private Map level;
	private JFrame frame;
	private boolean running = false;
	private Mouse mouse;
	private Keyboard keyboard;
	public List<Villager> vills;
	public List<Villager> sols;
	public List<Mob> mobs;
	private Ui ui;
	private boolean paused = false;
	private double ns = 1000000000.0 / 60.0;
	private Villager selectedvill;

	public int xScroll = 0;
	public int yScroll = 0;

	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public static void main(String[] args) {
		new Game();

	}

	public Game() {
		Dimension size = new Dimension(width * SCALE, height * SCALE);
		setPreferredSize(size);
		screen = new Screen(width, height);
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
		level = new Map(100, 100);
		mobs = new ArrayList<Mob>();
		vills = new ArrayList<Villager>();
		sols = new ArrayList<Villager>();
		ui = new Ui(level);
		spawnvills();

		addKeyListener(keyboard);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		start();
	}

	private void spawnvills() {
		Villager vil = new Villager(64, 64, level);
		vil.addClothing(new Clothing("Brown Shirt", vil.x, vil.y, Sprite.brownShirt1, "A brown tshirt", true));
		addVillager(vil);
		Villager vil2 = new Villager(32, 64, level);
		vil2.addClothing(new Clothing("Green Shirt", vil2.x, vil2.y, Sprite.greenShirt1, "A green tshirt", true));
		addVillager(vil2);
		Villager vil3 = new Villager(32, 32, level);
		vil3.addClothing(new Clothing("Green Shirt", vil2.x, vil2.y, Sprite.greenShirt2, "A green tshirt", true));
		addVillager(vil3);
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
		thread = new Thread(this, "Display");
		thread.start();
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0;
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				if (!paused) {
					update();
					updates++;
				}
				updateUI();
				delta--;
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
		ui.update(mouse);
		moveCamera();
		paused = ui.paused;

	}

	private Villager getIdlestVil() {
		Villager lowest = vills.get(0);
		for (Villager i : vills) {
			if (i.getJobSize() < lowest.getJobSize())
				return i;
		}
		return lowest;
	}

	private Villager anyVillHoverOn(Mouse mouse) {
		for (Villager i : vills) {
			if (i.hoverOn(mouse))
				return i;
		}
		return null;
	}

	private void deselectAllVills() {
		for (Villager i : vills) {
			i.setSelected(false);
		}
	}

	private void updateMouse() {
		if ((mouse.getButton() == 1 && UiIcons.isWoodSelected()) && !UiIcons.hoverOnAnyIcon()
				&& (level.selectTree(mouse.getX(), mouse.getY()) != null)) {
			Villager idle = getIdlestVil();
			idle.resetMove();
			idle.addJob(level.selectTree(mouse.getX(), mouse.getY()));
			ui.deSelectIcons();
			return;
		}
		if (((mouse.getButton() == 1 && UiIcons.isMiningSelected()) && !UiIcons.hoverOnAnyIcon())
				&& (level.selectOre(mouse.getX(), mouse.getY()) != null)) {
			Villager idle = getIdlestVil();
			idle.resetMove();
			idle.addJob(level.selectOre(mouse.getX(), mouse.getY()));
			ui.deSelectIcons();
			return;

		}
		if (anyVillHoverOn(mouse) != null) {
			if (mouse.getButton() == 1) {
				selectedvill = anyVillHoverOn(mouse);
				selectedvill.setSelected(true);
			}
			ui.deSelectIcons();
			return;
		}
		if (!ui.menuVisible() && UiIcons.isTrowelHover() && mouse.getClicked()) {
			ui.showMenuOn(mouse.getTrueXPixels(), mouse.getTrueYPixels(),
					new MenuItemType[] { MenuItemType.BUILD_WALL, MenuItemType.CANCEL });
		}

		if (mouse.getButton() == 3) {
			if (selectedvill != null) {
				if (level.selectTree(mouse.getX(), mouse.getY()) != null) {
					ui.showMenuOn(mouse.getTrueXPixels(), mouse.getTrueYPixels(),
							new MenuItemType[] { MenuItemType.CHOP, MenuItemType.MOVE, MenuItemType.CANCEL });
				} else {
					if (level.selectOre(mouse.getX(), mouse.getY()) != null) {
						ui.showMenuOn(mouse.getTrueXPixels(), mouse.getTrueYPixels(),
								new MenuItemType[] { MenuItemType.MINE, MenuItemType.MOVE, MenuItemType.CANCEL });
					} else {

						ui.showMenuOn(mouse.getTrueXPixels(), mouse.getTrueYPixels(),
								new MenuItemType[] { MenuItemType.MOVE, MenuItemType.CANCEL });
					}
				}

			} else {
				ui.showMenuOn(mouse.getTrueXPixels(), mouse.getTrueYPixels(), MenuItemType.CANCEL);
			}
		}
		if (ui.outlineIsVisible() && !ui.menuVisible() && mouse.getClicked()
				&& !level.getTile(ui.getOutlineX() >> 4, ui.getOutlineY() >> 4).solid()) {
			Villager idle = getIdlestVil();
			idle.resetMove();
			idle.addBuildJob(ui.getOutlineX(), ui.getOutlineY());
			ui.removeBuildSquare();
			ui.deSelectIcons();
		}
		if (ui.menuVisible()) {
			if (ui.getMenu().clickedOnItem(MenuItemType.CANCEL, mouse)) {
				ui.getMenu().hide();
				ui.deSelectIcons();
				return;
			}
			if (ui.getMenu().clickedOnItem(MenuItemType.MOVE, mouse)) {
				selectedvill.resetMove();
				selectedvill.movement = selectedvill.getPath(selectedvill.x >> 4, selectedvill.y >> 4, mouse.getTileX(),
						mouse.getTileY());
				if (selectedvill.movement == null) {
					selectedvill.movement = selectedvill.getShortest(level.getEntityOn(mouse.getX(), mouse.getY()));
				}
				selectedvill.setSelected(false);
				ui.deSelectIcons();
				ui.getMenu().hide();
				return;
			}
			if (ui.getMenu().clickedOnItem(MenuItemType.CHOP, mouse)) {
				selectedvill.resetMove();
				selectedvill.addJob(level.selectTree(mouse.getX(), mouse.getY()));
				selectedvill.setSelected(false);
				ui.deSelectIcons();
				ui.getMenu().hide();
				return;
			}
			if (ui.getMenu().clickedOnItem(MenuItemType.MINE, mouse)) {
				selectedvill.resetMove();
				selectedvill.addJob(level.selectOre(mouse.getX(), mouse.getY()));
				selectedvill.setSelected(false);
				ui.deSelectIcons();
				ui.getMenu().hide();
				return;
			}
			if (ui.getMenu().clickedOnItem(MenuItemType.BUILD_WALL, mouse) && !ui.outlineIsVisible()) {
				ui.showBuildSquare();
				ui.deSelectIcons();
				ui.getMenu().hide();
				return;
			}
		}
		return;

	}

	private void moveCamera() {
		if (keyboard.up && yScroll > 0)
			yScroll -= 2;
		if (keyboard.down && yScroll < (level.height * Tile.SIZE) - 1 - height)
			yScroll += 2;
		if (keyboard.left && xScroll > 0)
			xScroll -= 2;
		if (keyboard.right && xScroll < (level.width * Tile.SIZE) - width - 1)
			xScroll += 2;

	}

	private void updateMobs() {
		mobs.forEach((Mob i) -> i.update());
		vills.forEach((Villager i) -> i.update());
		sols.forEach((Villager i) -> i.update());
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
		level.renderItems(screen);
		renderMobs();
		level.renderEntites(screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		ui.setOffset(xScroll, yScroll);
		ui.render(g);
		g.dispose();
		bs.show();

	}

}
