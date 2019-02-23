package entity.nonDynamic.building.container.workstations;

import entity.nonDynamic.building.BuildAbleObject;
import graphics.Sprite;
import graphics.SpriteHashtable;

public class Furnace extends Workstation {
    private Sprite sprite1, sprite2, sprite3;
	public Furnace() {
		super();
		sprite1 = SpriteHashtable.get(82);
        sprite2 =SpriteHashtable.get(83);
        sprite3 = SpriteHashtable.get(84);
		setName("furnace");
	}

	public void render() {
		if (!isRunning()) {
			sprite1.draw(x,y);
		} else {
			if (animationcounter > 29) {
				sprite2.draw(x,y);
				animationCounterTick();
			} else {
				sprite3.draw(x,y);
				animationCounterTick();
			}
		}
	}

	@Override
	public BuildAbleObject clone() {
		return new Furnace();
	}

}
