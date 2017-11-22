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
		case CRYSTAL:
			setName("crystal");
			sprite = Sprite.crystalOre;
			itemSprite = Sprite.crystalChunk;
			break;
		case COPPER:
			setName("copper ore");
			sprite = Sprite.copperOre;
			itemSprite = Sprite.copperChunk;
			break;
		default:
			sprite = null;
		}
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
			worker.level.walkableEntities.remove(this);
			if (type == OreType.STONE) {
				worker.level.addItem(new Item(type.name().toLowerCase() + "s", this.x, this.y, itemSprite, true));
			} else {
				worker.level.addItem(new Item(type.name().toLowerCase() + " ore", this.x, this.y, itemSprite, true));
			}
			return true;
		}
	}
}
