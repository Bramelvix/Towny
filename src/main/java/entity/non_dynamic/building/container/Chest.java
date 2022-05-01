package entity.non_dynamic.building.container;

import entity.non_dynamic.building.BuildAbleObject;
import graphics.SpriteHashtable;

public class Chest extends Container {

	@Override
	public BuildAbleObject instance() {
		return new Chest();
	}

	public Chest() {
		super(10);
		sprite = SpriteHashtable.get(39);
		setName("chest");
	}

}
