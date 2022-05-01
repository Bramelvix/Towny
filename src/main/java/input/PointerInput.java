package input;

import events.*;
import main.Game;
import map.Tile;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import static org.lwjgl.glfw.GLFW.*;


public class PointerInput {

	public static class EType<T extends Event> implements EventType<T> {
		public static final EType<PointerMoveEvent> MOVE = new EType<>();
		public static final EType<PointerDragEvent> DRAG = new EType<>();
		public static final EType<PointerDragEvent> DRAG_START = new EType<>();
		public static final EType<PointerDragEvent> DRAG_END = new EType<>();
		public static final EType<PointerClickEvent> PRESSED = new EType<>();
		public static final EType<PointerClickEvent> RELEASED = new EType<>();

		private EType() {
		}
	}

	private static PointerInput instance;

	public static PointerInput getInstance() {
		if (instance != null) {
			return instance;
		}
		throw new RuntimeException("Pointer device not properly configured");
	}

	public static void configure(Game game) {
		instance = new PointerInput(game);
	}

	protected EventListenerCatalog listeners = new EventListenerCatalog();

	protected final Game game;
	protected double xpos;
	protected double ypos;

	protected boolean[] released = new boolean[3];
	protected boolean[] pressed = new boolean[3];
	protected int heldDownButton = -1;

	public PointerInput(Game game) {
		this.game = game;
	}

	public <T extends Event> Subscription on(EType<T> type, EventListener<T> listener) {
		return listeners.register(type, listener);
	}

	public GLFWCursorPosCallbackI positionCallback() {
		return (long window, double xpos, double ypos) -> {
			listeners.fire(EType.MOVE, new PointerMoveEvent(xpos, ypos));

			this.xpos = xpos;
			this.ypos = ypos;
			if (heldDownButton != -1) {
				listeners.fire(EType.DRAG, new PointerDragEvent(xpos, ypos, heldDownButton));
			}
		};
	}

	public GLFWMouseButtonCallbackI buttonsCallback() {
		return (long window, int button, int action, int mods) -> {
			// stop pesky gaming mice with their fancy buttons from crashing my entire game
			if (button > 2 || button < 0) {
				return;
			}
			if (action == GLFW_RELEASE) {
				released[button] = true;
				pressed[button] = false;
				heldDownButton = -1;
				listeners.fire(EType.RELEASED, new PointerClickEvent(button, action, this.xpos, this.ypos));
				listeners.fire(EType.DRAG_END, new PointerDragEvent(this.xpos, this.ypos, button));
				//TODO drag_start and drag_end can be triggered by 2 different mouse buttons atm so fix that
			} else if (action == GLFW_PRESS) {
				pressed[button] = true;
				released[button] = false;
				heldDownButton = button;
				listeners.fire(EType.PRESSED, new PointerClickEvent(button, action, this.xpos, this.ypos));
				listeners.fire(EType.DRAG_START, new PointerDragEvent(this.xpos, this.ypos, button));
			}
		};
	}

	public void resetLeftAndRight() {
		released[GLFW_MOUSE_BUTTON_LEFT] = false;
		pressed[GLFW_MOUSE_BUTTON_LEFT] = false;
		released[GLFW_MOUSE_BUTTON_RIGHT] = false;
		pressed[GLFW_MOUSE_BUTTON_RIGHT] = false;
	}

	public boolean wasPressed(int button) {
		return button <= 3 && button >= 0 && pressed[button];
	}

	public boolean wasReleased(int button) {
		return button <= 3 && button >= 0 && released[button];
	}

	// the x and y of the tiles in the game that the mouse is on
	public int getTileX() {
		return (int) ((xpos + game.getxScroll()) / Tile.SIZE);
	}

	public int getTileY() {
		return (int) ((ypos + game.getyScroll()) / Tile.SIZE);
	}

	public int getTrueX() {
		return (int) xpos;
	}

	public int getTrueY() {
		return (int) ypos;
	}

	// x and y coord on the screen, in pixels , WITH OFFSET
	public float getX() {
		return (float) xpos + game.getxScroll();
	}

	public float getY() {
		return (float) ypos + game.getyScroll();
	}

}
