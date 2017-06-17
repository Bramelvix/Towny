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


	@Override
	protected void start() {
		worker.setMovement(worker.getPath(xloc >> 4, yloc >> 4));
		if (worker.isMovementNull()) {
			completed = true;
		}
		started = true;
	}

	private void fight() {
		if (timer == 0) {
			target.hit(worker.getDamage());
			timer = 70 / worker.getSpeed();
		}
		timer--;
	}

	@Override
	public void execute() {
		if (target == null) {
			completed = true;
			return;
		} else {
			xloc = target.getX();
			yloc = target.getY();
		}
		if (!completed && started) {
			if (worker.aroundTile(xloc, yloc)) {
				fight();
				if (target.getHealth() == 0) {
					completed = true;
				}
				return;
			} else {
				if (worker.isMovementNull()) {
					if (worker.getPath(xloc >> 4, yloc >> 4) != null) {
						worker.setMovement(worker.getPath(xloc >> 4, yloc >> 4));
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
