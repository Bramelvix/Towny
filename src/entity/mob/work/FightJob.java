package entity.mob.work;

import entity.mob.Mob;
import entity.mob.Villager;

public class FightJob extends Job {
	private Mob target;
	private int timer = 0;

	public FightJob(Villager worker, Mob target) {
		super(worker);
		this.target = target;
	}

	private void start() {
		worker.movement = worker.getPath(xloc >> 4, yloc >> 4);
		if (worker.movement == null) {
			completed = true;
			material.setReservedVil(null);
		}
		started = true;
	}

	private void fight() {
		if (timer == 0) {
			target.hit(worker.getDamage());
			timer = 100 / worker.getSpeed();
		}
		timer--;
	}

	public void execute() {
		xloc = target.getX();
		yloc = target.getY();
		if (!completed && started) {
			if (worker.aroundSpot(xloc, yloc)) {
				fight();
				if (target.getHealth() == 0) {
					completed = true;
				}
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

		} else {
			start();
		}
	}

}
