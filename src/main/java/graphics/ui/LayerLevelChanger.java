package graphics.ui;

import graphics.opengl.OpenGLUtils;
import graphics.ui.icon.Icon;
import input.PointerClickEvent;
import input.PointerInput;
import util.vectors.Vec2f;
import util.vectors.Vec4f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;


public class LayerLevelChanger {

	private static final float r = 0.3568f, g = 0.3686f, b = 0.3882f, a = 0.43137f; //colour for background
	private final int x;
	private final int y; // x and y of the top left corner
	private final int width;
	private final int height; // width and height
	private final Icon up;
	private final Icon down;
	private int z;
	private final int mapHeight;

	LayerLevelChanger(int x, int y,int width, int height, int mapHeight, PointerInput pointer) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.mapHeight = mapHeight;
		up = new Icon(x + 10, y + 5, "/icons/plain-arrow-up.png", 0.065f, pointer);
		down = new Icon(x + 100, y + 5, "/icons/plain-arrow-down.png", 0.065f, pointer);
		pointer.on(PointerInput.EType.RELEASED, this::updateClick);
	}

	public void updateClick(PointerClickEvent event) {
		if (event.button == GLFW_MOUSE_BUTTON_LEFT) {
			if (up.hoverOn()) {
				if (this.z != 0) {
					this.z--;
				}
			} else if (down.hoverOn()) {
				if (this.z != mapHeight - 1) {
					this.z++;
				}
			}
		}
	}

	public void render() {
		OpenGLUtils.drawFilledSquare(new Vec2f(x, y), new Vec2f(width, height), new Vec2f(0, 0), new Vec4f(r, g, b, a));
		up.render();
		OpenGLUtils.drawText(-z + "", x + 60, y + 10);
		down.render();
	}

	public void setZ(int z) {
		if (this.z != z) {
			this.z = z;
		}
	}

	public int getZ() {
		return z;
	}

}
