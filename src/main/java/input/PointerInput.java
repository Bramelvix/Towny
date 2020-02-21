package input;

import static org.lwjgl.glfw.GLFW.*;

import events.Event;
import events.EventListener;
import events.EventListenerCatalog;
import events.EventType;
import events.Subscription;
import main.Game;
import map.Tile;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;


public class PointerInput {

	public static class EType<T extends Event> implements EventType<T> {
		public static final EType<PointerMoveEvent> MOVE = new EType<> ();
		public static final EType<Event> DRAG = new EType<> ();
		public static final EType<Event> DRAG_START = new EType<> ();
		public static final EType<Event> DRAG_END = new EType<> ();
		public static final EType<PointerClickEvent> PRESSED = new EType<> ();
		public static final EType<PointerClickEvent> RELEASED = new EType<> ();
		private EType () {}
	}

	private static PointerInput INSTANCE;

	public static PointerInput getInstance() {
		if (INSTANCE != null ) { return INSTANCE; }
		throw new RuntimeException ("Pointer device not properly configured");
	}

	public static void configure (Game game) {
		INSTANCE = new PointerInput (game);
	}

	protected EventListenerCatalog listeners = new EventListenerCatalog ();

	protected final Game game;
	protected double xpos;
	protected double ypos;

	protected boolean[] released = new boolean[3];
	protected boolean[] pressed = new boolean[3];
	protected int heldDownButton = -1;
	protected float dragOffsetX, dragOffsetY;

	public PointerInput (Game game) {
		this.game = game;
	}

	public <T extends Event> Subscription on (EType<T> type, EventListener<T> listener) {
		return listeners.register (type, listener);
	}

	public GLFWCursorPosCallbackI positionCallback () {
		return (long window, double xpos, double ypos) -> {
			listeners.fire (EType.MOVE, new PointerMoveEvent (xpos, ypos));

			this.xpos = xpos;
			this.ypos = ypos;

			if (heldDownButton == GLFW_MOUSE_BUTTON_MIDDLE) {
				int newScrollX = (int)(- xpos + dragOffsetX);
				int newScrollY = (int)(- ypos + dragOffsetY);
				int maxScrollX = (game.map[game.currentLayerNumber].width * Tile.SIZE) - (Game.width+1);
				int maxScrollY = (game.map[game.currentLayerNumber].height * Tile.SIZE) - (Game.height+1);

				if(newScrollX < 0) game.xScroll = 0;
				else game.xScroll = Math.min(newScrollX, maxScrollX);

				if(newScrollY < 0) game.yScroll = 0;
				else game.yScroll = Math.min(newScrollY, maxScrollY);
			}
		};
	}

	public GLFWMouseButtonCallbackI buttonsCallback () {
		return (long window, int button, int action, int mods) -> {
			// stop pesky gaming mice with their fancy buttons from crashing my entire game
			if (button > 3 || button < 0) { return; }
			if (action == GLFW_RELEASE) {
				released[button] = true;
				pressed[button] = false;
				heldDownButton = -1;
				listeners.fire(EType.RELEASED, new PointerClickEvent(button, action, this.xpos, this.ypos));
			} else if (action == GLFW_PRESS) {
				pressed[button] = true;
				released[button] = false;
				heldDownButton = button;
				listeners.fire(EType.PRESSED, new PointerClickEvent(button, action, this.xpos, this.ypos) );
				if (button == GLFW_MOUSE_BUTTON_MIDDLE) {
					dragOffsetX = getX();
					dragOffsetY = getY();
				}
			}
		};
	}

	public void resetLeftAndRight () {
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

	public boolean heldDown(int button) {
		return heldDownButton == button;
	}

	public float getDragOffsetX() {
		return dragOffsetX;
	}

	public float getDragOffsetY() {
		return dragOffsetY;
	}

	// the x and y of the tiles in the game that the mouse is on
	public int getTileX() {
		return (int) (xpos + game.xScroll) / Tile.SIZE;
	}

	public int getTileY() {
		return (int) (ypos + game.yScroll) / Tile.SIZE;
	}

	public int getTrueX() {
		return (int) xpos;
	}

	public int getTrueY() {
		return (int) ypos;
	}

	// x and y coord on the screen, in pixels , WITH OFFSET
	public float getX() {
		return (float) xpos + game.xScroll;
	}

	public float getY() {
		return (float) ypos + game.yScroll;
	}

}
