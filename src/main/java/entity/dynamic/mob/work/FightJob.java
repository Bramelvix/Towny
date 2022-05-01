package entity.dynamic.mob.work;

import entity.dynamic.mob.Mob;
import entity.dynamic.mob.Villager;
import entity.pathfinding.Path;

import java.util.Optional;

public class FightJob extends Job {

	private final Mob target;
	private int timer = 0;

	public FightJob(Villager worker, Mob target) {
		super(worker);
		this.target = target;
	}

	@Override
	protected void start() {
		worker.setPath(worker.getPath(xloc, yloc).orElse(null));
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
		}

		xloc = target.getTileX();
		yloc = target.getTileY();

		if (completed || !started) {
			start();
		}

		if (worker.aroundTile(xloc, yloc, zloc)) {
			fight();
			if (target.getHealth() <= 0) {
				completed = true;
			}
			return;
		}

		if (worker.isMovementNull()) {
			Optional<Path> found = worker.getPath(xloc, yloc);
			if (found.isPresent()) {
				worker.setPath(found.get());
			} else {
				worker.drop();
				completed = true;
			}
			return;
		}

		worker.move();
	}
}
