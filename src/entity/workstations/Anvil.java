package entity.workstations;

import entity.BuildAbleObject;
import graphics.Sprite;

public class Anvil extends Workstation {
	public Anvil() {
		super();
		sprite = Sprite.anvil;
		setName("anvil");
	}

	@Override
	public BuildAbleObject clone() {
		// TODO Auto-generated method stub
		return new Anvil();
	}

}
