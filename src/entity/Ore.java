package entity;

import entity.item.Item;
import graphics.Sprite;
import map.Map;
import sound.Sound;

public class Ore extends WorkableEntity {
	private float mined = 100;
	private Sprite itemSprite;
	private OreType type;

	public Ore(int x, int y, OreType type) {
		super(x, y);
		this.type = type;
		setVisible(true);
		getSprite();
	}

	private void getSprite() {
		switch (type) {
		case IRON:
			sprite = Sprite.ironOre;
			itemSprite = Sprite.ironChunk;
			break;
		case GOLD:
			sprite = Sprite.goldOre;
			itemSprite = Sprite.goldChunk;
			break;
		case COAL:
			sprite = Sprite.coalOre;
			break;
		default:
			sprite = null;
		}
	}

	public boolean work(Map level) {
		if (mined > 0){
			if (mined%20==0) Sound.speelGeluid(Sound.stoneMining);
			mined--;
			return false;
		} else {
			remove();
			level.entities.remove(this);
			level.getTile(x>>4, y>>4).setSolid(false);
			level.addItem(new Item(type.name().toLowerCase() + " ore", this.x, this.y, itemSprite, true, 2));
			return true;
		}
	}
}
