package graphics.ui;

import util.vectors.Vec2f;
import util.vectors.Vec4f;

public abstract class UiElement {
	protected final Vec2f position; // x and y of the top left corner
	protected final Vec2f size; // width and height
	protected boolean visible; // is the element visible
	protected static final Vec4f colour = new Vec4f(0.3568f, 0.3686f, 0.3882f, 0.43137f);

	public UiElement(Vec2f position, Vec2f size, boolean visible) {
		this.position = position;
		this.size = size;
		this.visible = visible;
	}

	public UiElement(Vec2f position, Vec2f size) {
		this(position, size, true);
	}

	public abstract void render();
}
