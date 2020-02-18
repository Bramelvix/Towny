package graphics;

import graphics.opengl.OpenGLUtils;
import util.vectors.Vec2f;
import util.vectors.Vec2i;

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
	public void draw(Vec2f pos, Vec2f offset) {
		for (Vec2f texPos : texCoordList) {
			OpenGLUtils.drawTexturedQuad(pos, new Vec2f(SIZE), offset, texPos, texSize, this.getSpriteSheetID());
		}
	}
}
