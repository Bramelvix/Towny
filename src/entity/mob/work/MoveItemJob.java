package entity.mob.work;

import entity.item.Item;
import entity.mob.Villager;

public class MoveItemJob extends Job {
	private boolean pickUpJob; // is the job a pickup or drop job

	public MoveItemJob(Item material, Villager worker) {
		this(worker);
		pickUpJob = true;
		pickUpJob = true;
		this.material = material;
		if (hasMaterial(material))
			completed = true;
		if (!material.isReservedVil(worker))
			completed = true;
		
		worker.movement = worker.getPath(material);
	}

	private MoveItemJob(Villager worker) {
		super(worker);
	}

	public MoveItemJob(int xloc, int yloc, Villager worker) {
		this(worker);
		if (worker.holding == null)
			completed = true;
		pickUpJob = false;
		this.xloc = xloc;
		this.yloc = yloc;
		worker.movement = worker.getPath(xloc >> 4, yloc >> 4);

	}

	public boolean hasMaterial(Item mat) {
		return worker.holding(mat);
	}

	public void execute() {
		if (!completed) {
			if (pickUpJob) {
				if (hasMaterial(material)) {
					completed = true;
					return;
				} else {
					if (worker.onSpot(material.getX(), material.getY())) {
						worker.pickUp(material);
					} else {
						if (worker.movement == null) {
							if (worker.getPath(material) != null) {
								worker.movement = worker.getPath(material);
								return;
							} else {
								completed = true;
								return;
							}
						} else {
							worker.move();
						}
					}
				}
			} else {
				if (worker.onSpot(xloc, yloc)) {
					worker.drop();
					completed = true;
					return;
				} else {
					if (worker.movement == null) {
						if (worker.getPath(xloc >> 4, yloc >> 4) != null) {
							worker.movement = worker.getPath(xloc >> 4, yloc >> 4);
							return;
						} else {
							worker.drop();
							completed = true;
							return;
						}
					} else {
						worker.move();
					}
				}
			}
		}
	}

	public boolean isCompleted() {
		return completed;
	}

}
