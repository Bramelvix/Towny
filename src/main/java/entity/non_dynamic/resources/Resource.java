package entity.non_dynamic.resources;

import entity.non_dynamic.StaticEntity;
import entity.non_dynamic.Workable;
import graphics.Sprite;
import graphics.opengl.OpenGLUtils;
import map.Level;
import util.vectors.Vec2f;

//superclass of gatherable resources such as trees and ore
public abstract class Resource extends StaticEntity implements Workable {

	Resource(float x, float y, int z, Level level) {
		super(x, y, z, level);
	}

	@Override
	public void render() {
		super.render();
		drawSelection();
	}

	protected void drawSelection() {
		if (this.isSelected()) {
			OpenGLUtils.addOutline(location.xy(), new Vec2f(Sprite.SIZE)); // render the red square around selected resources
		}
	}

}
