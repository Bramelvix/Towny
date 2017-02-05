package entity.mob;

import entity.Resource;
import entity.Wall;
import entity.item.Item;
import map.Map;

public class Job {
	public boolean completed;
	private Item material;
	private int xloc, yloc;
	private boolean hasMaterial;
	private boolean needsMaterial;
	private Villager worker;
	private Resource jobObj;
	private boolean buildJob;
	private Wall buildJobObj;
	private Map level;

	private Job(int xloc, int yloc, Villager worker) {
		completed = false;
		this.xloc = xloc;
		this.worker = worker;
		this.yloc = yloc;
		worker.movement = worker.getShortest(xloc >> 4, yloc >> 4);
		needsMaterial = false;
	}

	public Job(int xloc, int yloc, Item mat, Villager worker, Map level) {
		this(xloc, yloc, worker);
		this.level = level;
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
				worker.movement = null;
				completed = true;
			}
		}

	}

	public Job(Resource e, Villager worker) {
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
				worker.move();
				return;
			} else {
				if (worker.aroundSpot(worker.x, worker.y, xloc, yloc)) {
					if (jobObj != null) {
						if (jobObj.work(worker.level))
							completed = true;
						return;
					} else {
						if (buildJob && buildJobObj != null) {
							if (!level.isClearTile(buildJobObj.x >> 4, buildJobObj.y >> 4) && !buildJobObj.initialised) {
								// wachten als de plaats niet leeg is
								return;
							}
							if (!buildJobObj.initialised) {
								if (!buildJobObj.initialise(material, worker.level))
									completed = true;
							}
							if (buildJobObj.build()) {
								completed = true;
							}
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
