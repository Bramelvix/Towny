package input;

import main.Game;
import map.Tile;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glTranslatef;

public class MouseButton implements GLFWMouseButtonCallbackI {
    private static boolean[] released = new boolean[3];
    private static boolean[] pressed = new boolean[3];
    private static int dragOffsetX, dragOffsetY;

    public MouseButton() {
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        if (button > 3 || button < 0) { //stop pesky gaming mice with their fancy buttons from crashing my entire game
            return;
        }
        if (action == GLFW_RELEASE) {
            released[button] = true;
            pressed[button] = false;
        } else if (action == GLFW_PRESS) {
            pressed[button] = true;
            released[button] = false;
            if (button == GLFW_MOUSE_BUTTON_MIDDLE) {
                dragOffsetX = MousePosition.getMouseX();
                dragOffsetY = MousePosition.getMouseY();
            }

        }
    }

    public static boolean wasPressed(int button) {
        if (button > 3 || button < 0) {
            return false;
        }
        return pressed[button];
    }

    public static boolean wasReleased(int button) {
        if (button > 3 || button < 0) {
            return false;
        }
        return released[button];
    }

    public static void resetReleased() {
        for (int i = 0; i < released.length; i++) {
            released[i] = false;
        }
    }

    public static boolean heldDown(int button) {
        return wasPressed(button) && !wasReleased(button);
    }

    public static int getDragOffsetX() {
        return dragOffsetX;
    }

    public static int getDragOffsetY() {
        return dragOffsetY;
    }
}
