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

	public void initialise(Item material, Map level) {
		this.level = level;
		if (material == null) {
			return;
		}
		if (material.quantity > 0) {
			material.quantity--;
		}
		if (material.quantity == 0) {
			level.items.remove(material);
			material = null;
			return;
		}
		if (material.getName().equals("Logs")) {
			sprite = Sprite.woodenWallHorizontal;
		}
		level.entities.add(this);
		initialised = true;

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
				System.out.println(level.getTile(x, y));
				level.getTile(x, y).setSolid(true);
				return true;
			}

		}
		return false;
	}

}
