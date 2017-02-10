package entity;

import entity.item.Item;
import map.Level;

public interface Buildable { //interface for buildable objects (walls, doors, etc)
	public abstract boolean build(); // build method
	public abstract boolean initialise(Item material, Level level); // initialising a building. Villagers do this at the start before building an object

}
