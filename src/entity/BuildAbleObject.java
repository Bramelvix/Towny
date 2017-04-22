package entity;

import entity.item.Item;
import map.Level;
import sound.Sound;

public abstract class BuildAbleObject extends Entity {
	public boolean initialised = false; // has the building been initialised
	protected byte condition = 0; // condition of the building (0=not built ,
									// 100=
	// done)

	public BuildAbleObject(int x, int y) {
		super(x, y);
		setVisible(false);
	}

	public boolean initialise(Item material, Level level) {
		this.level = level;
		if (material == null)
			return false;
		if (material.quantity > 0)
			material.quantity--;
		level.hardEntities.add(this);
		initialised = true;
		return true;

	}

	// build method called by villagers when building
	public boolean build() {
		if (initialised) {
			if (condition < 100) {
				if (condition == 1)
					Sound.speelGeluid(Sound.drill);
				condition++;
				return false;
			} else {
				this.setVisible(true);
				level.getTile(x >> 4, y >> 4).setSolid(true);
				return true;
			}

		}
		return false;
	}

}
