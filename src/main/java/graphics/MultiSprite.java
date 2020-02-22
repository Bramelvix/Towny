package graphics;

import graphics.opengl.InstanceData;
import graphics.opengl.OpenGLUtils;
import util.vectors.Vec2f;
import util.vectors.Vec2i;
import util.vectors.Vec3f;

//A MultiSprite is a Sprite composed of multiple images overlaid on top of each other
public class MultiSprite extends Sprite{

	private Vec2f[] texCoordList; //array of UV coordinates for every texture used in this MultiSprite

	public MultiSprite(Vec2f[] texCoordList, Spritesheet sheet) {
		super(new Vec2i(0,0), sheet);
		this.texCoordList = texCoordList;
	}

	public Vec2f[] getTexList() {
		return texCoordList;
	}

	@Override
	public void draw(Vec3f pos, InstanceData instanceData) {
		for (Vec2f texPos : texCoordList) {
			instanceData.put(new Vec3f(OpenGLUtils.pToGL(pos.x, 'w'), OpenGLUtils.pToGL(pos.y, 'h'), 0.f), texPos);
		}
	}
}
