package entity.nonDynamic.building;

import entity.nonDynamic.StaticEntity;
import entity.nonDynamic.Workable;
import map.Level;
import map.Tile;
import sound.Sound;

public abstract class BuildAbleObject extends StaticEntity implements Workable {

	private boolean open; // can a villager walk over the object
	public boolean initialised = false; // has the building been initialised
	private byte condition = 0; // condition of the building (0=not built ,100= done)

	public BuildAbleObject() {
		setVisible(false);
	}

	protected void setOpened(boolean open) { //the open value has to be changeable for certain objects like doors
		this.open = open;
		if (initialised) {
			level.getTile(getTileX(), getTileY()).setSolid(!this.open);
		}
	}

	public void initialise(int x, int y, Level[] levels, int depth) {
		this.level = levels[depth];
		setLocation(x * Tile.SIZE, y * Tile.SIZE, depth);
		level.addEntity(this, !this.open);
		initialised = true;
	}

	// method called by villagers when building
	public boolean work() {
		if (!initialised) {
			return false;
		}
		if (condition < 100) {
			if (condition == 1) {
				Sound.playSound(Sound.drill);
			}
			condition++;
			return false;
		}
		this.setVisible(true);
		return true;
	}

	public abstract BuildAbleObject instance();

}
