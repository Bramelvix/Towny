package input;

import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import static org.lwjgl.glfw.GLFW.*;

public class MouseButton implements GLFWMouseButtonCallbackI {

	private static boolean[] released = new boolean[3];
	private static boolean[] pressed = new boolean[3];
	private static int heldDownButton = -1;
	private static int dragOffsetX, dragOffsetY;

	@Override
	public void invoke(long window, int button, int action, int mods) {
		if (button > 3 || button < 0) { //stop pesky gaming mice with their fancy buttons from crashing my entire game
			return;
		}
		if (action == GLFW_RELEASE) {
			released[button] = true;
			pressed[button] = false;
			heldDownButton = -1;
		} else if (action == GLFW_PRESS) {
			pressed[button] = true;
			released[button] = false;
			heldDownButton = button;
			if (button == GLFW_MOUSE_BUTTON_MIDDLE) {
				dragOffsetX = MousePosition.getX();
				dragOffsetY = MousePosition.getY();
			}

		}
	}

	public static boolean wasPressed(int button) {
		return button <= 3 && button >= 0 && pressed[button];
	}

	public static boolean wasReleased(int button) {
		return button <= 3 && button >= 0 && released[button];
	}

	public static void resetLeftAndRight() {
		released[GLFW_MOUSE_BUTTON_LEFT] = false;
		pressed[GLFW_MOUSE_BUTTON_LEFT] = false;
		released[GLFW_MOUSE_BUTTON_RIGHT] = false;
		pressed[GLFW_MOUSE_BUTTON_RIGHT] = false;

	}

	public static boolean heldDown(int button) {
		return heldDownButton==button;
	}

	static int getDragOffsetX() {
		return dragOffsetX;
	}

	static int getDragOffsetY() {
		return dragOffsetY;
	}

}
