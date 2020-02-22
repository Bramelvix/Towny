package entity.nonDynamic.resources;

import entity.dynamic.mob.Villager;
import entity.nonDynamic.StaticEntity;
import graphics.opengl.InstanceData;
import graphics.opengl.OpenGLUtils;
import graphics.Sprite;
import map.Level;
import util.vectors.Vec2f;

//superclass of gatherable resources such as trees and ore
public abstract class Resource extends StaticEntity {

	Resource(float x, float y, int z, Level level) {
		super(x, y, z, level);
	}

	public abstract boolean work(Villager worker);

	@Override
	public void render(InstanceData instanceData) {
		super.render(instanceData);
		drawSelection();
	}

	protected void drawSelection() {
		if (this.isSelected()) {
			OpenGLUtils.addOutline(location.xy(), new Vec2f(Sprite.SIZE)); // render the red square around selected resources
		}
	}

}
