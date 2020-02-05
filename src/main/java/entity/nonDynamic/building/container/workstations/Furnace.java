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

	public void render() {
		if (!isRunning()) {
			sprite1.draw(x,y);
		} else {
			draw(animationCounter > 29 ? sprite2 : sprite3, x, y);
			animationCounterTick();
		}
	}

	private void draw(Sprite sprite, int x, int y) {
		sprite.draw(x,y);
	}

	@Override
	public BuildAbleObject instance() {
		return new Furnace();
	}

}
