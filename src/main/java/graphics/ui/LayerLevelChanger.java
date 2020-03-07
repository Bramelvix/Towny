package graphics.ui;

import graphics.opengl.OpenGLUtils;
import graphics.ui.icon.Icon;
import input.PointerInput;
import util.vectors.Vec2f;
import util.vectors.Vec4f;

import java.io.IOException;


public class LayerLevelChanger {

	private static final Vec4f colour = new Vec4f(0.3568f, 0.3686f, 0.3882f, 0.43137f); //colour for background
	private final int x;
	private final int y; // x and y of the top left corner
	private final int width;
	private final int height; // width and height
	private final Icon up;
	private final Icon down;

	LayerLevelChanger(int x, int y,int width, int height, PointerInput pointer) throws IOException {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		up = new Icon(x + 10, y + 5, "/icons/plain-arrow-up.png", 0.065f, pointer);
		down = new Icon(x + 100, y + 5, "/icons/plain-arrow-down.png", 0.065f, pointer);
	}

	public void init(PointerInput pointer, Runnable actionUp, Runnable actionDown) {
		up.setOnClick(pointer, actionUp);
		down.setOnClick(pointer, actionDown);
	}

	public void render(int z) {
		OpenGLUtils.drawFilledSquare(new Vec2f(x, y), new Vec2f(width, height), new Vec2f(0, 0), colour);
		up.render();
		OpenGLUtils.drawText(-z + "", x + 60, y + 10);
		down.render();
	}

}
