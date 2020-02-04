package input;

import main.Game;
import map.Tile;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;

public class MousePosition implements GLFWCursorPosCallbackI {
	private static int mouseX = -1; // x and y coord on the screen, in pixels , WITH OFFSET
	private static int mouseY = -1;
	private static int mouseTileX = -1; // the x and y of the tiles in the game that the mouse is on
	private static int mouseTileY = -1;
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
		mouseX = trueX + game.xScroll;
		mouseY = trueY + game.yScroll;
		mouseTileX = mouseX / Tile.SIZE;
		mouseTileY = mouseY / Tile.SIZE;
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

	public static int getTileX() {
		return mouseTileX;
	}

	public static int getTileY() {
		return mouseTileY;
	}

	public static int getTrueX() {
		return trueX;
	}

	public static int getTrueY() {
		return trueY;
	}

	public static int getX() {
		return mouseX;
	}
	public static int getY() {
		return mouseY;
	}
}

