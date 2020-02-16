package entity.nonDynamic.building.container.workstations;

import entity.nonDynamic.building.BuildAbleObject;
import graphics.Sprite;
import graphics.SpriteHashtable;

public class Furnace extends Workstation {

	private Sprite sprite1, sprite2, sprite3;

	public Furnace() {
		super();
		sprite1 = SpriteHashtable.get(82);
		sprite2 = SpriteHashtable.get(83);
		sprite3 = SpriteHashtable.get(84);
		setName("furnace");
	}

	public void render(float xOffset,float yOffset) {
		if (!isRunning()) {
			sprite1.draw(x,y, xOffset, yOffset);
		} else {
			draw(animationCounter > 29 ? sprite2 : sprite3, x, y, xOffset, yOffset);
			animationCounterTick();
		}
	}

	private void draw(Sprite sprite, int x, int y, float xOffset, float yOffset) {
		sprite.draw(x,y, xOffset, yOffset);
	}

	@Override
	public BuildAbleObject instance() {
		return new Furnace();
	}

}
