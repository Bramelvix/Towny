package entity;

import entity.item.Item;
import graphics.Sprite;
import map.Map;
import sound.Sound;

public class Wall extends Entity {
	public int condition = 0;
	private boolean visible;

	public Wall(int x, int y, boolean visible) {
		this.x = x;
		this.y = y;
		setVisible(visible);
		condition = 0;
	}

	public void initWall(Item material, Map level) {
		System.out.println(x + "////////////////////" + y);
		this.level = level;
		if (material.quantity == 0) {
			level.items.remove(material);
		} else {
			material.quantity--;
		}
		if (material.getName().equals("Logs")) {
			sprite1 = Sprite.woodenWallHorizontal;
		}
		level.entities.add(this);

	}

	public boolean build() {
		if (condition < 100) {
			if (condition==1)
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
