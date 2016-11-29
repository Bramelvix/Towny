package entity.mob;

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
		this(xloc,yloc,worker);
		needsMaterial = true;
		buildJobObj = new Wall(xloc, yloc);
		material = mat;
		buildJob = true;
		if (material == null) {
			completed = true;
		}

	}

	public Job(WorkableEntity e, Villager worker) {
		this(e.x, e.y, worker);
		jobObj = e;

	}

	public void execute() {
		if (!completed) {
			if (needsMaterial && !hasMaterial) {
				if (worker.pickUp(material)) {
					worker.movement = worker.getShortest(xloc >> 4, yloc >> 4);
					hasMaterial = true;
				}
				return;
			}
			if (!worker.aroundSpot(worker.x, worker.y,xloc,yloc) && worker.movement != null) {
				worker.move();
				return;
			} else {
				if (worker.aroundSpot(worker.x, worker.y,xloc,yloc)) {
					if (jobObj != null) {
						if (jobObj.work(worker.level))
							completed = true;
						return;
					} else {
						if (buildJob && buildJobObj != null) {
							if (!buildJobObj.initialised) {
								buildJobObj.initialise(material, worker.level);
							}
							if (buildJobObj.build())
								completed = true;
							return;

						}

					}
				}
			}

		}
	}

}
