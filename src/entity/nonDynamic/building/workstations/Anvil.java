package entity.nonDynamic.building.workstations;

import entity.nonDynamic.building.BuildAbleObject;
import graphics.SpriteHashtable;

public class Anvil extends Workstation {
	public Anvil() {
		super();
        sprites.add(SpriteHashtable.get(85));
		setName("anvil");
	}

	@Override
	public BuildAbleObject clone() {
		return new Anvil();
	}

}
