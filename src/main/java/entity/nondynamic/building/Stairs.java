package entity.nondynamic.building;

import entity.dynamic.mob.Villager;
import graphics.SpriteHashtable;
import map.Level;

public class Stairs extends BuildAbleObject {

	private final boolean top;

	public Stairs(boolean top) {
		super(!top, "stairs");
		setVisible(true);
		this.top = top;
		sprite = SpriteHashtable.get(top ? 40 : 41);
	}

	@Override
	public void initialise(int x, int y, Level[] levels, int depth) {
		initialise(x, y, levels, depth, true);
	}

	private void initialise(int x, int y, Level[] levels, int depth, boolean firstTime) {
		super.initialise(x, y, levels, depth);
		if (firstTime) {
			if (top && depth != levels.length - 1) {
				levels[depth + 1].removeEntity(x, y);
				Stairs otherPart = new Stairs(false);
				otherPart.initialise(x, y, levels, depth + 1, false);
				levels[depth + 1].addStairs(otherPart);
			} else if (depth != 0) {
				levels[depth - 1].removeEntity(x, y);
				Stairs otherPart = new Stairs(true);
				otherPart.initialise(x, y, levels, depth - 1, false);
				levels[depth - 1].addStairs(otherPart);
			}
		}
		setOpened(true);
	}

	@Override
	protected void addEntity() {
		level.addStairs(this);
	}

	public void goOnStairs(Villager villager) {
		if (!top && z > 0) { //GO UP
			villager.setLocation(location.x, location.y, z - 1);
		} else if (top && z < villager.levels.length - 1) { //GO DOWN
			villager.setLocation(location.x, location.y, z + 1);
		}
	}

	@Override
	public BuildAbleObject instance() {
		return new Stairs(this.top);
	}

	public boolean isTop() {
		return top;
	}

}
