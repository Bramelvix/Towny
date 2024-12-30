package entity.nondynamic;

import entity.Entity;
import map.Level;

public abstract class StaticEntity extends Entity {

	public Level level;

	protected StaticEntity(float x, float y, int z, Level level, boolean transparent, String displayName) {
		super(x, y, z, transparent, displayName);
		this.level = level;
	}

	protected StaticEntity(String name) {
		super(name);
	}

	protected StaticEntity(boolean transparent, String displayName) {
		super(transparent, displayName);
	}

}
