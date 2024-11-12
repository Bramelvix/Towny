package entity.dynamic.mob.work;

import entity.dynamic.mob.Villager;
import entity.non_dynamic.building.container.Container;

public class DropItemJob extends Job {
	private final Container container;

	public DropItemJob(Villager worker, int xloc, int yloc, int zloc) {
		super(worker);
		container = worker.levels[zloc].getEntityOn(xloc, yloc, Container.class).orElse(null);

		this.xloc = xloc; //locations are in tile numbers
		this.yloc = yloc;
		this.zloc = zloc;
	}

	@Override
	protected void start() {
		started = true;

		if (worker.getHolding() == null || (!worker.levels[zloc].isClearTile(xloc, yloc) && worker.levels[zloc].getEntityOn(xloc, yloc, Container.class).isEmpty())) {
			completed = true;
			return;
		}

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

		if (container != null) {
			if (worker.aroundTile(xloc, yloc, zloc)) {
				worker.drop(container);
				completed = true;
				return;
			}
		} else if (worker.onSpot(xloc, yloc, zloc)) {
			worker.drop();
			completed = true;
			return;
		}

		if (worker.isMovementNull()) {
			completed = true;
		}

		worker.move();
	}
}
