package entity;

import entity.item.Item;
import entity.item.ItemHashtable;
import entity.mob.Villager;
import entity.mob.work.Job;
import entity.mob.work.MoveItemJob;
import graphics.Sprite;
import graphics.SpriteHashtable;
import map.Tile;
import sound.Sound;

public class Ore extends Resource {
	private byte mined = 100; // mined percentage (100 = unfinished / 0 =
								// finished)
	private Item minedItem; // Item dropped when the ore is mined
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
			setOre(type.toString().toLowerCase() + " ore", SpriteHashtable.get(54), ItemHashtable.get(5, this.x, this.y));
			break;
		case GOLD:
			setOre(type.toString().toLowerCase() + " ore", SpriteHashtable.get(56), ItemHashtable.get(7, this.x, this.y));
			break;
		case COAL:
			setOre(type.toString().toLowerCase() + " ore", SpriteHashtable.get(53), ItemHashtable.get(8, this.x, this.y));
			break;
		case STONE:
			setOre("stone", SpriteHashtable.getStone(), ItemHashtable.get(10, this.x, this.y));
			break;
		case CRYSTAL:
			setOre(type.toString().toLowerCase() + " ore", SpriteHashtable.get(57), ItemHashtable.get(9, this.x, this.y));
			break;
		case COPPER:
			setOre(type.toString().toLowerCase() + " ore", SpriteHashtable.get(55), ItemHashtable.get(6, this.x, this.y));
			break;
		}
	}

	private void setOre(String name, Sprite oreSprite, Item item) {
		setName(name);
		this.sprite = oreSprite;
		this.minedItem = item;
	}

	// work method executed by the villager when mining
	public boolean work(Villager worker) {
		if (!worker.level.isClearTile(this.x / 16, this.y / 16) && worker.level.getItemOn(this.x, this.y) != null
				&& worker.level.getNearestEmptySpot(this.x, this.y) != null) {
			worker.addJob(new MoveItemJob(worker.level.getItemOn(this.x, this.y), worker));
            Tile tile = worker.level.getNearestEmptySpot(this.x, this.y);
            worker.addJob(new MoveItemJob(tile.x * 16, tile.y * 16, worker));
			worker.addJob(new Job(this, worker));
			return true;
		}
		if (mined > 0) {
			if (mined % 20 == 0)
				Sound.speelGeluid(Sound.stoneMining);
			mined--;
			return false;
		} else {
			worker.level.removeEntity(this);
			worker.level.addItem(minedItem.copy());
			return true;
		}
	}
}
