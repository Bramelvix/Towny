package entity;

import entity.item.Item;
import map.Level;
import sound.Sound;

public abstract class BuildAbleObject extends Entity {
	public boolean initialised = false; // has the building been initialised
	protected byte condition = 0; // condition of the building (0=not built ,
									// 100=
	// done)
	protected String resourceName;

	public BuildAbleObject() {
		setVisible(false);
	}

	public boolean initialise(int x, int y, Item material, Level level) {
		if (material == null)
			return false;
		if (material.getQuantity() > 0)
			material.lowerQuantity();;
		this.level = level;
		setLocation(x * 16, y * 16);
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

	public String getResourceName() {
		return resourceName;
	}

}
