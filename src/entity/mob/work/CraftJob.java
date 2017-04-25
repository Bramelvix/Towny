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
		int stationDropPuntx = -10;
		int stationDropPunty = -10;
		boolean legeplaats = false;
		if (worker.level.isWalkAbleTile(station.getX() - 16, station.getY())) {
			stationDropPuntx = station.getX() - 16;
			stationDropPunty = station.getY();
			legeplaats = true;
		} else if (worker.level.isWalkAbleTile(station.getX() - 16, station.getY() - 16)) {
			stationDropPuntx = station.getX() - 16;
			stationDropPunty = station.getY() - 16;
			legeplaats = true;
		} else if (worker.level.isWalkAbleTile(station.getX(), station.getY() - 16)) {
			stationDropPuntx = station.getX();
			stationDropPunty = station.getY() - 16;
			legeplaats = true;
		} else if (worker.level.isWalkAbleTile(station.getX() + 16, station.getY() - 16)) {
			stationDropPuntx = station.getX() + 16;
			stationDropPunty = station.getY() - 16;
			legeplaats = true;
		} else if (worker.level.isWalkAbleTile(station.getX() + 16, station.getY() + 16)) {
			stationDropPuntx = station.getX() + 16;
			stationDropPunty = station.getY() + 16;
			legeplaats = true;
		} else if (worker.level.isWalkAbleTile(station.getX(), station.getY() + 16)) {
			stationDropPuntx = station.getX();
			stationDropPunty = station.getY() + 16;
			legeplaats = true;
		} else if (worker.level.isWalkAbleTile(station.getX() + 16, station.getY())) {
			stationDropPuntx = station.getX() + 16;
			stationDropPunty = station.getY();
			legeplaats = true;
		} else if (worker.level.isWalkAbleTile(station.getX() - 16, station.getY() + 16)) {
			stationDropPuntx = station.getX() - 16;
			stationDropPunty = station.getY() + 16;
			legeplaats = true;
		}
		if (!legeplaats) {
			completed = true;
		}
		for (Item i : this.resources) {
			worker.addJob(new MoveItemJob(i, worker));
			worker.addJob(new MoveItemJob(stationDropPuntx, stationDropPunty, worker));

		}

	}

}
