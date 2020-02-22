package entity.nonDynamic.building.container.workstations;

import entity.nonDynamic.building.BuildAbleObject;
import graphics.Sprite;
import graphics.SpriteHashtable;
import graphics.opengl.InstanceData;
import util.vectors.Vec2f;
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
	public void render(InstanceData instanceData) {
		if (!isRunning()) {
			sprite1.draw(location, instanceData);
		} else {
			draw(animationCounter > 29 ? sprite2 : sprite3, location, instanceData);
			animationCounterTick();
		}
	}

	private void draw(Sprite sprite, Vec3f pos, InstanceData instanceData) {
		sprite.draw(pos, instanceData);
	}

	@Override
	public BuildAbleObject instance() {
		return new Furnace();
	}

}
