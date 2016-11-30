package entity.mob;

import java.util.logging.Level;

import entity.BuildableEntity;
import entity.Wall;
import entity.WorkableEntity;
import entity.item.Item;

public class Job {
	public boolean completed;
	private Item material;
	private int xloc, yloc;
	private boolean hasMaterial;
	private boolean needsMaterial;
	private Villager worker;
	private WorkableEntity jobObj;
	private boolean buildJob;
	private BuildableEntity buildJobObj;

	private Job(int xloc, int yloc, Villager worker) {
		completed = false;
		this.xloc = xloc;
		this.worker = worker;
		this.yloc = yloc;
		worker.movement = worker.getShortest(xloc >> 4, yloc >> 4);
		needsMaterial = false;
	}

	public Job(int xloc, int yloc, Item mat, Villager worker) {
		this(xloc, yloc, worker);
		if (worker.getShortest(xloc >> 4, yloc >> 4) == null) {
			completed = true;
		} else {
			needsMaterial = true;
			int xtussen = xloc >> 4;
			int ytussen = yloc >> 4;
			buildJobObj = new Wall(xtussen << 4, ytussen << 4);
			material = mat;
			buildJob = true;
			if (material == null) {
				completed = true;
			}
		}

	}

	public Job(WorkableEntity e, Villager worker) {
		this(e.x, e.y, worker);
		jobObj = e;

	}

	private void checkItem() {
		if (worker.holding != null && needsMaterial) {
			if (worker.holding.getName().equals("Logs")) {
				hasMaterial = true;
			}

		}
	}

	public void execute() {
		if (!completed) {
			checkItem();
			if (needsMaterial && !hasMaterial) {
				if (worker.pickUp(material)) {
					worker.movement = worker.getShortest(xloc >> 4, yloc >> 4);
					hasMaterial = true;
				}
				return;
			}
			if (!worker.aroundSpot(worker.x, worker.y, xloc, yloc) && worker.movement != null) {
				if (needsMaterial && hasMaterial) {
					worker.move();
					return;
				} else {
					worker.move();
					return;
				}
			} else {
				if (worker.aroundSpot(worker.x, worker.y, xloc, yloc)) {
					if (jobObj != null) {
						if (jobObj.work(worker.level))
							completed = true;
						return;
					} else {
						if (buildJob && buildJobObj != null) {
							if (!buildJobObj.initialised) {
								if (!buildJobObj.initialise(material, worker.level))
									completed = true;
							}
							if (buildJobObj.build())
								completed = true;

							if (material.quantity <= 0) {
								worker.holding = null;
							} else {
								worker.drop();
							}
							return;

						}

					}
				}
			}

		}
	}

}
