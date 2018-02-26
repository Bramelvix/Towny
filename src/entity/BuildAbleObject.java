package entity;

import entity.item.Item;
import map.Level;
import sound.Sound;

public abstract class BuildAbleObject extends Entity {
	private boolean open; // can a villager walk over the object
	public boolean initialised = false; // has the building been initialised
	protected byte condition = 0; // condition of the building (0=not built ,
									// 100=
	// done)

	public BuildAbleObject() {
		setVisible(false);
	}

	public void setOpened(boolean open) { //the open value has to be changeable for certain objects like doors
		this.open = open;
		if (open) {
			level.removeEntity(this);
			level.addEntity(this, false);
		} else {
			level.removeEntity(this);
			level.addEntity(this, true);
		}
	}

	public boolean isOpen() {
		return open;
	}

	public boolean initialise(int x, int y, Item material, Level level) {
		if (material == null) {
			return false;
		}
		this.level = level;
		setLocation(x * 16, y * 16);
		if (isOpen()) {
			level.addEntity(this, false);
		} else {
			level.addEntity(this, true);
		}

		initialised = true;
		return true;

	}

	// build method called by villagers when building
	public boolean build() {
		if (initialised) {
			if (condition < 100) {
				if (condition == 1) {
					Sound.speelGeluid(Sound.drill);
				}
				condition++;
				return false;
			} else {
				this.setVisible(true);
				level.getTile(x * 16, y * 16).setSolid(true);
				return true;
			}

		}
		return false;
	}
	
	
	public abstract BuildAbleObject clone();

}
