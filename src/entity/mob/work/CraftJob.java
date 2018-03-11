package entity.mob.work;

import entity.item.Item;
import entity.mob.Villager;
import entity.building.workstations.Workstation;

public class CraftJob extends Job {
	private Item[] resources;
	private Item product;
	private Workstation station;
	private byte craftTimer = 100;
	private boolean itemsUpdated = false;

	public <T extends Item> CraftJob(Villager worker, Item[] resources, T product) {
		super(worker);
		this.resources = resources;
		this.product = product;
	}

	public <T extends Item> CraftJob(Villager worker, Item[] resources, T product, Workstation station) {
		super(worker);
		this.product = product;
		this.station = station;
		boolean[] legeplaatsen = new boolean[8];
		this.resources = resources;
		for (Item item : this.resources) {
			if (item == null) {
				completed = true;
				return;
			}
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
				if (!itemsUpdated) {
					station.setRunning(true);
					for (Item i : resources) {
						worker.level.removeItem(i);
					}
					itemsUpdated = true;
				}
				if (craft()) {
					worker.setHolding(product);
					if (worker.level.isClearTile(worker.getX() / 16, worker.getY() / 16)) {
						worker.drop();
					}
					completed = true;
				}

			}
		} else {
			start();
		}
	}

	private boolean craft() {
		craftTimer--;
		if (craftTimer == 0) {
			station.setRunning(false);
			return true;
		}
		return false;
	}

	@Override
	protected void start() {
		worker.setMovement(worker.getShortest(station.getX() >> 4, station.getY() >> 4));
		completed = worker.isMovementNull();
		started = true;
	}

	public static boolean doesNotContainTrue(boolean[] array) {
		for (boolean b : array) {
			if (b) {
				return false;
			}
		}
		return true;
	}

}
