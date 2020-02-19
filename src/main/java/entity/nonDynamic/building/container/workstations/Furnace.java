package entity.nonDynamic.building.container.workstations;

import entity.nonDynamic.building.BuildAbleObject;
import graphics.Sprite;
import graphics.SpriteHashtable;
import util.vectors.Vec2f;

public class Furnace extends Workstation {

	private final Sprite sprite1;
	private final Sprite sprite2;
	private final Sprite sprite3;

	public Furnace() {
		super();
		sprite1 = SpriteHashtable.get(82);
		sprite2 = SpriteHashtable.get(83);
		sprite3 = SpriteHashtable.get(84);
		setName("furnace");
	}

	public void render(Vec2f offset) {
		if (!isRunning()) {
			sprite1.draw(location, offset);
		} else {
			draw(animationCounter > 29 ? sprite2 : sprite3, location, offset);
			animationCounterTick();
		}
	}

	private void draw(Sprite sprite, Vec2f pos, Vec2f offset) {
		sprite.draw(pos, offset);
	}

	@Override
	public BuildAbleObject instance() {
		return new Furnace();
	}

}
