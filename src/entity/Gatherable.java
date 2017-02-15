package entity;

import map.Level;

//interface for workable entites like ore and trees
public interface Gatherable{
	public abstract boolean work(Level level);

}
