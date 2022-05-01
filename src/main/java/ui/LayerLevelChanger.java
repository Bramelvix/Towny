package ui;

import graphics.opengl.OpenGLUtils;
import input.PointerInput;
import ui.icon.Icon;
import util.vectors.Vec2f;


public class LayerLevelChanger extends UiElement {
	private final Icon up;
	private final Icon down;
	private int z;

	LayerLevelChanger(int x, int y, int width, int height, PointerInput pointer) {
		super(new Vec2f(x, y), new Vec2f(width, height));
		up = new Icon(x + 10f, y + 5f, "/icons/plain-arrow-up.png", 0.065f, pointer);
		down = new Icon(x + 100f, y + 5f, "/icons/plain-arrow-down.png", 0.065f, pointer);
	}

	public void init(PointerInput pointer, Runnable actionUp, Runnable actionDown) {
		up.setOnClick(pointer, actionUp);
		down.setOnClick(pointer, actionDown);
	}

	public void setZ(int z) {
		this.z = z;
	}

	@Override
	public void render() {
		OpenGLUtils.drawFilledSquare(position, size, new Vec2f(0, 0), colour);
		up.render();
		OpenGLUtils.drawText(-z + "", position.x + 60, position.y + 10);
		down.render();
	}

	void destroy() {
		up.destroy();
		down.destroy();
	}

}
