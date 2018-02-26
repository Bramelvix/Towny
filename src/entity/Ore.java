package entity;

import entity.item.Item;
import entity.mob.Villager;
import entity.mob.work.Job;
import entity.mob.work.MoveItemJob;
import entity.pathfinding.Point;
import graphics.Sprite;
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
			setOre(type.toString().toLowerCase() + " ore", Sprite.ironOre,
					new Item(type.toString().toLowerCase() + " ore", this.x, this.y, Sprite.ironChunk, true));
			break;
		case GOLD:
			setOre(type.toString().toLowerCase() + " ore", Sprite.goldOre,
					new Item(type.toString().toLowerCase() + " ore", this.x, this.y, Sprite.goldChunk, true));
			break;
		case COAL:
			setOre(type.toString().toLowerCase() + " ore", Sprite.coalOre,
					new Item(type.toString().toLowerCase() + " ore", this.x, this.y, Sprite.coalChunk, true));
			break;
		case STONE:
			setOre("stone", Sprite.getStone(),
					new Item(type.toString().toLowerCase() + "s", this.x, this.y, Sprite.stoneChunk, true));
			break;
		case CRYSTAL:
			setOre(type.toString().toLowerCase() + " ore", Sprite.crystalOre,
					new Item(type.toString().toLowerCase(), this.x, this.y, Sprite.crystalChunk, true));
			break;
		case COPPER:
			setOre(type.toString().toLowerCase() + " ore", Sprite.copperOre,
					new Item(type.toString().toLowerCase() + " ore", this.x, this.y, Sprite.copperChunk, true));
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
			Point punt = worker.level.getNearestEmptySpot(this.x, this.y);
			worker.addJob(new MoveItemJob(punt.x * 16, punt.y * 16, worker));
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
