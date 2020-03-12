package graphics;

import graphics.opengl.OpenGLUtils;
import map.Tile;
import util.vectors.Vec2f;
import util.vectors.Vec2i;
import util.vectors.Vec3f;

import java.nio.ByteOrder;

//sprites in the game
public class Sprite {

	public static final float SIZE = Tile.SIZE; // 48
	private final Vec2f texCoords; //The location of the texture coordinates for this sprite in it's spritesheet
	private final Vec2f texSize; //The size of the texture (in uv coordinates)

	//TODO make this the actual average colour, right now its just the first pixel
	private final int avgColor;

	protected Sprite(int x, int y, int sheetIndex) {
		this(new Vec2i(x,y), sheetIndex);
	}

	protected Sprite(Vec2i pos, int sheetIndex) {
		pos.y = SpritesheetHashtable.getBaselineY(pos.y, sheetIndex);
		Spritesheet spritesheet = SpritesheetHashtable.getCombined();
		texCoords = new Vec2f((pos.x * SIZE) / spritesheet.getWidth(), (pos.y * SIZE) / spritesheet.getHeight());

		texSize = new Vec2f(SIZE / spritesheet.getWidth(), SIZE / spritesheet.getHeight());

		avgColor = spritesheet.getBuffer().order(ByteOrder.BIG_ENDIAN).getInt((int) (((pos.y*SIZE)*spritesheet.getWidth()*4) + pos.x*SIZE*4));

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

	public int getAvgColor() {
		return avgColor;
	}


}
