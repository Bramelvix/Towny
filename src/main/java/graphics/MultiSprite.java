package graphics;

import graphics.opengl.OpenGLUtils;
import util.vectors.*;

import java.nio.ByteOrder;

//A MultiSprite is a Sprite composed of multiple images overlaid on top of each other
public class MultiSprite implements Sprite {

	private final Vec2f[] texCoordArr; //array of UV coordinates for every texture used in this MultiSprite
	private final Vec2i[] texPosArr;

	protected int avgColor = -1;

	public MultiSprite(StaticSprite[] sprites) {
		texCoordArr = MultiSprite.spriteToCoords(sprites);
		texPosArr = MultiSprite.spriteToPos(sprites);
	}

	@Override
	public void draw(Vec3f pos) {
		for (Vec2f texPos : texCoordArr) {
			OpenGLUtils.getInstanceData().put(new Vec3f(OpenGLUtils.pToGL(pos.x, 'w'), OpenGLUtils.pToGL(pos.y, 'h'), 0.f), texPos);
		}
	}

	@Override
	public int getAvgColour() {
		if (avgColor == -1) { // trying to optimise this by making it so the average colour is only calculated when its actually accessed.
			avgColor = calculateAvgColour();
		}
		return avgColor;
	}

	@Override
	public Vec2f getTexCoords() {
		throw new UnsupportedOperationException("Cannot get texture coordinates from multisprite");
	}

	public int calculateAvgColour() {
		int[] averages = new int[texPosArr.length];
		for (int i = 0; i < texPosArr.length; i++) {
			averages[i] = Sprite.getAverageRGB(SPRITESHEET.getBuffer().order(ByteOrder.BIG_ENDIAN), texPosArr[i].x, texPosArr[i].y, SPRITESHEET.getWidth());
		}
		return Sprite.getAverageRGB(averages);
	}

	private static Vec2f[] spriteToCoords(Sprite[] sprites) {
		Vec2f[] texCoordArr = new Vec2f[sprites.length];
		for (int i = 0; i < sprites.length; i++) {
			texCoordArr[i] = (sprites[i].getTexCoords());
		}
		return texCoordArr;
	}

	private static Vec2i[] spriteToPos(StaticSprite[] sprites) {
		Vec2i[] texPosArr = new Vec2i[sprites.length];
		for (int i = 0; i < sprites.length; i++) {
			texPosArr[i] = sprites[i].pos;
		}
		return texPosArr;
	}
}
