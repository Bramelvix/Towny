package entity;

import entity.item.Item;
import map.Map;

public interface Buildable {
	public abstract boolean build();
	public abstract boolean initialise(Item material, Map level);

}
