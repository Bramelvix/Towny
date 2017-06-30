package entity.mob.work;



import entity.item.Item;
import entity.mob.Villager;
import entity.workstations.Workstation;

public class CraftJob extends Job {
	private Item[] resources;
	private Item product;
	private Workstation station;

	public CraftJob(Villager worker, Item[] resources, Item product) {
		super(worker);
		this.resources = resources;
		this.product = product;
	}

	public CraftJob(Villager worker, Item[] resources, Item product, Workstation station) {
		super(worker);
		this.resources = resources;
		this.product = product;
		this.station = station;
		boolean[] legeplaatsen = new boolean[8];
		for (int i = 0; i < resources.length; i++) {
			Item item = resources[i];
			int stationDropPuntx = -10;
			int stationDropPunty = -10;
			if (worker.level.isWalkAbleTile((station.getX() / 16) - 1, station.getY() / 16) && !legeplaatsen[0]) {
				stationDropPuntx = station.getX() - 16;
				stationDropPunty = station.getY();
				legeplaatsen[0] = true;
			} else if (worker.level.isWalkAbleTile((station.getX() / 16) - 1, (station.getY() / 16) - 1)
					&& !legeplaatsen[1]) {
				stationDropPuntx = station.getX() - 16;
				stationDropPunty = station.getY() - 16;
				legeplaatsen[1] = true;
			} else if (worker.level.isWalkAbleTile(station.getX() / 16, (station.getY() / 16) - 1)
					&& !legeplaatsen[2]) {
				stationDropPuntx = station.getX();
				stationDropPunty = station.getY() - 16;
				legeplaatsen[2] = true;
			} else if (worker.level.isWalkAbleTile((station.getX() / 16) + 1, (station.getY() / 16) - 1)
					&& !legeplaatsen[3]) {
				stationDropPuntx = station.getX() + 16;
				stationDropPunty = station.getY() - 16;
				legeplaatsen[3] = true;
			} else if (worker.level.isWalkAbleTile((station.getX() / 16) + 1, (station.getY() / 16) + 1)
					&& !legeplaatsen[4]) {
				stationDropPuntx = station.getX() + 16;
				stationDropPunty = station.getY() + 16;
				legeplaatsen[4] = true;
			} else if (worker.level.isWalkAbleTile(station.getX() / 16, (station.getY() / 16) + 16)
					&& !legeplaatsen[5]) {
				stationDropPuntx = station.getX();
				stationDropPunty = station.getY() + 16;
				legeplaatsen[5] = true;
			} else if (worker.level.isWalkAbleTile((station.getX() / 16) + 16, station.getY() / 16)
					&& !legeplaatsen[6]) {
				stationDropPuntx = station.getX() + 16;
				stationDropPunty = station.getY();
				legeplaatsen[6] = true;
			} else if (worker.level.isWalkAbleTile((station.getX() / 16) - 16, (station.getY() / 16) + 16)
					&& !legeplaatsen[7]) {
				stationDropPuntx = station.getX() - 16;
				stationDropPunty = station.getY() + 16;
				legeplaatsen[7] = true;
			}
			if (doesNotContainTrue(legeplaatsen)) {
				completed = true;
				return;
			}
			worker.addJob(new MoveItemJob(item, worker));
			worker.addJob(new MoveItemJob(stationDropPuntx, stationDropPunty, worker));
		}

	}

	@Override
	public void execute() {
		if (started && !completed) {
			if (!worker.aroundTile(station.getX(), station.getY())) {
				if (worker.isMovementNull()) {
					completed = true;
				} else {
					worker.move();
				}
				return;
			} else {
				for (Item i : resources) {
					i.quantity--;
					if (i.quantity < 0) {
						worker.level.removeItem(i);
					}
				}
				worker.holding = product;
				completed = true;
			}
		} else {
			start();
		}
	}

	@Override
	protected void start() {
		worker.setMovement(worker.getShortest(station.getX() >> 4, station.getY() >> 4));
		completed = (worker.isMovementNull());
		started = true;
	}

	public static boolean doesNotContainTrue(boolean[] array) {
		for (boolean b : array) {
			if (b)
				return false;
		}
		return true;
	}

}
