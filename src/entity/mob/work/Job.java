package entity.mob.work;

import entity.BuildAbleObject;
import entity.Resource;
import entity.item.Item;
import entity.mob.Villager;

public class Job implements Workable {
	protected boolean completed; // is the job done
	protected Item material; // what materials are needed for the job (like logs
								// for a wall)
	protected int xloc, yloc; // the x and y location of the job
	private boolean needsMaterial; // does the worker still need the materials
	protected Villager worker; // the villager doing the job
	private Resource jobObj; // the resource the worker needs to gather
	private boolean buildJob; // is the job a buildjob
	private BuildAbleObject buildJobObj; // the buildable entity the worker
											// needs to build
	protected boolean started = false;
	private boolean goingToPickUpItem = false;

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

	public Job(int xloc, int yloc, Item mat, BuildAbleObject object, Villager worker) {
		this(xloc, yloc, worker);
		needsMaterial = true;
		buildJobObj = object;
		material = mat;
		buildJob = true;
		if (material == null) {
			completed = true;
		}

	}

	public Job(Resource e, Villager worker) {
		this(e.getX(), e.getY(), worker);
		jobObj = e;

	}

	// checks if the worker still needs the item
	private void checkItem() {
		if (needsMaterial && worker.holding != null) {
			if (worker.holding.equals(material)) {
				needsMaterial = false;
			} else {
				worker.drop();
			}
		}
	}

	protected void start() {
		worker.setMovement(worker.getShortest(xloc / 16, yloc / 16));
		completed = worker.isMovementNull();
		started = true;
	}

	private void goPickupItem() {
		if (!goingToPickUpItem) {
			worker.addJob(new MoveItemJob(material, worker), 0);
			goingToPickUpItem = true;
		}
	}

	// execute the job
	public void execute() {
		checkItem();
		if (needsMaterial) {
			goPickupItem();
			return;
		} else {
			if (!completed && started) {
				if (!worker.aroundTile(xloc, yloc)) {
					if (worker.isMovementNull()) {
						completed = true;
					} else {
						worker.move();
					}
					return;
				} else {
					if (jobObj != null) {
						if (jobObj.work(worker)) {
							completed = true;
						}
						return;
					} else {
						if (buildJob && buildJobObj != null) {
							if (!worker.level.isWalkAbleTile(xloc / 16, yloc / 16) && !buildJobObj.initialised) {
								// wait if the buildLocation is blocked by an
								// item or entity
								return;
							}
							if (!buildJobObj.initialised) {
								completed = !buildJobObj.initialise(xloc / 16, yloc / 16, material, worker.level);
							}
							completed = buildJobObj.build();
							worker.holding = null;
							return;

						}

					}
				}

			} else {
				start();
			}
		}
	}

}
