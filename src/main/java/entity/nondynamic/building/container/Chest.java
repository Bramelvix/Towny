package entity.nondynamic.building.container;

import entity.nondynamic.building.BuildAbleObject;
import graphics.SpriteHashtable;

public class Chest extends Container {

	@Override
	public BuildAbleObject instance() {
		return new Chest();
	}

	public Chest() {
		super(10, "chest");
		sprite = SpriteHashtable.get(39);
	}
}
