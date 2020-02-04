package entity.nonDynamic.building.container.workstations;

import entity.nonDynamic.building.BuildAbleObject;
import graphics.SpriteHashtable;

public class Anvil extends Workstation {
	public Anvil() {
		super();
		sprite = SpriteHashtable.get(85);
		setName("anvil");
	}

	@Override
	public BuildAbleObject instance() {
		return new Anvil();
	}

}
