package entity.building.workstations;

import entity.building.BuildAbleObject;
import graphics.SpriteHashtable;

public class Anvil extends Workstation {
	public Anvil() {
		super();
        sprite = SpriteHashtable.get(85);
		setName("anvil");
	}

	@Override
	public BuildAbleObject clone() {
		return new Anvil();
	}

}
