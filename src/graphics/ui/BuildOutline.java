package graphics.ui;

import java.awt.Color;
import java.awt.Graphics;

import input.Mouse;
import map.Map;

public class BuildOutline {
	private Color buildable = new Color(78, 244, 66, 210); // groen
	private Color notbuildable = new Color(244, 66, 66, 210); // rood
	private int buildSquareXS = 0; // x coördinaat van de start in de game
									// wereld
	private int buildSquareXSTeken; // x coördinaat van de start op het scherm
	private int buildSquareYSTeken; // y coördinaat van de start op het scherm
	private int buildSquareYS = 0; // y coördinaat van de start in de game
									// wereld
	private int buildSquareXE = 0; // x coördinaat van het einde in de game
									// wereld
	private int buildSquareYE = 0; // y coördinaat van het einde in de game
									// wereld
	private final int WIDTH = 48; // breedte van een vierkant in pixels
	private int squarewidth = 0; // breedte van de outline in blokken
	private int squareheight = 0; // hoogte van de outline in blokken
	private boolean visible;
	private Map level;

	public void render(Graphics g) {
		if (visible) {
			if (buildSquareXE == 0 && buildSquareYE == 0) {
				if (nietBouwbaar(((buildSquareXS / 3) >> 4), (buildSquareYS / 3) >> 4)) {
					g.setColor(notbuildable);
				} else {
					g.setColor(buildable);
				}
				g.fillRect(buildSquareXSTeken, buildSquareYSTeken, WIDTH, WIDTH);
				return;
			}
			if (squarewidth > squareheight) {
				if (buildSquareXSTeken < buildSquareXE) { // START LINKS VAN
															// EIND == SLEEP
															// NAAR RECHTS
					for (int i = 0; i < squarewidth; i++) {
						if (nietBouwbaar(((buildSquareXS / 3) >> 4) + i, ((buildSquareYS / 3) >> 4))) {
							g.setColor(notbuildable);
						} else {
							g.setColor(buildable);
						}
						g.fillRect(buildSquareXSTeken + (i * WIDTH), buildSquareYSTeken, WIDTH, WIDTH);
					}
					return;
				} else { // START RECHTS VAN EIND == SLEEP NAAR LINKS
					for (int i = 0; i < squarewidth; i++) {
						if (nietBouwbaar((((buildSquareXS - (WIDTH * (squarewidth - 1))) / 3) >> 4) + i,
								((buildSquareYS / 3) >> 4))) {
							g.setColor(notbuildable);
						} else {
							g.setColor(buildable);
						}
						g.fillRect(buildSquareXSTeken - (WIDTH * (squarewidth - 1)) + (i * WIDTH), buildSquareYSTeken,
								WIDTH, WIDTH);
					}
					return;
				}
			} else {
				if (buildSquareYSTeken < buildSquareYE) { // START BOVEN EIND ==
															// SLEEP NAAR ONDER
					for (int i = 0; i < squareheight; i++) {
						if (nietBouwbaar(((buildSquareXS / 3) >> 4), ((buildSquareYS / 3) >> 4) + i)) {
							g.setColor(notbuildable);
						} else {
							g.setColor(buildable);
						}
						g.fillRect(buildSquareXSTeken, buildSquareYSTeken + (WIDTH * i), WIDTH,
								WIDTH * (squareheight - i));
					}
					return;
				} else { // START ONDER EIND == SLEEP NAAR BOVEN
					for (int i = 0; i < squareheight; i++) {
						if (nietBouwbaar(((buildSquareXS / 3) >> 4),
								(((buildSquareYS - (WIDTH * (squareheight - 1))) / 3) >> 4) + i)) {
							g.setColor(notbuildable);
						} else {
							g.setColor(buildable);
						}
						g.fillRect(buildSquareXSTeken, buildSquareYSTeken - (WIDTH * (squareheight - 1)) + (i * WIDTH),
								WIDTH, WIDTH);
					}
					return;
				}

			}
		}
	}

	private boolean nietBouwbaar(int x, int y) {
		return (level.getTile(x, y).solid());

	}

	public int getTileXS() {
		return (buildSquareXS / 3);
	}

	public int[][] getSquareCoords() {
		int[][] coords;
		if (buildSquareXE == 0 && buildSquareYE == 0) {
			coords = new int[1][2];
			coords[0][0] = (buildSquareXS / 3);
			coords[0][1] = (buildSquareYS / 3);
		} else {
			if (squarewidth > squareheight) {
				if (buildSquareXSTeken < buildSquareXE) { // START LINKS VAN
															// EIND == SLEEP
															// NAAR RECHTS
					coords = new int[squarewidth][2];
					for (int i = 0; i < squarewidth; i++) {
						coords[i][0] = (buildSquareXS / 3) + (i << 4);
						coords[i][1] = (buildSquareYS / 3);
					}
				} else { // START RECHTS VAN EIND == SLEEP NAAR LINKS
					coords = new int[squarewidth][2];
					for (int i = 0; i < squarewidth; i++) {
						coords[i][0] = (((buildSquareXS - (WIDTH * (squarewidth - 1))))) + (i << 4);
						coords[i][1] = ((buildSquareYS / 3));
					}
				}
			} else {
				if (buildSquareYSTeken < buildSquareYE) { // START BOVEN EIND ==
															// SLEEP NAAR ONDER
					coords = new int[squareheight][2];
					for (int i = 0; i < squareheight; i++) {
						coords[i][0] = (buildSquareXS / 3);
						coords[i][1] = (buildSquareYS / 3) + (i << 4);
					}
				} else { // START ONDER EIND == SLEEP NAAR BOVEN
					coords = new int[squareheight][2];
					for (int i = 0; i < squareheight; i++) {
						coords[i][0] = (buildSquareXS / 3);
						coords[i][1] = (buildSquareYS - (WIDTH * (squareheight - 1)) / 3) + (i << 4);
					}
				}
			}
		}
		return coords;

	}

	public int getLength() {
		if (squarewidth > squareheight) {
			return squarewidth;
		} else {
			return squareheight;
		}
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

	public void update(Mouse mouse, int xOff, int yOff) {
		if (visible) {
			if (mouse.getDrag()) {
				buildSquareXE = (mouse.getTileX() << 4) * 3;
				buildSquareYE = (mouse.getTileY() << 4) * 3;
				squarewidth = Math.abs(((buildSquareXE >> 4) / 3) - ((buildSquareXS >> 4) / 3)) + 1;
				squareheight = Math.abs(((buildSquareYE >> 4) / 3) - ((buildSquareYS >> 4) / 3)) + 1;
				if (squareheight > squarewidth) {
					squarewidth = 1;
				} else {
					squareheight = 1;
				}
			} else {
				buildSquareXS = ((mouse.getTileX() << 4) * 3);
				buildSquareXSTeken = ((mouse.getTileX() << 4) * 3) - (xOff * 3);
				buildSquareYS = ((mouse.getTileY() << 4) * 3);
				buildSquareYSTeken = ((mouse.getTileY() << 4) * 3) - (yOff * 3);
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
			visible = false;
		}
	}

	public boolean isVisible() {
		return visible;
	}
}
