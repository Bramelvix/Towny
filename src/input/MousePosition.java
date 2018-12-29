package input;

import main.Game;
import map.Tile;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;

public class MousePosition implements GLFWCursorPosCallbackI {
    private static int mouseX = -1; // x and y coord on the screen, in ingame pixels (1/3), WITH OFFSET
    private static int mouseY = -1;
    private static int mouseTileX = -1; // the x and y of the tiles in the game that the mouse is on
    private static int mouseTileY = -1;
    private static int trueX = -1; // x and y coord on the screen, in ingame pixels (1/3), WITHOUT OFFSET
    private static int trueY = -1;
    private static int trueXpixels = -1; // x and y coord on the screen, in acutal pixels, WITHOUT OFFSET
    private static int trueYpixels = -1;
    private static int xPixels = -1;
    private static int yPixels = -1;

    private static Game game;

    public MousePosition(Game game) {
        this.game = game;
    }

    @Override
    public void invoke(long window, double x, double y) {
        trueXpixels = (int) x;
        trueYpixels = (int) y;
        xPixels = trueXpixels + game.xScroll;
        yPixels = trueYpixels + game.yScroll;
        trueX = trueXpixels / Game.SCALE;
        trueY = trueYpixels / Game.SCALE;
        mouseX = trueX + (game.xScroll/3);
        mouseY = trueY + (game.yScroll/3);
        mouseTileX = mouseX / Tile.SIZE;
        mouseTileY = mouseY / Tile.SIZE;
        if (MouseButton.heldDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
            int deltaX = -trueXpixels;
            int deltaY = -trueYpixels;
            if (deltaX + MouseButton.getDragOffsetX() >= 0 && deltaX + MouseButton.getDragOffsetX() <= ((game.map[game.currentLayerNumber].width * Tile.SIZE) - Game.width) * Game.SCALE) {
                game.xScroll = deltaX + MouseButton.getDragOffsetX();
            }
            if (deltaY + MouseButton.getDragOffsetY() >= 0 && deltaY + MouseButton.getDragOffsetY() <= ((game.map[game.currentLayerNumber].height * Tile.SIZE) - Game.height) * Game.SCALE) {
                game.yScroll = deltaY + MouseButton.getDragOffsetY();
            }

        }
    }

    public static int getMouseX() {
        return trueXpixels+game.xScroll;
    }

    public static int getMouseY() {
        return trueYpixels+game.yScroll;
    }

    public static int getTileX() {
        return mouseTileX;
    }

    public static int getTileY() {
        return mouseTileY;
    }

    public static int getTrueX() {
        return trueX;
    }
    public static int getXPixels() {
        return xPixels;
    }
    public static int getYPixels() {
        return yPixels;
    }


    public static int getTrueY() {
        return trueY;
    }

    public static int getTrueXPixels() {
        return trueXpixels;
    }

    public static int getTrueYPixels() {
        return trueYpixels;
    }
    public static int getX() {
        return mouseX;
    }
    public static int getY() {
        return mouseY;
    }
}

