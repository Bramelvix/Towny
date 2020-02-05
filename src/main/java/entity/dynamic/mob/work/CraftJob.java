package entity.dynamic.mob.work;

import entity.dynamic.item.Item;
import entity.dynamic.mob.Villager;
import entity.nonDynamic.building.container.workstations.Workstation;
import map.Tile;

public class CraftJob extends Job {

	private final Item[] resources;
	private final Item product;
	private final Workstation station;
	private byte craftTimer = 100;
	private boolean itemsUpdated = false;

	public <T extends Item> CraftJob(Villager worker, T[] resources, T product, Workstation station) {
		super(worker);
		this.product = product;
		this.station = station;
		this.resources = resources;
		if (this.station == null) {
			completed = true;
			return;
		}
		for (Item item : this.resources) {
			if (item == null) {
				completed = true;
				return;
			}
			worker.addJob(new MoveItemJob(item, worker));
			worker.addJob(new MoveItemJob(station.getX(), station.getY(),worker.getZ(), worker));
		}
	}

	@Override
	public void execute() {
		if (started && !completed) {
			if (!worker.aroundTile(station.getX(), station.getY(), station.getZ())) {
				if (worker.isMovementNull()) {
					completed = true;
				} else {
					worker.move();
				}
			} else {
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
					if (worker.levels[worker.getZ()].isClearTile(worker.getX() / Tile.SIZE, worker.getY() / Tile.SIZE)) {
						worker.drop();
					}
					completed = true;
				}
			}
		} else {
			start();
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

	@Override
	protected void start() {
		worker.setPath(worker.getPathAround(station.getX() , station.getY() ));
		started = true;
	}

}
