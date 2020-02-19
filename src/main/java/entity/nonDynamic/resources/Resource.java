package entity.nonDynamic.resources;

import entity.dynamic.mob.Villager;
import entity.nonDynamic.StaticEntity;
import graphics.opengl.OpenGLUtils;
import graphics.Sprite;
import map.Level;
import util.vectors.Vec2f;
import util.vectors.Vec4f;

//superclass of gatherable resources such as trees and ore
public abstract class Resource extends StaticEntity {

	Resource(float x, float y, int z, Level level) {
		super(x, y, z, level);
	}

	public abstract boolean work(Villager worker);

	public void render(Vec2f offset) {
		super.render(offset);
		drawSelection(offset);
	}

	protected void drawSelection(Vec2f offset) {
		if (this.isSelected()) {
			OpenGLUtils.drawOutline(location, new Vec2f(Sprite.SIZE), offset, new Vec4f(1,0,0,1)); // render the red square around selected resources
		}
	}

}
