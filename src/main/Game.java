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
import input.Keyboard;
import input.Mouse;
import map.Map;
import map.Tile;
import sound.Sound;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static int width = 400;
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
	private Villager vil;
	private Ui ui;
	private boolean paused = false;
	private double ns = 1000000000.0 / 60.0;

	public int xScroll = 10;
	public int yScroll = 10;

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
		ui = new Ui();
		mouse = new Mouse(this);
		level = new Map(100, 100);
		mobs = new ArrayList<Mob>();
		vills = new ArrayList<Villager>();
		sols = new ArrayList<Villager>();
		vil = new Villager(64, 64, level);
		vil.addClothing(new Clothing("Brown Shirt", vil.x, vil.y, Sprite.brownShirt1, "A brown tshirt", true));
		addVillager(vil);
		addKeyListener(keyboard);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		start();
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

	private void updateMouse() {
		if ((((mouse.getButton() == 1 && UiIcons.isWoodSelected()) && !UiIcons.hoverOnAnyIcon())
				|| vil.isSelected() && mouse.getButton() == 3)
				&& (level.selectTree(mouse.getX(), mouse.getY()) != null)) {
			vil.resetMove();
			vil.addJob(level.selectTree(mouse.getX(), mouse.getY()));
			vil.setSelected(false);
			ui.deSelectIcons();
		}
		if ((((mouse.getButton() == 1 && UiIcons.isMiningSelected()) && !UiIcons.hoverOnAnyIcon())
				|| vil.isSelected() && mouse.getButton() == 3)
				&& (level.selectOre(mouse.getX(), mouse.getY()) != null)) {
			vil.resetMove();
			vil.addJob(level.selectOre(mouse.getX(), mouse.getY()));
			vil.setSelected(false);
			ui.deSelectIcons();

		}
		if ((mouse.getButton() == 1 && UiIcons.isTrowelSelected() && !UiIcons.hoverOnAnyIcon())
				&& level.isClearTile(mouse.getTileX(), mouse.getTileY())) {
			vil.resetMove();
			vil.addBuildJob(mouse.getX(), mouse.getY());
			vil.setSelected(false);
			ui.deSelectIcons();

		}
		if (mouse.getButton() == 1) {
			if (vil.hoverOn(mouse)) {
				vil.setSelected(true);
			} else {
				vil.setSelected(false);
				ui.deSelectIcons();
			}
		}
		if (mouse.getButton() == 3 && vil.isSelected()) {
			vil.resetMove();
			vil.movement = vil.getPath(vil.x >> 4, vil.y >> 4, mouse.getTileX(), mouse.getTileY());
			vil.setSelected(false);
			ui.deSelectIcons();
		}

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

		ui.render(g);

		g.dispose();
		bs.show();

	}

}
