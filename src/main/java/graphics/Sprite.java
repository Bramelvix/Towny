package graphics;

import graphics.opengl.OpenGLUtils;
import map.Tile;
import util.vectors.Vec2f;
import util.vectors.Vec2i;
import util.vectors.Vec3f;

import java.nio.ByteOrder;

//sprites in the game
public class Sprite {

	public static final int SIZE = Tile.SIZE; // 48
	private final Vec2f texCoords = new Vec2f(0); //The location of the texture coordinates for this sprite in it's spritesheet
	private final Vec2f texSize = new Vec2f(1); //The size of the texture (in uv coordinates)

	//TODO make this the actual average colour, right now its just the first pixel
	int avgColor;

	protected Sprite(int x, int y, int sheetIndex) {
		this(new Vec2i(x,y), sheetIndex);
	}

	protected Sprite(Vec2i pos, int sheetIndex) {
		pos.y = SpritesheetHashtable.getBaselineY(pos.y, sheetIndex);
		Spritesheet spritesheet = SpritesheetHashtable.getCombined();
		texCoords.x = (float) (pos.x*SIZE) / spritesheet.getWidth();
		texCoords.y = (float) (pos.y*SIZE) / spritesheet.getHeight();

		texSize.x = (float) SIZE / spritesheet.getWidth();
		texSize.y = ((float) SIZE / spritesheet.getHeight());


		avgColor = spritesheet.getBuffer().order(ByteOrder.BIG_ENDIAN).getInt(((pos.y*SIZE)*spritesheet.getWidth()*4) + pos.x*SIZE*4 );

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
