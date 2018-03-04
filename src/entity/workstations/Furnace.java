package entity.workstations;

import entity.BuildAbleObject;
import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteHashtable;

public class Furnace extends Workstation {
	public Furnace() {
		super();
		setName("furnace");
	}

	public void render(Screen s) {
		if (!isRunning()) {
            s.renderSprite(x, y, SpriteHashtable.get(82));
		} else {
			if (animationcounter > 29) {
                s.renderSprite(x, y, SpriteHashtable.get(83));
				anmiationCounterTick();
			} else {
                s.renderSprite(x, y, SpriteHashtable.get(84));
				anmiationCounterTick();
			}
		}
	}

	@Override
	public BuildAbleObject clone() {
		// TODO Auto-generated method stub
		return new Furnace();
	}

}
