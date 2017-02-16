package entity.mob.work;

import entity.Resource;
import entity.Wall;
import entity.item.Item;
import entity.mob.Villager;
import map.Level;

public class Job implements Workable {
	protected boolean completed; // is the job done
	protected Item material; // what materials are needed for the job (like logs
								// for a wall)
	protected int xloc, yloc; // the x and y location of the job
	private boolean hasMaterial; // does the worker have the materials needed
	private boolean needsMaterial; // does the worker still need the materials
	protected Villager worker; // the villager doing the job
	private Resource jobObj; // the resource the worker needs to gather
	private boolean buildJob; // is the job a buildjob
	private Wall buildJobObj; // the buildable entity the worker needs to build
	private Level level; // the level in which the job is located
	private boolean started = false;

	// constructors
	private Job(int xloc, int yloc, Villager worker) {
		this(worker);
		completed = false;
		this.xloc = xloc;
		this.yloc = yloc;
		needsMaterial = false;
	}

	protected Job(Villager worker) {
		this.worker = worker;
	}

	public boolean isCompleted() {
		return completed;
	}

	public Job(int xloc, int yloc, Item mat, Villager worker, Level level) {
		this(xloc, yloc, worker);
		this.level = level;
		if (worker.getShortest(xloc >> 4, yloc >> 4) == null) {
			completed = true;
		} else {
			needsMaterial = true;
			int xtussen = xloc >> 4; // this step is needed to turn pixels into
										// tiles and then back into pixels, to
										// round down numbers undevidable by 16.
										// (example: pixel 260 x => divide by 16
										// rounded down to get 16 => multiply up
										// by 16 to get 256). Items are always
										// placed exactly on tile borders.
			int ytussen = yloc >> 4;
			buildJobObj = new Wall(xtussen << 4, ytussen << 4);
			material = mat;
			buildJob = true;
			if (material == null) {
				completed = true;
			}
		}

	}

	public Job(Resource e, Villager worker) {
		this(e.getX(), e.getY(), worker);
		jobObj = e;

	}

	// checks if the worker has or still needs the item
	private void checkItem() {
		if (worker.holding != null && needsMaterial) {
			if (worker.holding.equals(material)) {
				hasMaterial = true;
			}

		}
	}

	private void start() {
		worker.movement = worker.getShortest(xloc >> 4, yloc >> 4);
		if (worker.movement == null)
			completed = true;
		started = true;
	}

	// execute the job
	public void execute() {
		if (!completed && started) {
			checkItem();
			if (needsMaterial && !hasMaterial) {
				if (worker.pickUp(material)) {
					worker.movement = worker.getShortest(xloc >> 4, yloc >> 4);
					hasMaterial = true;
				}
				return;
			}
			if (!worker.aroundSpot(xloc, yloc) && worker.movement != null) {
				worker.move();
				return;
			} else {
				if (jobObj != null) {
					if (jobObj.work(worker.level))
						completed = true;
					return;
				} else {
					if (buildJob && buildJobObj != null) {
						if (!level.isClearTile(buildJobObj.getX() >> 4, buildJobObj.getY() >> 4)
								&& !buildJobObj.initialised) {
							// wait if the buildLocation is blocked by an
							// item or entity
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

		} else {
			start();
		}
	}

}
