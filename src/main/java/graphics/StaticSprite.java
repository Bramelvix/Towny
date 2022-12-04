package graphics;

import graphics.opengl.OpenGLUtils;
import util.vectors.Vec2f;
import util.vectors.Vec2i;
import util.vectors.Vec3f;

import java.nio.ByteOrder;

public class StaticSprite implements Sprite {
	private final Vec2f texCoords; //The location of the texture coordinates for this sprite in its spritesheet
	protected int avgColor = -1;
	protected final Vec2i pos;

	protected StaticSprite(int x, int y, int sheetIndex) {
		this(new Vec2i(x, y), sheetIndex);
	}

	protected StaticSprite(Vec2i pos, int sheetIndex) {
		this.pos = pos;
		pos.y = SpritesheetHashtable.getBaselineY(pos.y, sheetIndex);
		texCoords = new Vec2f((pos.x * SIZE) / SPRITESHEET.getWidth(), (pos.y * SIZE) / SPRITESHEET.getHeight());
	}

	@Override
	public void draw(Vec3f pos) {
		OpenGLUtils.getInstanceData().put(new Vec3f(OpenGLUtils.pToGL(pos.x, 'w'), OpenGLUtils.pToGL(pos.y, 'h'), pos.z), texCoords);
	}

	@Override
	public Vec2f getTexCoords() {
		return texCoords;
	}

	@Override
	public int getAvgColour() {
		if (avgColor == -1) { // trying to optimise this by making it so the average colour is only calculated when its actually accessed.
			avgColor = Sprite.getAverageRGB(SPRITESHEET.getBuffer().order(ByteOrder.BIG_ENDIAN), pos.x, pos.y, SPRITESHEET.getWidth());
		}
		return avgColor;
	}
}
