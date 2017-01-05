package graphics.ui;

import java.awt.Color;
import java.awt.Graphics;

import input.Mouse;
import map.Map;

public class BuildOutline {
	private Color buildable = new Color(78, 244, 66, 210);
	private Color notbuildable = new Color(244, 66, 66, 210);
	private int buildSquareXS = 0;
	private int buildSquareYS = 0;
	private int buildSquareXE = 0;
	private int buildSquareYE = 0;
	private final int WIDTH = 48;
	private int squarewidth = 0;
	private int squareheight = 0;
	private boolean visible;
	private Map level;

	public void render(Graphics g) {
		if (visible) {
			if (level.getTile((buildSquareXS / 3) >> 4, (buildSquareYS / 3) >> 4).solid()) {
				g.setColor(notbuildable);
			} else {
				g.setColor(buildable);
			}
			if (buildSquareXE == 0 && buildSquareYE == 0) {
				g.fillRect(buildSquareXS, buildSquareYS, WIDTH * squarewidth, WIDTH * squareheight);
				return;
			}
			if (squarewidth > squareheight) {
				if (buildSquareXS < buildSquareXE) { // START LINKS VAN EIND
					g.fillRect(buildSquareXS, buildSquareYS, WIDTH * squarewidth, WIDTH * squareheight);
					return;
				} else { // START RECHTS VAN EIND
					g.fillRect(buildSquareXE, buildSquareYE, WIDTH * squarewidth, WIDTH * squareheight);
					return;
				}
			} else {
				if (buildSquareYS < buildSquareYE) { // START BOVEN EIND
					g.fillRect(buildSquareXS, buildSquareYS, WIDTH * squarewidth, WIDTH * squareheight);
					return;
				} else { // START ONDER EIND
					g.fillRect(buildSquareXE, buildSquareYE, WIDTH * squarewidth, WIDTH * squareheight);
					return;
				}

			}
		}
	}

	public int getTileXS() {
		return (buildSquareXS / 3);
	}

	public int getTileYS() {
		return (buildSquareYS / 3);
	}

	public int getTileXE() {
		return (buildSquareXE / 3);
	}

	public int getTileYE() {
		return (buildSquareYE / 3);
	}

	public void update(int x, int y, Mouse mouse) {
		if (visible) {
			if (mouse.getButton() == 1) {
				buildSquareXE = (x << 4) * 3;
				buildSquareYE = (y << 4) * 3;
				squarewidth = Math.abs(((buildSquareXE >> 4) / 3) - ((buildSquareXS >> 4) / 3)) + 1;
				squareheight = Math.abs(((buildSquareYE >> 4) / 3) - ((buildSquareYS >> 4) / 3)) + 1;
				// System.out.println(squarewidth + " / " + squareheight);
				if (squareheight > squarewidth) {
					squarewidth = 1;
				} else {
					squareheight = 1;
				}
			} else {
				buildSquareXS = (x << 4) * 3;
				buildSquareYS = (y << 4) * 3;
				squarewidth = 1;
				squareheight = 1;
				buildSquareXE = 0;
				buildSquareYE = 0;

			}
		}
	}

	public BuildOutline(Map level) {
		this.level = level;
	}

	public void show(Mouse mouse) {
		if (!visible) {
			visible = true;
			buildSquareXS = (mouse.getTileX() << 4) * 3;
			buildSquareYS = (mouse.getTileY() << 4) * 3;
			squarewidth = 1;
			squareheight = 1;
		}
	}

	public void remove() {
		if (visible) {
			buildSquareXS = 0;
			buildSquareYS = 0;
			buildSquareXE = 0;
			buildSquareYE = 0;
			squarewidth = 0;
			squareheight = 0;
			visible = false;
		}
	}

	public boolean isVisible() {
		return visible;
	}
}
