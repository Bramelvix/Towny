package graphics.ui;

import java.awt.Color;
import java.awt.Graphics;

import map.Map;

public class BuildOutline {
	private Color buildable = new Color(78, 244, 66, 210);
	private Color notbuildable = new Color(244, 66, 66, 210);
	private int buildSquareX = 0;
	private int buildSquareY = 0;
	private int buildSquareWidth = 0;
	private boolean visible;
	private Map level;

	public void render(Graphics g) {
		if (visible) {
			if (level.getTile((buildSquareX / 3) >> 4, (buildSquareY / 3) >> 4).solid()) {
				g.setColor(notbuildable);
			} else {
				g.setColor(buildable);
			}
			g.fillRect(buildSquareX, buildSquareY, buildSquareWidth, buildSquareWidth);
		}
	}

	public int getTileX() {
		return (buildSquareX / 3);
	}

	public int getTileY() {
		return (buildSquareY / 3);
	}

	public void update(int x, int y) {
		if (visible) {
			buildSquareX = (x << 4) * 3;
			buildSquareY = (y << 4) * 3;
		}
	}

	public BuildOutline(Map level) {
		this.level = level;
	}

	public void show() {
		if (!visible) {
			buildSquareWidth = 48;
			visible = true;
		}
	}

	public void remove() {
		if (visible) {
			buildSquareX = 0;
			buildSquareY = 0;
			visible = false;
			buildSquareWidth = 0;
		}
	}

	public boolean isVisible() {
		return visible;
	}
}
