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

import entity.Wall;
import entity.item.Clothing;
import entity.mob.Mob;
import entity.mob.Villager;
import graphics.Screen;
import graphics.Sprite;
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
	private int scale = 3;
	private Map level;
	private JFrame frame;
	private boolean running = false;
	private Mouse mouse;
	private Keyboard keyboard;
	public List<Mob> mobs;
	Villager vil;

	public int xScroll = 10;
	public int yScroll = 10;

	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public static void main(String[] args) {
		new Game();

	}

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		screen = new Screen(width, height);
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Towns");
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		Sound.initSound();
		keyboard = new Keyboard();
		UiIcons.init();
		mouse = new Mouse(this);
		level = new Map(100, 100);
		mobs = new ArrayList<Mob>();
		vil = new Villager(64, 64, level);
		vil.addClothing(new Clothing("Brown Shirt", vil.x, vil.y, Sprite.brownShirt1, "A brown tshirt", true));
		mobs.add(vil);
		addKeyListener(keyboard);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		start();
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		double delta = 0;
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer = System.currentTimeMillis();
				frame.setTitle("Towns - fps: " + frames + " , updates: " + updates);
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
		moveCamera();
		UiIcons.update(mouse);

	}

	private void updateMouse() {
		if ((((mouse.getButton() == 1 && UiIcons.isWoodSelected()) && !UiIcons.isWoodHover())
				|| vil.isSelected() && mouse.getButton() == 3)
				&& (level.selectTree(mouse.getX(), mouse.getY()) != null)) {
			vil.resetMove();
			vil.movement = vil.getShortest(level.selectTree(mouse.getX(), mouse.getY()));
			vil.setJob(level.selectTree(mouse.getX(), mouse.getY()));
			vil.setSelected(false);
		}
		if ((((mouse.getButton() == 1 && UiIcons.isMiningSelected()) && !UiIcons.isMiningHover())
				|| vil.isSelected() && mouse.getButton() == 3)
				&& (level.selectOre(mouse.getX(), mouse.getY()) != null)) {
			vil.resetMove();
			vil.movement = vil.getShortest(level.selectOre(mouse.getX(), mouse.getY()));
			vil.setJob(level.selectOre(mouse.getX(), mouse.getY()));
			vil.setSelected(false);

		}
		if ((mouse.getButton() == 1 && UiIcons.isTrowelSelected() && !UiIcons.isTrowelHover())
				&& level.isClearTile(mouse.getTileX(), mouse.getTileY())) {
			vil.resetMove();
			vil.movement = vil.getShortest(mouse.getTileX(), mouse.getTileY());
			vil.setJob(new Wall(mouse.getTileX(), mouse.getTileY(), false), true);
			vil.setSelected(false);

		}
		if (mouse.getButton() == 1) {
			if (vil.hoverOn(mouse)) {
				vil.setSelected(true);
			} else {
				vil.setSelected(false);
			}
		}
		if (mouse.getButton() == 3 && vil.isSelected()) {
			vil.resetMove();
			vil.movement = vil.getPath(vil.x >> 4, vil.y >> 4, mouse.getTileX(), mouse.getTileY());
			vil.setSelected(false);
		}
		if (mouse.getButton() == 1) {
			UiIcons.select();
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
		for (Mob i : mobs) {
			i.update(mouse);
		}
	}

	private void renderMobs() {
		for (Mob i : mobs) {
			i.render(screen);
		}
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
		UiIcons.render(screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();

	}

}
