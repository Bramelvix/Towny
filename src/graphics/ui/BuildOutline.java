package graphics.ui;

import java.awt.Color;

import entity.dynamic.mob.work.BuildingRecipe;
import graphics.OpenglUtils;
import input.MousePosition;
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
    private final int WIDTH = 16 * Game.SCALE; // width of a square IN REAL PIXELS (16*Scale)
    private int squarewidth; // width of the outline in squares
    private int squareheight; // height of the outline in squares
    private boolean visible; // is the outline visible
    private Level[] levels; // the map is needed to decide if a square is empty
    private boolean lockedSize = false;
    private BuildingRecipe build;
    private int z = 0;

    // rendering the outline
    public void render() {
        if (visible) {
            if (lockedSize || buildSquareXE == 0 && buildSquareYE == 0) {
                OpenglUtils.buildOutlineDraw(buildSquareXSTeken,buildSquareYSTeken,WIDTH,notBuildable((((buildSquareXS) / Game.SCALE) /16), ((buildSquareYS) / Game.SCALE) /16, z) ? notbuildable : buildable);
                return;
            }
            if (squarewidth > squareheight) {
                if (buildSquareXSTeken < buildSquareXE) { // START LINKS VAN EIND == SLEEP NAAR RECHTS
                    for (int i = 0; i < squarewidth; i++) {
                        OpenglUtils.buildOutlineDraw(buildSquareXSTeken + (i * WIDTH),buildSquareYSTeken,WIDTH,notBuildable((((buildSquareXS) / Game.SCALE) /16) + i, (((buildSquareYS) / Game.SCALE) /16), z) ? notbuildable
                                : buildable);
                    }
                } else { // START RECHTS VAN EIND == SLEEP NAAR LINKS
                    for (int i = 0; i < squarewidth; i++) {
                        OpenglUtils.buildOutlineDraw(buildSquareXSTeken - (WIDTH * (squarewidth - 1)) + (i * WIDTH),buildSquareYSTeken,WIDTH,(notBuildable(((((buildSquareXS) - (WIDTH * (squarewidth - 1))) / Game.SCALE) /16) + i,
                                (((buildSquareYS) / Game.SCALE) /16), z)) ? notbuildable : buildable);
                    }
                }
            } else {
                if (buildSquareYSTeken < buildSquareYE) { // START BOVEN EIND == SLEEP NAAR ONDER
                    for (int i = 0; i < squareheight; i++) {
                        OpenglUtils.buildOutlineDraw(buildSquareXSTeken,buildSquareYSTeken + (WIDTH * i), WIDTH* (squareheight - i),(notBuildable((((buildSquareXS) / Game.SCALE) /16), (((buildSquareYS) / Game.SCALE) /16) + i, z))
                                ? notbuildable
                                : buildable);
                    }
                } else { // START ONDER EIND == SLEEP NAAR BOVEN
                    for (int i = 0; i < squareheight; i++) {

                        OpenglUtils.buildOutlineDraw(buildSquareXSTeken,buildSquareYSTeken - (WIDTH * (squareheight - 1)) + (i * WIDTH),WIDTH,(notBuildable((((buildSquareXS) / Game.SCALE) /16),
                                ((((buildSquareYS) - (WIDTH * (squareheight - 1))) / Game.SCALE) /16) + i, z)) ? notbuildable
                                : buildable);
                    }
                }

            }
        }
    }

    // is the tile empty
    private boolean notBuildable(int x, int y, int z) {
        return (levels[z].getTile(x, y).solid());

    }

    // getters
    public int[][] getSquareCoords() {
        int[][] coords;
        if (buildSquareXE == 0 && buildSquareYE == 0) {
            coords = new int[1][2];
            coords[0][0] = (buildSquareXS / Game.SCALE);
            coords[0][1] = (buildSquareYS / Game.SCALE);
        } else {
            if (squarewidth > squareheight) {
                if (buildSquareXSTeken < buildSquareXE) { // START LINKS VAN EIND == SLEEP NAAR RECHTS
                    coords = new int[squarewidth][2];
                    for (int i = 0; i < squarewidth; i++) {
                        coords[i][0] = (buildSquareXS / Game.SCALE) + (i *16);
                        coords[i][1] = (buildSquareYS / Game.SCALE);
                    }
                } else { // START RECHTS VAN EIND == SLEEP NAAR LINKS
                    coords = new int[squarewidth][2];
                    for (int i = 0; i < squarewidth; i++) {
                        coords[i][0] = (((buildSquareXS - (WIDTH * (squarewidth - 1))))) + (i *16);
                        coords[i][1] = ((buildSquareYS / Game.SCALE));
                    }
                }
            } else {
                if (buildSquareYSTeken < buildSquareYE) { // START BOVEN EIND == SLEEP NAAR ONDER
                    coords = new int[squareheight][2];
                    for (int i = 0; i < squareheight; i++) {
                        coords[i][0] = (buildSquareXS / Game.SCALE);
                        coords[i][1] = (buildSquareYS / Game.SCALE) + (i *16);
                    }
                } else { // START ONDER EIND == SLEEP NAAR BOVEN
                    coords = new int[squareheight][2];
                    for (int i = 0; i < squareheight; i++) {
                        coords[i][0] = (buildSquareXS / Game.SCALE);
                        coords[i][1] = (buildSquareYS - (WIDTH * (squareheight - 1)) / Game.SCALE) + (i *16);
                    }
                }
            }
        }
        return coords;

    }

    public boolean isVisible() {
        return visible;
    }


    // update the outline
    public void update(int xOff, int yOff, boolean force, int z) {
        this.z = z;
        if (visible || force) { //TODO fix this aids
            /*if (MouseButton.heldDown(GLFW_MOUSE_BUTTON_LEFT) && !lockedSize) {
                buildSquareXE = ((MousePosition.getTileX() * 16) * Game.SCALE);
                buildSquareYE = ((MousePosition.getTileY() * 16) * Game.SCALE);
                squarewidth = Math.abs(((buildSquareXE / 16) / Game.SCALE) - ((buildSquareXS / 16) / Game.SCALE)) + 1;
                squareheight = Math.abs(((buildSquareYE / 16) / Game.SCALE) - ((buildSquareYS / 16) / Game.SCALE)) + 1;
                if (squareheight > squarewidth) {
                    squarewidth = 1;
                } else {
                    squareheight = 1;
                }
            } else {*/
                buildSquareXS = ((MousePosition.getTileX() * 16) * Game.SCALE);
                buildSquareXSTeken = ((MousePosition.getTileX() * 16) * Game.SCALE)-xOff;
                buildSquareYS = ((MousePosition.getTileY() * 16) * Game.SCALE);
                buildSquareYSTeken = ((MousePosition.getTileY() * 16) * Game.SCALE)-yOff;
                squarewidth = 1;
                squareheight = 1;
                buildSquareXE = 0;
                buildSquareYE = 0;

          //  }
        }
    }

    public void update(int xOff, int yOff,int z) {
        update(xOff, yOff, false,z);
    }

    public BuildingRecipe getBuildRecipe() {
        return build;
    }

    // constructor
    BuildOutline(Level[] level) {
        setLevels(level);
    }

    public void setLevels(Level[] levels) {
        this.levels = levels;
    }

    // show the outline
    public void show(int xoff, int yoff, int z,boolean lockedSize, BuildingRecipe build) {
        if (!visible) {
            update(xoff, yoff, true,z);
            visible = true;
            buildSquareXS = (MousePosition.getTileX() *16) * Game.SCALE;
            buildSquareYS = (MousePosition.getTileY() *16) * Game.SCALE;
            squarewidth = 1;
            squareheight = 1;
            this.build = build;
            this.lockedSize = lockedSize;
        }
    }

    // hide the outline
    public void remove() {
        visible = false;
    }
}
