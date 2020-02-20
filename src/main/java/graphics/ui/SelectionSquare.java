package graphics.ui;

import graphics.opengl.OpenGLUtils;
import input.PointerInput;
import util.vectors.Vec2f;
import util.vectors.Vec4f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class SelectionSquare {

	private static final Vec4f colour = new Vec4f(0.3568f, 0.3686f, 0.3882f, 0.43137f); //colour of the square
	private final Vec2f position; // ONSCREEN
	private final Vec2f ingame; // INGAME
	private float width, height;
	private boolean visible;

	public SelectionSquare() {
		position = new Vec2f(0);
		ingame = new Vec2f(0);
	}

	public void update(PointerInput pointer) {
		if (visible) {
			if (pointer.heldDown(GLFW_MOUSE_BUTTON_LEFT)) {
				width = pointer.getTrueX() - position.x;
				height = pointer.getTrueY() - position.y;
			}
		}
	}

	void reset() {
		visible = false;
		ingame.x = 0;
		ingame.y = 0;
		position.x = 0;
		position.y = 0;
		width = 0;
		height = 0;
	}

	public void init(PointerInput pointer) {
		if (!visible) {
			position.x = pointer.getTrueX();
			position.y = pointer.getTrueY();
			ingame.x = pointer.getX();
			ingame.y =  pointer.getY();
			visible = true;
		}
	}

	public void render(Vec2f offset) {
		if (visible) {
			OpenGLUtils.drawFilledSquare(position, new Vec2f(width, height), offset, colour);
		}
	}

	public float getX() {
		return (width < 0) ? ingame.x + width : ingame.x;
	}

	public float getY() {
		return (height < 0) ? ingame.y += height : ingame.y;
	}

	public float getWidth() {
		return (width < 0) ? width = -width : width;
	}

	public float getHeight() {
		return (height < 0) ? height = -height : height;
	}

}
