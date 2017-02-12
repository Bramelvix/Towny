package graphics.ui;

import java.awt.Color;
import java.awt.Graphics;

import input.Mouse;

public class SelectionSquare {
	private static final Color COL = new Color(91, 94, 99, 110); // colour of
																	// the
																	// square
	private int x, y; // INGAME
	private int xcoord, ycoord; // ONSCREEN
	private int width, height, widthteken, heightteken;
	private boolean visible;
	private boolean startedDragging;

	public void update(Mouse mouse) {
		if (mouse.getDrag())
			startedDragging = true;
		if (visible) {
			if (startedDragging) {
				widthteken = mouse.getX()*3 - xcoord;
				width = widthteken/3;
				heightteken = mouse.getY()*3 - ycoord;
				height = heightteken/3;
			}
		}

	}

	public void reset() {
		visible = false;
		xcoord = 0;
		ycoord = 0;
		x = 0;
		y = 0;
		width = 0;
		height = 0;
		heightteken = 0;
		startedDragging = false;
		widthteken = 0;
	}

	public void show(Mouse mouse) {
		if (!visible) {
			x = mouse.getX();
			y = mouse.getY();
			xcoord = mouse.getX() * 3;
			ycoord = mouse.getY() * 3;
			visible = true;
		}
	}

	public void render(Graphics g) {
		if (visible) {
			g.setColor(COL);
			g.fillRect(xcoord, ycoord, widthteken, heightteken);
		}
	}

	public int getX() {
		if (width < 0) {
			x += width;

		}
		return x;
	}

	public int getY() {
		if (height < 0) {
			y += height;
		}
		return y;
	}

	public int getWidth() {
		if (width < 0) {
			width = -width;

		}
		return width;
	}

	public int getHeight() {
		if (height < 0) {
			height = -height;
		}
		return height;
	}

	public boolean isVisible() {
		return visible;
	}

}
