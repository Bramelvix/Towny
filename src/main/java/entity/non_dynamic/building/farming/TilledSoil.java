package entity.non_dynamic.building.farming;

import entity.non_dynamic.building.BuildAbleObject;
import graphics.SpriteHashtable;

import java.time.Instant;

public class TilledSoil extends BuildAbleObject {
	private Instant plantTime;
	private byte worked = 100;
	private long growTimeSeconds;

	public TilledSoil() {
		super("fertile soil");
		this.setOpened(true);
		sprite = SpriteHashtable.get(141);
	}

	public boolean sow() {
		if (worked > 0) {
			worked--;
			return false;
		}
		sprite = SpriteHashtable.get(142);
		plantTime = Instant.now();
		worked = 100;
		return true;

	}

	public boolean harvest() { //TODO finish implementing
		if (worked > 0) {
			worked--;
			return false;
		}
		sprite = SpriteHashtable.get(141);
		//drop plant product
		worked = 100;
		plantTime = null;
		return false;
	}

	public boolean isPlanted() {
		return plantTime != null;
	}

	public boolean isReadyToHarvest() {
		return plantTime != null && plantTime.isBefore(Instant.now().plusSeconds(growTimeSeconds));
	}

	@Override
	public BuildAbleObject instance() {
		return new TilledSoil();
	}

}
