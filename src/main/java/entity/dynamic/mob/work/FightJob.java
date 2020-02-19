package entity.dynamic.mob.work;

import entity.dynamic.mob.Mob;
import entity.dynamic.mob.Villager;
import map.Tile;

public class FightJob extends Job {

	private final Mob target;
	private int timer = 0;

	public FightJob(Villager worker, Mob target) {
		super(worker);
		this.target = target;
	}

	@Override
	protected void start() {
		worker.setPath(worker.getPath((int) xloc/ Tile.SIZE, (int) yloc/ Tile.SIZE));
		if (worker.isMovementNull()) {
			completed = true;
		}
		started = true;
	}

	private void fight() {
		if (timer <= 0) {
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
			if (worker.aroundTile(xloc, yloc, zloc)) {
				fight();
				if (target.getHealth() <= 0) {
					completed = true;
				}
			} else {
				if (worker.isMovementNull()) {
					if (worker.getPath((int) xloc / Tile.SIZE, (int) yloc / Tile.SIZE) != null) {
						worker.setPath(worker.getPath((int) xloc / Tile.SIZE, (int) yloc / Tile.SIZE));
					} else {
						worker.drop();
						completed = true;
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
