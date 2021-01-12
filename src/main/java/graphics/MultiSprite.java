package graphics;

import graphics.opengl.OpenGLUtils;
import util.vectors.Vec2f;
import util.vectors.Vec2i;
import util.vectors.Vec3f;

//A MultiSprite is a Sprite composed of multiple images overlaid on top of each other
public class MultiSprite extends StaticSprite {

	private final Vec2f[] texCoordArr; //array of UV coordinates for every texture used in this MultiSprite
	private final Sprite bottomSprite;

	public MultiSprite(Sprite[] sprites, int sheetIndex) {
		super(new Vec2i(0,0), sheetIndex);
		this.bottomSprite = sprites[0];
		this.texCoordArr = spriteToCoords(sprites);
	}

	public Vec2f[] getTexArr() {
		return texCoordArr;
	}

	@Override
	public void draw(Vec3f pos) {
		for (Vec2f texPos : texCoordArr) {
			OpenGLUtils.instanceData.put(new Vec3f(OpenGLUtils.pToGL(pos.x, 'w'), OpenGLUtils.pToGL(pos.y, 'h'), 0.f), texPos);
		}
	}

	@Override
	public int getAvgColour() {
		return bottomSprite.getAvgColour();
	}

	private static Vec2f[] spriteToCoords(Sprite[] sprites) {
		Vec2f[] texCoordArr = new Vec2f[sprites.length];
		for (int i = 0; i < sprites.length; i++) {
			texCoordArr[i] = (sprites[i].getTexCoords());
		}
		return texCoordArr;
	}
}
