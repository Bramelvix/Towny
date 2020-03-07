package entity.nonDynamic.building.container.workstations;

import entity.nonDynamic.building.BuildAbleObject;
import graphics.Sprite;
import graphics.SpriteHashtable;
import util.vectors.Vec3f;

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

	@Override
	public void render() {
		if (!isRunning()) {
			sprite1.draw(location);
		} else {
			draw(animationCounter > 29 ? sprite2 : sprite3, location);
			animationCounterTick();
		}
	}

	private void draw(Sprite sprite, Vec3f pos) {
		sprite.draw(pos);
	}

	@Override
	public BuildAbleObject instance() {
		return new Furnace();
	}

}
