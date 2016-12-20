package graphics;


import entity.mob.Villager;
import graphics.ui.icon.Icon;
import map.Tile;

public class Screen {
	public int width;
	public int height;
	public int[] pixels;
	public int xOffset, yOffset;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = tile.sprite.pixels[x + y * tile.sprite.SIZE];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderVillager(int xp, int yp, Villager e) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < 16; y++) {
			int ya = y + yp;
			for (int x = 0; x < 16; x++) {
				int xa = x + xp;
				if (xa < -16 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = e.sprite.pixels[x + y * 16];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}

	}

	public void renderSelection(int xp, int yp, Villager e) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < 16; y++) {
			int ya = y + yp;
			for (int x = 0; x < 16; x++) {
				int xa = x + xp;
				if (xa < -16 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				if (e.isSelected() && (x == 0 || x == 16 - 1 || y == 0 || y == 16 - 1)) {
					pixels[xa + ya * width] = 0xf44242;

				}

			}
		}
	}

	public void renderEntity(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < 16; y++) {
			int ya = y + yp;
			for (int x = 0; x < 16; x++) {
				int xa = x + xp;
				if (xa < -16 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[x + y * 16];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderIcon(int xp, int yp, Icon i) {
		for (int y = 0; y < i.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < i.getWidth(); x++) {
				int xa = x + xp;
				if (xa < -16 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = 0;
				if ((i.hoverOn() || i.selected())
						&& (x == 0 || x == i.getWidth() - 1 || y == 0 || y == i.getHeight() - 1)) {
					col = 0xff0000;
				} else {
					col = i.pixels[x + y * i.getWidth()];
				}
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}

	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
