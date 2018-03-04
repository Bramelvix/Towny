package entity.workstations;

import entity.BuildAbleObject;
import graphics.Sprite;
import graphics.SpriteHashtable;

public class Anvil extends Workstation {
	public Anvil() {
		super();
        sprite = SpriteHashtable.get(85);
		setName("anvil");
	}

	@Override
	public BuildAbleObject clone() {
		// TODO Auto-generated method stub
		return new Anvil();
	}

}
