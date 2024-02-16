package ui;

import graphics.opengl.OpenGLUtils;
import input.PointerInput;
import util.vectors.Vec2f;

import static events.EventListener.onlyWhen;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class SelectionSquare extends UiElement {
	private final Vec2f ingame = new Vec2f(0); // INGAME
	private final Vec2f offset = new Vec2f(0);

	public SelectionSquare() {
		super(new Vec2f(0), new Vec2f(0), false);
		PointerInput.getInstance().on(PointerInput.EType.DRAG, onlyWhen(
				event -> visible && event.button == GLFW_MOUSE_BUTTON_LEFT,
				event -> {
					size.x = (float) event.x - position.x;
					size.y = (float) event.y - position.y;
				}
		));
		PointerInput.getInstance().on(PointerInput.EType.DRAG_START, onlyWhen(
				event -> event.button == GLFW_MOUSE_BUTTON_LEFT,
				event -> {
					position.x = (float) event.x;
					position.y = (float) event.y;
					ingame.x = position.x + offset.x;
					ingame.y = position.y + offset.y;
				}
		));
	}

	void reset() {
		visible = false;
		ingame.x = 0;
		ingame.y = 0;
		position.x = 0;
		position.y = 0;
		size.x = 0;
		size.y = 0;
	}

	public void show() {
		visible = true;
	}

	public void update(float x, float y) {
		this.offset.x = x;
		this.offset.y = y;
	}

	public void render() {
		if (visible) {
			OpenGLUtils.drawFilledSquare(position, size, colour);
		}
	}

	@Override
	public float getX() {
		return (size.x < 0) ? ingame.x + size.x : ingame.x;
	}

	@Override
	public float getY() {
		return (size.y < 0) ? ingame.y + size.y : ingame.y;
	}

	@Override
	public float getWidth() {
		return Math.abs(size.x);
	}

	@Override
	public float getHeight() {
		return Math.abs(size.y);
	}

}
