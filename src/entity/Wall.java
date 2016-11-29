package entity;

import entity.item.Item;
import graphics.Sprite;
import map.Map;
import sound.Sound;

public class Wall extends Entity {
	public int condition = 0;
	private boolean visible;

	public Wall(int x, int y, boolean visible) {
		this.x = x << 4;
		this.y = y << 4;
		setVisible(visible);
		condition = 0;
	}

	public void initWall(Item material, Map level) {
		System.out.println(x + "////////////////////" + y);
		this.level = level;
		if (material==null) {
			return;
		}
		System.out.println(material.quantity);
		if (material.quantity > 0) {
			material.quantity--;
		}
		if (material.quantity == 0) {
			level.items.remove(material);
			material = null;
			return;
		}
		System.out.println(material==null);
		if (material.getName().equals("Logs")) {
			sprite = Sprite.woodenWallHorizontal;
		}
		level.entities.add(this);

	}

	public boolean build() {
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

}
