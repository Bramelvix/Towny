package graphics;

import java.util.Random;

import entity.item.weapon.WeaponMaterial;
import entity.item.weapon.WeaponType;
import main.Game;
import map.Tile;

//sprites in the game
public class Sprite {
	private int x, y; // x and y on the spritesheet
	public static final int SIZE = Tile.SIZE; // 16
	public int[] pixels; // pixels array
	private Spritesheet sheet; // spritesheet

	protected Sprite(int x, int y, Spritesheet sheet) {
		this.x = x * SIZE + (x * sheet.getMargin());
		this.y = y * SIZE + (y * sheet.getMargin());
		this.sheet = sheet;
		pixels = new int[SIZE * SIZE];
		load();
	}

	protected Sprite() {
		pixels = new int[SIZE * SIZE];
	}


	// load a sprites pixels into the pixel array
	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.getPixels()[(x + this.x) + (y + this.y) * sheet.getWidth()];
			}
		}
	}


}
