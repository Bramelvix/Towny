package entity;

import map.Map;

public abstract class WorkableEntity extends Entity{
	
	
	public abstract boolean work(Map level);
	
	public WorkableEntity(int x, int y) {
		super(x,y);
	}

}
