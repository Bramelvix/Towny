package entity;

import map.Map;

public class Resource extends Entity implements Workable{
	
	public Resource(int x, int y) {
		super(x,y);
	}

	public boolean work(Map level) {
		return false;
	}

}
