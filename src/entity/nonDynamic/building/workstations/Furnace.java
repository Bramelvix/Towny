package entity.nonDynamic.building.workstations;

import entity.nonDynamic.building.BuildAbleObject;
import graphics.Screen;
import graphics.SpriteHashtable;

public class Furnace extends Workstation {
	public Furnace() {
		super();
        sprites.add(SpriteHashtable.get(82));
        sprites.add(SpriteHashtable.get(83));
        sprites.add(SpriteHashtable.get(84));
		setName("furnace");
	}

	public void render(Screen s) {
		if (!isRunning()) {
            s.renderSprite(x, y, sprites.get(0));
		} else {
			if (animationcounter > 29) {
                s.renderSprite(x, y, sprites.get(1));
				anmiationCounterTick();
			} else {
                s.renderSprite(x, y, sprites.get(2));
				anmiationCounterTick();
			}
		}
	}

	@Override
	public BuildAbleObject clone() {
		return new Furnace();
	}

}
