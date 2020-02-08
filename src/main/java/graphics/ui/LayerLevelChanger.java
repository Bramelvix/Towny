package graphics.ui;

import graphics.OpenglUtils;
import graphics.ui.icon.Icon;
import input.PointerInput;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;


public class LayerLevelChanger {

	private static final float r = 0.3568f, g = 0.3686f, b = 0.3882f, a = 0.43137f; //colour for background
	private int x, y; // x and y of the top left corner
	private int width, height; // width and height
	private Icon up, down;
	private int z;
	private int mapHeight;

	LayerLevelChanger(int x, int y,int width, int height, int mapHeight) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.mapHeight = mapHeight;
		init();
	}

	private void init() {
		up = new Icon(x + 10, y + 5, "/icons/plain-arrow-up.png", 0.065f);
		down = new Icon(x + 100, y + 5, "/icons/plain-arrow-down.png", 0.065f);
	}

	public void update(PointerInput pointer, int z) {
		up.update(pointer);
		down.update(pointer);
		if (this.z != z) {
			this.z = z;
		}
		if (pointer.wasPressed(GLFW_MOUSE_BUTTON_LEFT)) {
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
		OpenglUtils.drawFilledSquare(x, y, width, height, r, g, b, a);
		up.render();
		OpenglUtils.drawText(-z + "", x + 60, y + 10);
		down.render();
	}

	public int getZ() {
		return z;
	}

}
