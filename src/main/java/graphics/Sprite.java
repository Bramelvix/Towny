package graphics;

import graphics.opengl.OpenGLUtils;
import map.Tile;

//sprites in the game
public class Sprite {

	private final int id;
	public static final int SIZE = Tile.SIZE; // 48
	public final int[] pixels;

	protected Sprite(int x, int y, Spritesheet sheet) {
		pixels = load(x * SIZE, y * SIZE, sheet);
		id = OpenGLUtils.loadTexture(pixels, SIZE, SIZE);
	}

	public Sprite(int[] pixels) {
		this.pixels = pixels;
		id = OpenGLUtils.loadTexture(this.pixels, SIZE, SIZE);
	}

	// load a sprites pixels into the pixel array
	private int[] load(int xa, int ya, Spritesheet sheet) {
		int[] pixels = new int[SIZE * SIZE];
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.getPixels()[(x + xa) + (y + ya) * sheet.getWidth()];
			}
		}
		return pixels;
	}

	public void draw(int x, int y, float xOffset, float yOffset) {
		OpenGLUtils.drawTexturedQuadScaled(id, x, y, xOffset, yOffset, SIZE);
	}

}
