package graphics;

import graphics.opengl.OpenGLUtils;
import util.vectors.Vec2f;
import util.vectors.Vec2i;
import util.vectors.Vec3f;

//A MultiSprite is a Sprite composed of multiple images overlaid on top of each other
public class MultiSprite extends StaticSprite {

	private final Vec2f[] texCoordArr; //array of UV coordinates for every texture used in this MultiSprite

	public MultiSprite(Vec2f[] texCoordList, int sheetIndex) {
		super(new Vec2i(0,0), sheetIndex);
		this.texCoordArr = texCoordList;
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
}
