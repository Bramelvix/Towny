package entity.mob;

import entity.item.Item;
import map.Level;

public abstract class Humanoid extends Mob {

	public Humanoid(Level level, int x, int y) {
		super(level);
		while (!level.isWalkAbleTile(x / 16, y / 16)) {
			x += 16;
			y += 16;
		}
		setLocation(x, y);
	}

	public Item holding; // item the mob is holding in his hands


	public <T extends Item> boolean holding(T item) {
		return holding != null ? holding.equals(item) : false;
	}

	@Override
	public void die() {
		if (holding != null) {
			level.addItem(holding);
		}

	}

	// DO NOT TOUCH THIS. SET THE MOVEMENT TO THE PATH OBJ USE move()!! DO NOT
	// USE!!!
	@Override
	protected final void moveTo(int x, int y) {
		super.moveTo(x, y);
		if (!(holding == null)) {
			holding.setLocation(x, y);
		}

	}


}
