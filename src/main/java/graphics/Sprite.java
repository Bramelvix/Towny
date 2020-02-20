package graphics;

import graphics.opengl.OpenGLUtils;
import map.Tile;
import util.vectors.Vec2f;
import util.vectors.Vec2i;

import java.nio.ByteOrder;

//sprites in the game
public class Sprite {

	private final int spriteSheetID;
	public static final int SIZE = Tile.SIZE; // 48
	private Vec2f texCoords = new Vec2f(0); //The location of the texture coordinates for this sprite in it's spritesheet
	Vec2f texSize = new Vec2f(1); //The size of the texture (in uv coordinates)

	//TODO make this the actual average color, right now its just the first pixel
	int avgColor;

	protected Sprite(int x, int y, Spritesheet sheet) {
		this(new Vec2i(x,y), sheet);
	}

	protected Sprite(Vec2i pos, Spritesheet sheet) {
		spriteSheetID = sheet.getId();
		texCoords.x = (float) (pos.x*SIZE) / sheet.getWidth();
		texCoords.y = (float) (pos.y*SIZE) / sheet.getHeight();

		texSize.x = (float) SIZE / sheet.getWidth();
		texSize.y = ((float) SIZE / sheet.getHeight());

		avgColor = sheet.getBuffer().order(ByteOrder.BIG_ENDIAN).getInt(((pos.y*SIZE)*sheet.getWidth()*4) + pos.x*SIZE*4 );

	}

	public void draw(Vec2f pos, Vec2f offset) {
		OpenGLUtils.drawTexturedQuad(pos, new Vec2f(SIZE), offset, texCoords, texSize, spriteSheetID);
	}

	public int getSpriteSheetID() {
		return spriteSheetID;
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
