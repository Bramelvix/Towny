package entity.nonDynamic.resources;

import entity.dynamic.mob.Villager;
import entity.nonDynamic.StaticEntity;
import graphics.OpenGLUtils;
import graphics.Sprite;
import map.Level;

//superclass of gatherable resources such as trees and ore
public abstract class Resource extends StaticEntity {

	Resource(int x, int y, int z, Level level) {
		super(x, y, z, level);
	}

	public abstract boolean work(Villager worker);

	public void render(float xOffset, float yOffset) {
		super.render(xOffset, yOffset);
		drawSelection();
	}

	protected void drawSelection() {
		if (this.isSelected()) {
			OpenGLUtils.drawSelection(x,y, Sprite.SIZE, Sprite.SIZE); // render the red square around selected resources
		}
	}

}
