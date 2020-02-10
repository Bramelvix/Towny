package input;

import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Keyboard implements GLFWKeyCallbackI {

	private static boolean[] keys = new boolean[65536];

	@Override
	public void invoke(long window, int keycode, int i1, int i2, int i3) {
		keys[keycode] = i2 != GLFW_RELEASE;
	}

	public static boolean isKeyDown(int keycode) {
		return keys[keycode];
	}

}
