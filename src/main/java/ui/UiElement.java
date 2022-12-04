package ui;

import util.vectors.Vec2f;
import util.vectors.Vec4f;

public abstract class UiElement {
	protected final Vec2f position; // x and y of the top left corner
	protected final Vec2f size; // width and height
	protected boolean visible; // is the element visible
	protected static final Vec4f colour = new Vec4f(0.3568f, 0.3686f, 0.3882f, 0.43137f);

	protected UiElement(Vec2f position, Vec2f size, boolean visible) {
		this.position = position;
		this.size = size;
		this.visible = visible;
	}

	protected UiElement(Vec2f position, Vec2f size) {
		this(position, size, true);
	}

	public abstract void render();

	public boolean mouseOver(double x, double y) {
		return mouseOver(x, y, 0);
	}

	public boolean mouseOver(double x, double y, int margin) {
		return x >= getX() - margin
				&& x <= getX() + getWidth() + margin
				&& y >= getY() - margin
				&& y <= getY() + getHeight() + margin;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return size.x;
	}

	public float getHeight() {
		return size.y;
	}
}
