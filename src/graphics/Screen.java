package graphics;

import entity.Entity;
import entity.Tree;
import map.Tile;

//the game screen (the game world and the items in it, NOT INCLUDING THE UI)
public class Screen {
	public int width; // screen width
	public int height; // screen height
	public int[] pixels; // array of pixels (is 1d but is actually 2d)
	public int xOffset, yOffset; // the offset of the screen

	// constructor
	public Screen(int width, int height, int[] pixels) {
		this.pixels = pixels;
		this.width = width;
		this.height = height;
	}

	// empties the pixels array
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	// render the red square around selected entities
	public void renderSelection(int xp, int yp, Entity e) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < Sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < Sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) {
					continue;
				}
				if (e.isSelected() && (x == 0 || x == Sprite.SIZE - 1 || y == 0 || y == Sprite.SIZE - 1)) {
					pixels[xa + ya * width] = 0xf44242;

				}

			}
		}
	}

	// render trees
	public void renderTree(int xp, int yp, Tree e) {
		renderSprite(xp, yp, e.sprite);
		renderSprite(xp, yp - Tile.SIZE, e.extraSprite, e.isSelected(), true);

	}

	// render sprites
	public void renderSprite(int xp, int yp, Sprite e) {
		renderSprite(xp, yp, e, false, true);

	}

	public void renderSprite(int xp, int yp, Sprite e, boolean selected, boolean forceOffset) {
		if (forceOffset) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < Sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < Sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				int col = e.pixels[x + y * Sprite.SIZE];
				if (selected && (x == 0 || x == Sprite.SIZE - 1 || y == 0)) {
					col = 0xf44242;
				}
				if (col != 0xffff00ff) {
					pixels[xa + ya * width] = col;
				}
			}
		}

	}

	// sets the offset for the screen
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
