package entity.dynamic.mob.work;

import entity.dynamic.item.Item;
import entity.dynamic.mob.Villager;
import entity.non_dynamic.building.container.Workstation;

public class CraftJob extends Job {

	private final Item[] resources;
	private final Item product;
	private final Workstation station;
	private byte craftTimer = 100;
	private boolean itemsUpdated = false;
	private boolean prereqsDone = false;

	public <T extends Item> CraftJob(Villager worker, Item[] resources, T product, Workstation station) {
		super(worker);
		this.product = product;
		this.station = station;
		this.resources = resources;
		if (this.station == null) {
			completed = true;
		}
	}

	@Override
	public void execute() {
		if (!prereqsDone) {
			prerequisites();
			return;
		}

		if (!started || completed) {
			start();
			return;
		}

		if (!worker.aroundTile(station.getTileX(), station.getTileY(), station.getZ())) {
			if (worker.isMovementNull()) {
				completed = true;
			} else {
				worker.move();
			}
			return;
		}

		if (!itemsUpdated) {
			station.setRunning(true);
			for (Item i : resources) {
				station.takeItem(i);
			}
			itemsUpdated = true;
		}
		if (craft()) {
			worker.setHolding(product);
			product.setVisible(true);
			if (worker.levels[worker.getZ()].isClearTile(worker.getTileX(), worker.getTileY())) {
				worker.drop();
			}
			completed = true;
		}

	}

	private boolean craft() {
		craftTimer--;
		if (craftTimer <= 0) {
			station.setRunning(false);
			return true;
		}
		return false;
	}

	private void prerequisites() {
		for (Item item : this.resources) {
			if (item == null) {
				completed = true;
				return;
			}
			worker.prependJobToChain(new PickUpItemJob(worker, item));
			worker.prependJobToChain(new DropItemJob(worker, station.getTileX(), station.getTileY(), worker.getZ()));
		}
		prereqsDone = true;
	}

	@Override
	protected void start() {
		worker.setPath(worker.getPathAround(station.getTileX(), station.getTileY()).orElse(null));
		started = true;
	}

}
