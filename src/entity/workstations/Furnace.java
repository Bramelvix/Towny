package entity.workstations;

import entity.Buildable;
import entity.Entity;
import entity.item.Item;
import graphics.Sprite;
import map.Level;

public class Furnace extends Entity implements Buildable{
	private static final Sprite furnaceOff = Sprite.furnaceOff;
	private static final Sprite furnaceOn1 = Sprite.furnaceOn1;
	private static final Sprite furnaceOn2 = Sprite.furnaceOn2;
	private boolean running = false;
	private byte animationcounter = 60;
	
	
	@Override
	public boolean build() {
		return false;
	}
	@Override
	public boolean initialise(Item material, Level level) {
		return false;
	}
	
	

}
