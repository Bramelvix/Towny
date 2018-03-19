package graphics.ui;

import java.awt.Color;
import java.awt.Graphics;

import entity.dynamic.mob.work.BuildingRecipe;
import input.Mouse;
import main.Game;
import map.Level;

//the green or red outline used to select where to build things
public class BuildOutline {
	private Color buildable = new Color(78, 244, 66, 210); // green
	private Color notbuildable = new Color(244, 66, 66, 210); // red
	private int buildSquareXS; // x coord of the start in the game world
	private int buildSquareYS; // y coord of the start in the game world
	private int buildSquareXSTeken; // x coord of the start on the screen
	private int buildSquareYSTeken; // y coord of the start on the screen
	private int buildSquareXE; // x coord of the end in the game world
	private int buildSquareYE; // y coord of the end in the game world
	private final int WIDTH = 16*Game.SCALE; // width of a square IN REAL PIXELS (16*Scale)
	private int squarewidth; // width of the outline in squares
	private int squareheight; // height of the outline in squares
	private boolean visible; // is the outline visible
	private Level level; // the map is needed to decide if a square is empty
	private boolean lockedSize = false;
	private BuildingRecipe build;

	// rendering the outline
	public void render(Graphics g) {
		if (visible) {
			if (lockedSize || buildSquareXE == 0 && buildSquareYE == 0) {
				g.setColor(
						notBuildable(((buildSquareXS / Game.SCALE) >> 4), (buildSquareYS / Game.SCALE) >> 4) ? notbuildable : buildable);
				g.fillRect(buildSquareXSTeken, buildSquareYSTeken, WIDTH, WIDTH);
				return;
			}
			if (squarewidth > squareheight) {
				if (buildSquareXSTeken < buildSquareXE) { // START LINKS VAN
															// EIND == SLEEP
															// NAAR RECHTS
					for (int i = 0; i < squarewidth; i++) {
						g.setColor(
								notBuildable(((buildSquareXS / Game.SCALE) >> 4) + i, ((buildSquareYS / Game.SCALE) >> 4)) ? notbuildable
										: buildable);
						g.fillRect(buildSquareXSTeken + (i * WIDTH), buildSquareYSTeken, WIDTH, WIDTH);
					}
				} else { // START RECHTS VAN EIND == SLEEP NAAR LINKS
					for (int i = 0; i < squarewidth; i++) {
						g.setColor((notBuildable((((buildSquareXS - (WIDTH * (squarewidth - 1))) / Game.SCALE) >> 4) + i,
								((buildSquareYS / Game.SCALE) >> 4))) ? notbuildable : buildable);
						g.fillRect(buildSquareXSTeken - (WIDTH * (squarewidth - 1)) + (i * WIDTH), buildSquareYSTeken,
								WIDTH, WIDTH);
					}
				}
			} else {
				if (buildSquareYSTeken < buildSquareYE) { // START BOVEN EIND ==
															// SLEEP NAAR ONDER
					for (int i = 0; i < squareheight; i++) {
						g.setColor((notBuildable(((buildSquareXS / Game.SCALE) >> 4), ((buildSquareYS / Game.SCALE) >> 4) + i))
								? notbuildable
								: buildable);
						g.fillRect(buildSquareXSTeken, buildSquareYSTeken + (WIDTH * i), WIDTH,
								WIDTH * (squareheight - i));
					}
					return;
				} else { // START ONDER EIND == SLEEP NAAR BOVEN
					for (int i = 0; i < squareheight; i++) {
						g.setColor((notBuildable(((buildSquareXS / Game.SCALE) >> 4),
								(((buildSquareYS - (WIDTH * (squareheight - 1))) / Game.SCALE) >> 4) + i)) ? notbuildable
										: buildable);
						g.fillRect(buildSquareXSTeken, buildSquareYSTeken - (WIDTH * (squareheight - 1)) + (i * WIDTH),
								WIDTH, WIDTH);
					}
					return;
				}

			}
		}
	}

	// is the tile empty
	private boolean notBuildable(int x, int y) {
		return (level.getTile(x, y).solid());

	}

	// getters
	public int getTileXS() {
		return (buildSquareXS / Game.SCALE);
	}

	public int[][] getSquareCoords() {
		int[][] coords;
		if (buildSquareXE == 0 && buildSquareYE == 0) {
			coords = new int[1][2];
			coords[0][0] = (buildSquareXS / Game.SCALE);
			coords[0][1] = (buildSquareYS / Game.SCALE);
		} else {
			if (squarewidth > squareheight) {
				if (buildSquareXSTeken < buildSquareXE) { // START LINKS VAN
															// EIND == SLEEP
															// NAAR RECHTS
					coords = new int[squarewidth][2];
					for (int i = 0; i < squarewidth; i++) {
						coords[i][0] = (buildSquareXS / Game.SCALE) + (i << 4);
						coords[i][1] = (buildSquareYS / Game.SCALE);
					}
				} else { // START RECHTS VAN EIND == SLEEP NAAR LINKS
					coords = new int[squarewidth][2];
					for (int i = 0; i < squarewidth; i++) {
						coords[i][0] = (((buildSquareXS - (WIDTH * (squarewidth - 1))))) + (i << 4);
						coords[i][1] = ((buildSquareYS / Game.SCALE));
					}
				}
			} else {
				if (buildSquareYSTeken < buildSquareYE) { // START BOVEN EIND ==
															// SLEEP NAAR ONDER
					coords = new int[squareheight][2];
					for (int i = 0; i < squareheight; i++) {
						coords[i][0] = (buildSquareXS / Game.SCALE);
						coords[i][1] = (buildSquareYS / Game.SCALE) + (i << 4);
					}
				} else { // START ONDER EIND == SLEEP NAAR BOVEN
					coords = new int[squareheight][2];
					for (int i = 0; i < squareheight; i++) {
						coords[i][0] = (buildSquareXS / Game.SCALE);
						coords[i][1] = (buildSquareYS - (WIDTH * (squareheight - 1)) / Game.SCALE) + (i << 4);
					}
				}
			}
		}
		return coords;

	}

	public boolean isVisible() {
		return visible;
	}

	public int getTileYS() {
		return (buildSquareYS / Game.SCALE);
	}


	// update the outline
	public void update(Mouse mouse, int xOff, int yOff, boolean force) {
		if (visible || force) {
			if (mouse.getDrag() && !lockedSize) {
				buildSquareXE = (mouse.getTileX() << 4) * Game.SCALE;
				buildSquareYE = (mouse.getTileY() << 4) * Game.SCALE;
				squarewidth = Math.abs(((buildSquareXE >> 4) / Game.SCALE) - ((buildSquareXS >> 4) / Game.SCALE)) + 1;
				squareheight = Math.abs(((buildSquareYE >> 4) / Game.SCALE) - ((buildSquareYS >> 4) / Game.SCALE)) + 1;
				if (squareheight > squarewidth) {
					squarewidth = 1;
				} else {
					squareheight = 1;
				}
			} else {
				buildSquareXS = ((mouse.getTileX() << 4) * Game.SCALE);
				buildSquareXSTeken = ((mouse.getTileX() << 4) * Game.SCALE) - (xOff * Game.SCALE);
				buildSquareYS = ((mouse.getTileY() << 4) * Game.SCALE);
				buildSquareYSTeken = ((mouse.getTileY() << 4) * Game.SCALE) - (yOff * Game.SCALE);
				squarewidth = 1;
				squareheight = 1;
				buildSquareXE = 0;
				buildSquareYE = 0;

			}
		}
	}

	public void update(Mouse mouse, int xOff, int yOff) {
		update(mouse, xOff, yOff, false);
	}

	public BuildingRecipe getBuildRecipe() {
		return build;
	}

	// constructor
    BuildOutline(Level level) {
		setLevel(level);
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	// show the outline
	public void show(Mouse mouse, int xoff, int yoff, boolean lockedSize, BuildingRecipe build) {
		if (!visible) {
			update(mouse, xoff, yoff, true);
			visible = true;
			buildSquareXS = (mouse.getTileX() << 4) * Game.SCALE;
			buildSquareYS = (mouse.getTileY() << 4) * Game.SCALE;
			squarewidth = 1;
			squareheight = 1;
			this.build = build;
			this.lockedSize = lockedSize;
		}
	}

	// hide the outline
	public void remove() {
		if (visible)
			visible = false;
	}
}
