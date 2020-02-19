package entity.dynamic.mob.work;

import entity.dynamic.mob.Villager;
import entity.nonDynamic.building.Stairs;
import entity.pathfinding.Path;
import entity.pathfinding.PathFinder;
import map.Tile;

import java.util.ArrayList;
import java.util.Optional;

public class MoveJob extends Job {

	private ArrayList<Path> paths;
	private int counter = 0;
	private final boolean exactLocation;

	public MoveJob(float xloc, float yloc, int zloc, Villager worker) { //movejob to move to this exact tile
		this(xloc, yloc, zloc, worker, true);
	}

	MoveJob(float xloc, float yloc, int zloc, Villager worker, boolean exactLocation) { //movejob to move to a tile or around this tile
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
			Path path = exactLocation ? worker.getPath((int) xloc / Tile.SIZE, (int) yloc / Tile.SIZE) : worker.getPathAround((int) xloc / Tile.SIZE, (int)yloc / Tile.SIZE);
			if (path != null) {
				paths.add(path);
			} else { //no path
				completed = true;
				return;
			}
		} else {
			float stairsX = -1;
			float stairsY = -1;
			boolean up = worker.getZ() < zloc;
			for (int i = 0; i < Math.abs(zloc - worker.getZ()); i++) {
				Optional<Stairs> optional = worker.levels[worker.getZ() + (up ? i : -i)].getNearestStairs((int) worker.getX()/Tile.SIZE, (int) worker.getY()/ Tile.SIZE, zloc > worker.getZ());
				if (optional.isPresent()) {
					float startx = stairsX == -1 ? worker.getX() : stairsX;
					float starty = stairsY == -1 ? worker.getY() : stairsY;
					stairsX = optional.get().getX();
					stairsY = optional.get().getY();
					Path path = PathFinder.findPath((int) startx / Tile.SIZE, (int) starty / Tile.SIZE, (int) stairsX / Tile.SIZE, (int) stairsY / Tile.SIZE, worker.levels[worker.getZ() + (up ? i : -i)]);
					if (path != null) {
						paths.add(path);
					} else { //no path
						completed = true;
						return;
					}
				} else { //no stairs
					completed = true;
					return;
				}
			}
			Path path = exactLocation ? PathFinder.findPath((int) stairsX / Tile.SIZE, (int) stairsY / Tile.SIZE, (int) xloc / Tile.SIZE, (int) yloc / Tile.SIZE, worker.levels[zloc]) : PathFinder.findPathAround((int) stairsX / Tile.SIZE, (int) stairsY / Tile.SIZE, (int) xloc / Tile.SIZE, (int) yloc / Tile.SIZE, worker.levels[zloc]);
			if (!((exactLocation &&(xloc / Tile.SIZE) == (stairsX / Tile.SIZE) && (yloc / Tile.SIZE) == (stairsY / Tile.SIZE)) || (!exactLocation && (stairsX <= ((xloc + Tile.SIZE))) && (stairsX >= ((xloc - Tile.SIZE)) && ((stairsY >= ((yloc - Tile.SIZE))) && (stairsY <= ((yloc + Tile.SIZE)))))))) {
				if (path == null) { //no path
					completed = true;
					return;
				} else {
					paths.add(path);
				}
			}
		}
		worker.setPath(paths.get(counter));
	}

	public void execute() {
		if (!completed && started) {
			if (worker.onSpot(paths.get(counter).getXdest() * Tile.SIZE, paths.get(counter).getYdest() * Tile.SIZE, worker.getZ())) {
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
		Stairs stairs = worker.levels[worker.getZ()].getEntityOn((int)worker.getX()/Tile.SIZE, (int) worker.getY()/Tile.SIZE);
		if (stairs != null) {
			stairs.goOnStairs(worker);
		}
	}

}
