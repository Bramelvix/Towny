package entity.nonDynamic.resources;

import entity.dynamic.mob.Villager;
import entity.nonDynamic.StaticEntity;
import graphics.opengl.OpenGLUtils;
import graphics.Sprite;
import map.Level;
import util.vectors.Vec4f;

//superclass of gatherable resources such as trees and ore
public abstract class Resource extends StaticEntity {

	Resource(int x, int y, int z, Level level) {
		super(x, y, z, level);
	}

	public abstract boolean work(Villager worker);

	public void render(float xOffset, float yOffset) {
		super.render(xOffset, yOffset);
		drawSelection(xOffset, yOffset);
	}

	protected void drawSelection(float xOffset, float yOffset) {
		if (this.isSelected()) {
			OpenGLUtils.drawOutline(x,y, Sprite.SIZE, Sprite.SIZE, xOffset, yOffset, new Vec4f(1,0,0,1)); // render the red square around selected resources
		}
	}

}
