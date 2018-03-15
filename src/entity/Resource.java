package entity;

import entity.mob.Villager;

//superclass of gatherable resources such as trees and ore
public abstract class Resource extends Entity {

    Resource(int x, int y) {
		super(x, y);
	}

	public abstract boolean work(Villager worker);

}
