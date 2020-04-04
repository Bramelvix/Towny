package graphics;

import graphics.opengl.OpenGLUtils;
import map.Tile;
import util.vectors.Vec2f;
import util.vectors.Vec2i;
import util.vectors.Vec3f;
import util.vectors.Vec4i;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

//sprites in the game
public class Sprite {

	public static final float SIZE = Tile.SIZE; // 48
	private final Vec2f texCoords; //The location of the texture coordinates for this sprite in it's spritesheet
	private final Vec2f texSize; //The size of the texture (in uv coordinates)
	private int avgColor;
	private final Spritesheet spritesheet;
	private final Vec2i pos;

	protected Sprite(int x, int y, int sheetIndex) {
		this(new Vec2i(x,y), sheetIndex);
	}

	protected Sprite(Vec2i pos, int sheetIndex) {
		this.pos = pos;
		pos.y = SpritesheetHashtable.getBaselineY(pos.y, sheetIndex);
		spritesheet = SpritesheetHashtable.getCombined();
		texCoords = new Vec2f((pos.x * SIZE) / spritesheet.getWidth(), (pos.y * SIZE) / spritesheet.getHeight());
		texSize = new Vec2f(SIZE / spritesheet.getWidth(), SIZE / spritesheet.getHeight());
		avgColor = -1;

	}

	private static Vec4i getARGB(ByteBuffer buffer, int x, int y, int width) {
		int pixel = buffer.getInt(((y * width) + x) * 4);
		int a = ((pixel >> 24) & 0xFF);
		int r = ((pixel >> 16) & 0xFF);
		int g = ((pixel >> 8) & 0xFF);
		int b = ((pixel) & 0xFF);
		return new Vec4i(a, r, g, b);
	}

	private static int getAverageRGB(ByteBuffer buffer, int x, int y, int width) {
		double r = 0;
		double g = 0;
		double b = 0;
		double a = 0;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				Vec4i pixel = getARGB(buffer, (int) (x * SIZE) + i, (int) (y * SIZE) + j, width);
				a += pixel.x * pixel.x;
				r += pixel.y * pixel.y;
				g += pixel.z * pixel.z;
				b += pixel.w * pixel.w;
			}
		}
		return new Color(
			(float) Math.sqrt(r / (SIZE*SIZE)) / 255f,
			(float) Math.sqrt(g / (SIZE*SIZE)) / 255f,
			(float) Math.sqrt(b / (SIZE*SIZE)) / 255f,
			(float) Math.sqrt(a / (SIZE*SIZE)) / 255f
		).getRGB();
	}

	public void draw(Vec3f pos) {
		OpenGLUtils.instanceData.put(new Vec3f(OpenGLUtils.pToGL(pos.x, 'w'), OpenGLUtils.pToGL(pos.y, 'h'), pos.z), texCoords);
	}

	public Vec2f getTexCoords() {
		return texCoords;
	}

	public Vec2f getTexSize() {
		return texSize;
	}

	public int getAvgColour() {
		if (avgColor == -1) { // trying to optimise this by making it so the average colour is only calculated when its actually accessed.
			avgColor = getAverageRGB(spritesheet.getBuffer().order(ByteOrder.BIG_ENDIAN), pos.x, pos.y, spritesheet.getWidth());
		}
		return avgColor;
	}


}
