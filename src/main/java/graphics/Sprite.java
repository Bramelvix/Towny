package graphics;

import graphics.opengl.OpenGLUtils;
import map.Tile;
import util.ImgInfo;
import util.vectors.Vec2f;

//sprites in the game
public class Sprite {

	private final int id;
	private final int spriteSheetID;
	public static final int SIZE = Tile.SIZE; // 48
	public final int[] pixels;
	private Vec2f texCoords = new Vec2f(0); //The location of the texture coordinates for this sprite in it's spritesheet
	private Vec2f texSize = new Vec2f(1); //The size of the texture (in uv coordinates)

	protected Sprite(int x, int y, Spritesheet sheet) {
		pixels = load(x * SIZE, y * SIZE, sheet);
		//id = OpenGLUtils.loadTexture(pixels, SIZE, SIZE);
		spriteSheetID = sheet.id;
		id = spriteSheetID;
		texCoords.x = (float) (x*SIZE) / sheet.width; //0.01754386f*4;
		texCoords.y = (float) (y*SIZE) / sheet.height; //0.032258064f*0;

		texSize.x = (float) SIZE / sheet.width;
		texSize.y = ((float) SIZE / sheet.height);

		System.out.println("______");
		System.out.println(texCoords.x + "  " + texCoords.y);
		System.out.println((float)SIZE / sheet.width);
	}

	public Sprite(int[] pixels) {
		this.pixels = pixels;
		id = OpenGLUtils.loadTexture(this.pixels, SIZE, SIZE);
		spriteSheetID = 0;
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

	public void draw(Vec2f pos, Vec2f offset) {
		//OpenGLUtils.drawTexturedQuadScaled(pos, new Vec2f(SIZE), offset, id);
		//System.out.println(texCoords.x);
		OpenGLUtils.drawCool(pos, new Vec2f(SIZE), offset, texCoords, texSize, id);
	}

}
