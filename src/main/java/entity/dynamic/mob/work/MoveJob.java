package entity.dynamic.mob.work;

import entity.dynamic.mob.Villager;
import entity.nondynamic.building.Stairs;
import entity.pathfinding.Path;
import entity.pathfinding.PathFinder;

import java.util.ArrayList;
import java.util.Optional;

public class MoveJob extends Job {

	private ArrayList<Path> paths;
	private int counter = 0;
	private final boolean exactLocation;

	public MoveJob(Villager worker, int xloc, int yloc, int zloc) { //movejob to move to this exact tile
		this(worker, xloc, yloc, zloc, true);
	}

	MoveJob(Villager worker, int xloc, int yloc, int zloc, boolean exactLocation) { //movejob to move to a tile or around this tile
		super(worker, xloc, yloc, zloc);
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
			if (path.isEmpty()) {
				completed = true;
				return;
			}
			paths.add(path.get());
		} else {
			int startx = worker.getTileX();
			int starty = worker.getTileY();
			boolean goingDown = worker.getZ() < zloc;
			for (int i = 0; i < Math.abs(zloc - worker.getZ()); i++) {
				int z = worker.getZ() + (goingDown ? i : -i);
				Optional<Stairs> optional = worker.levels[z].getNearestStairs(startx, starty, goingDown);
				if (optional.isEmpty()) { // no stairs found
					completed = true;
					return;
				}
				int stairsX = optional.get().getTileX();
				int stairsY = optional.get().getTileY();
				Optional<Path> path = PathFinder.findPath(startx, starty, stairsX, stairsY, worker.levels[z]);
				if (path.isEmpty()) {
					completed = true;
					return;
				}
				paths.add(path.get());
				startx = stairsX;
				starty = stairsY;
			}
			Optional<Path> path = exactLocation ? PathFinder.findPath(startx, starty, xloc, yloc, worker.levels[zloc]) : PathFinder.findPathAround(startx, starty, xloc, yloc, worker.levels[zloc]);
			if (!((exactLocation && xloc == startx && yloc == starty) || (!exactLocation && (startx <= (xloc + 1)) && (startx >= (xloc - 1) && ((starty >= (yloc - 1)) && (starty <= (yloc + 1))))))) {
				if (path.isEmpty()) { //no path
					completed = true;
					return;
				}
				paths.add(path.get());
			}
		}
		worker.setPath(paths.get(counter));
	}

	@Override
	public void execute() {
		if (completed || !started) {
			start();
			return;
		}

		if (!worker.onSpot(paths.get(counter).getXdest(), paths.get(counter).getYdest(), worker.getZ())) {
			worker.move();
			return;
		}

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
	}

	private void goOnStairs() {
		worker.levels[worker.getZ()].getEntityOn(worker.getTileX(), worker.getTileY(), Stairs.class).ifPresent(stairs -> stairs.goOnStairs(worker));
	}

}
