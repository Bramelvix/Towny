package entity;

import map.Level;

//superclass of gatherable resources such as trees and ore
public abstract class Resource extends Entity implements Gatherable {

	public Resource(int x, int y) {
		super(x, y);
	}

	public abstract boolean work(Level level);

}
