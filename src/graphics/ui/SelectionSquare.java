package graphics.ui;

import java.awt.Color;
import java.awt.Graphics;

import input.Mouse;
import main.Game;

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
		startedDragging = mouse.getDrag();
		if (visible) {
			if (startedDragging) {
				widthteken = mouse.getTrueX() * Game.SCALE - xcoord;
				width = widthteken / Game.SCALE;
				heightteken = mouse.getTrueY() * Game.SCALE - ycoord;
				height = heightteken / Game.SCALE;
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
			xcoord = mouse.getTrueX() * Game.SCALE;
			ycoord = mouse.getTrueY() * Game.SCALE;
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
		return (width < 0) ? x + width : x;
	}

	public int getY() {
		return (height < 0) ? y += height : y;
	}

	public int getWidth() {
		return (width < 0) ? width = -width : width;
	}

	public int getHeight() {
		return (height < 0) ? height = -height : height;
	}

	public boolean isVisible() {
		return visible;
	}

}
