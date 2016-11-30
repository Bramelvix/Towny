package entity;

import entity.item.Item;
import graphics.Sprite;
import map.Map;
import sound.Sound;

public class Wall extends BuildableEntity {
	public int condition = 0;

	public Wall(int x, int y) {
		super(x, y);
		setVisible(false);
		condition = 0;
	}

	public boolean initialise(Item material, Map level) {
		this.level = level;
		if (material == null) {
			return false;
		}
		if (material.quantity > 0) {
			material.quantity--;
		}
		if (material.getName().equals("Logs")) {
			sprite = Sprite.woodenWallHorizontal;
		}
		level.entities.add(this);
		initialised = true;
		return true;

	}

	public boolean build() {
		if (initialised) {
			if (condition < 100) {
				if (condition == 1)
					Sound.speelGeluid(Sound.drill);
				condition++;
				return false;
			} else {
				this.setVisible(true);
				level.getTile(x >> 4, y >> 4).setSolid(true);
				return true;
			}

		}
		return false;
	}

}
