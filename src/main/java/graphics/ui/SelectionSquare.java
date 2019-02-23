package graphics.ui;

import java.awt.Color;
import graphics.OpenglUtils;
import input.MouseButton;
import input.MousePosition;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class SelectionSquare {
	private static final Color COL = new Color(91, 94, 99, 110); // colour of the square
	private int x, y; // ONSCREEN
	private int ingameX, ingameY; // INGAME
	private int width, height;
	private boolean visible;

	public void update() {
		if (visible) {
			if (MouseButton.heldDown(GLFW_MOUSE_BUTTON_LEFT)) {
				width = MousePosition.getX() - x;
				height = MousePosition.getY() - y;
			}
		}

	}

	void reset() {
		visible = false;
		ingameX = 0;
		ingameY = 0;
		x = 0;
		y = 0;
		width = 0;
		height = 0;
	}

	public void init() {
		if (!visible) {
			x = MousePosition.getTrueX();
			y = MousePosition.getTrueY();
			ingameX = MousePosition.getX();
			ingameY =  MousePosition.getY();
			visible = true;
		}
	}

	public void render() {
		if (visible) {
			OpenglUtils.drawFilledSquare(x,y,width,height,COL.getRed()/255f,COL.getGreen()/255f,COL.getBlue()/255f,COL.getAlpha()/255f);
		}
	}

	public int getX() {
		return (width < 0) ? ingameX + width : ingameX;
	}

	public int getY() {
		return (height < 0) ? ingameY += height : ingameY;
	}

	public int getWidth() {
		return (width < 0) ? width = -width : width;
	}

	public int getHeight() {
		return (height < 0) ? height = -height : height;
	}

}
