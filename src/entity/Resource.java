package entity;

import map.Level;

//superclass of gatherable resources such as trees and ore
public class Resource extends Entity implements Gatherable {

	public Resource(int x, int y) {
		super(x, y);
	}

	public boolean work(Level level) {
		return false;
	}

}
