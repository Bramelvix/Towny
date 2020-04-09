package entity.dynamic.mob.work;

import entity.dynamic.mob.Villager;
import entity.nonDynamic.building.Stairs;
import entity.pathfinding.Path;
import entity.pathfinding.PathFinder;

import java.util.ArrayList;
import java.util.Optional;

public class MoveJob extends Job {

	private ArrayList<Path> paths;
	private int counter = 0;
	private final boolean exactLocation;

	public MoveJob(int xloc, int yloc, int zloc, Villager worker) { //movejob to move to this exact tile
		this(xloc, yloc, zloc, worker, true);
	}

	MoveJob(int xloc, int yloc, int zloc, Villager worker, boolean exactLocation) { //movejob to move to a tile or around this tile
		super(xloc, yloc, zloc, worker);
		this.exactLocation = exactLocation;
	}

	@Override
	protected void start() {
		paths = new ArrayList<>();
		started = true;
		if ((exactLocation && worker.onSpot(xloc, yloc, zloc)) || (!exactLocation && worker.aroundTile(xloc, yloc, zloc))) {
			completed = true;
			return;
		}
		if (zloc == worker.getZ()) {
			Optional<Path> path = exactLocation ? worker.getPath(xloc, yloc) : worker.getPathAround(xloc, yloc);
			if (!path.isPresent()) {
				completed = true;
				return;
			}
			paths.add(path.get());
		} else {
			int stairsX = -1;
			int stairsY = -1;
			boolean up = worker.getZ() < zloc;
			for (int i = 0; i < Math.abs(zloc - worker.getZ()); i++) {
				Optional<Stairs> optional = worker.levels[worker.getZ() + (up ? i : -i)].getNearestStairs(worker.getTileX(), worker.getTileY(), zloc > worker.getZ());
				if (optional.isPresent()) {
					int startx = stairsX == -1 ? worker.getTileX() : stairsX;
					int starty = stairsY == -1 ? worker.getTileY() : stairsY;
					stairsX = optional.get().getTileX();
					stairsY = optional.get().getTileY();
					Optional<Path> path = PathFinder.findPath(startx, starty, stairsX, stairsY, worker.levels[worker.getZ() + (up ? i : -i)]);
					if (!path.isPresent()) {
						completed = true;
						return;
					}
					paths.add(path.get());
				} else { //no stairs
					completed = true;
					return;
				}
			}
			Optional<Path> path = exactLocation ? PathFinder.findPath(stairsX, stairsY, xloc, yloc, worker.levels[zloc]) : PathFinder.findPathAround(stairsX , stairsY ,  xloc, yloc , worker.levels[zloc]);
			if (!((exactLocation && xloc == stairsX && yloc == stairsY) || (!exactLocation && (stairsX <= ((xloc + 1))) && (stairsX >= ((xloc - 1)) && ((stairsY >= ((yloc - 1))) && (stairsY <= ((yloc + 1)))))))) {
				if (!path.isPresent()) { //no path
					completed = true;
					return;
				}
				paths.add(path.get());
			}
		}
		worker.setPath(paths.get(counter));
	}

	public void execute() {
		if (!completed && started) {
			if (worker.onSpot(paths.get(counter).getXdest(), paths.get(counter).getYdest(), worker.getZ())) {
				if (counter == paths.size() - 1) {
					completed = true;
					if (zloc != worker.getZ()) {
						goOnStairs();
					}
					return;
				}
				goOnStairs();
				counter++;
				worker.setPath(paths.get(counter));
			} else {
				worker.move();
			}
		} else {
			start();
		}
	}

	private void goOnStairs() {
		worker.levels[worker.getZ()].getEntityOn(worker.getTileX(), worker.getTileY(), Stairs.class).ifPresent(stairs -> stairs.goOnStairs(worker));
	}

}
