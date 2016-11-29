package entity;

import entity.item.Item;
import map.Map;

public abstract class BuildableEntity extends Entity {
	public boolean initialised;
	public BuildableEntity(int x, int y) {
		super(x, y);
	}
	public abstract boolean build();
	public abstract void initialise(Item material, Map level);

}
