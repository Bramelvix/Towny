package entity.non_dynamic.building;

import entity.non_dynamic.StaticEntity;
import entity.non_dynamic.Workable;
import map.Level;
import map.Tile;
import sound.Sound;

public abstract class BuildAbleObject extends StaticEntity implements Workable {

	private boolean open; // can a villager walk over the object
	private boolean initialised = false; // has the building been initialised
	private byte condition = 0; // condition of the building (0=not built ,100= done)

	protected BuildAbleObject() {
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
		addEntity();
		initialised = true;
	}

	protected void addEntity() {
		level.addEntity(this, !this.open);
	}

	// method called by villagers when building
	@Override
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

	public boolean isInitialised() {
		return initialised;
	}
}
