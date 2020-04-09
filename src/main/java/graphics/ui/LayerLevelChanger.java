package graphics.ui;

import graphics.opengl.OpenGLUtils;
import graphics.ui.icon.Icon;
import input.PointerInput;
import util.vectors.Vec2f;
import util.vectors.Vec4f;

import java.io.IOException;


public class LayerLevelChanger extends UiElement {
	private final Icon up;
	private final Icon down;
	private int z;

	LayerLevelChanger(int x, int y,int width, int height, PointerInput pointer) throws IOException {
		super(new Vec2f(x, y), new Vec2f(width, height));
		up = new Icon(x + 10, y + 5, "/icons/plain-arrow-up.png", 0.065f, pointer);
		down = new Icon(x + 100, y + 5, "/icons/plain-arrow-down.png", 0.065f, pointer);
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
