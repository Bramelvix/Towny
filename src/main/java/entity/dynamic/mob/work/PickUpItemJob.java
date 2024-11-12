package entity.dynamic.mob.work;

import entity.dynamic.item.Item;
import entity.dynamic.mob.Villager;
import entity.non_dynamic.building.container.Container;

public class PickUpItemJob extends Job {
	private final Item item;
	private final Container container;

	public PickUpItemJob(Villager worker, Item item) {
		super(worker);
		this.item = item;
		container = worker.levels[zloc].getEntityOn(xloc, yloc, Container.class).orElse(null);

		if (worker.isHolding(item) || !item.isReserved(worker)) {
			completed = true;
			return;
		}

		item.setReserved(worker);
		xloc = item.getTileX();
		yloc = item.getTileY();
		zloc = item.getZ();
	}

	@Override
	protected void start() {
		started = true;
		worker.prependJobToChain(new MoveJob(worker, xloc, yloc, zloc, container == null));
	}

	@Override
	public void execute() {
		if (completed) {
			return;
		}

		if (!started) {
			start();
			return;
		}


		if (worker.isHolding(item)) {
			completed = true;
			return;
		}

		if (container != null) {
			if (worker.aroundTile(item.getTileX(), item.getTileY(), item.getZ())) {
				if (worker.pickUp(item, container)) {
					completed = true;
				}
				return;
			}
		} else if (worker.onSpot(item.getTileX(), item.getTileY(), item.getZ())) {
			if (worker.pickUp(item)) {
				completed = true;
			}
			return;
		}

		if (worker.isMovementNull()) {
			completed = true;
			item.removeReserved();
		}

		worker.move();
	}
}
