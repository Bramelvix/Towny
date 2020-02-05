package input;

import main.Game;
import map.Tile;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;

public class MousePosition implements GLFWCursorPosCallbackI {

	private static int trueX = -1; // x and y coord on the screen, in pixels, WITHOUT OFFSET
	private static int trueY = -1;

	private static Game game;

	public MousePosition(Game game) {
		MousePosition.game = game;
	}

	@Override
	public void invoke(long window, double x, double y) {
		trueX = (int) x;
		trueY = (int) y;
		if (MouseButton.heldDown(GLFW_MOUSE_BUTTON_MIDDLE)) {
			int deltaX = -trueX;
			int deltaY = -trueY;
			if (deltaX + MouseButton.getDragOffsetX() >= 0 && deltaX + MouseButton.getDragOffsetX() <= ((game.map[game.currentLayerNumber].width * Tile.SIZE) - Game.width)) {
				game.xScroll = deltaX + MouseButton.getDragOffsetX();
			}
			if (deltaY + MouseButton.getDragOffsetY() >= 0 && deltaY + MouseButton.getDragOffsetY() <= ((game.map[game.currentLayerNumber].height * Tile.SIZE) - Game.height)) {
				game.yScroll = deltaY + MouseButton.getDragOffsetY();
			}
		}
	}

	// the x and y of the tiles in the game that the mouse is on
	public static int getTileX() {
		return (trueX + game.xScroll) / Tile.SIZE;
	}

	public static int getTileY() {
		return (trueY + game.yScroll) / Tile.SIZE;
	}

	public static int getTrueX() {
		return trueX;
	}

	public static int getTrueY() {
		return trueY;
	}

	// x and y coord on the screen, in pixels , WITH OFFSET
	public static int getX() {
		return trueX + game.xScroll;
	}

	public static int getY() {
		return trueY + game.yScroll;
	}

}

