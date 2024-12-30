package entity.nondynamic.resources;

import entity.dynamic.item.Item;
import entity.nondynamic.StaticEntity;
import entity.nondynamic.Workable;
import graphics.Sprite;
import graphics.opengl.OpenGLUtils;
import map.Level;
import util.vectors.Vec2f;

//superclass of gatherable resources such as trees and ore
public abstract class Resource extends StaticEntity implements Workable {
	private final Item droppedResource;
	private byte gatheredPercentage = 0;

	Resource(float x, float y, int z, Level level, boolean transparent, String displayName, Item droppedResource) {
		super(x, y, z, level, transparent, displayName);
		this.droppedResource = droppedResource;
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

	@Override
	public boolean work() {
		if (gatheredPercentage < 100) {
			gatheredPercentage++;
			return false;
		}

		level.removeEntity(this);
		droppedResource.setLocation(location.x, location.y, this.z);
		level.addItem(droppedResource);
		return true;
	}
}
