package entity;

import entity.item.Item;
import graphics.Sprite;
import map.Level;
import sound.Sound;

public class Ore extends Resource {
	private byte mined = 100; // mined percentage (100 = unfinished / 0 =
								// finished)
	private Sprite itemSprite; // sprite for the item the ore drops when mined
	private OreType type; // type of ore (iron, coal, gold,...)

	// basic constructor
	public Ore(int x, int y, OreType type) {
		super(x, y);
		this.type = type;
		decideSprite();
		setVisible(true);
	}

	// decide the sprite for the ore
	private void decideSprite() {
		switch (type) {
		case IRON:
			setName("iron ore");
			sprite = Sprite.ironOre;
			itemSprite = Sprite.ironChunk;
			break;
		case GOLD:
			setName("gold ore");
			sprite = Sprite.goldOre;
			itemSprite = Sprite.goldChunk;
			break;
		case COAL:
			setName("coal ore");
			sprite = Sprite.coalOre;
			itemSprite = Sprite.coalChunk;
			break;
		case STONE:
			setName("stone");
			sprite = Sprite.getStone();
			itemSprite = Sprite.stoneChunk;
			break;
		default:
			sprite = null;
		}
	}

	// work method executed by the villager when mining
	public boolean work(Level level) {
		if (mined > 0) {
			if (mined % 20 == 0)
				Sound.speelGeluid(Sound.stoneMining);
			mined--;
			return false;
		} else {
			level.walkableEntities.remove(this);
			level.getTile(x >> 4, y >> 4).setSolid(false);
			if (type == OreType.STONE) {
				level.addItem(new Item(type.name().toLowerCase() + "s", this.x, this.y, itemSprite, true, 3));
			} else {
				level.addItem(new Item(type.name().toLowerCase() + " ore", this.x, this.y, itemSprite, true, 3));
			}
			return true;
		}
	}
}
