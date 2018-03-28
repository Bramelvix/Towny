package entity.dynamic.mob.work;

import entity.dynamic.mob.Villager;
import entity.nonDynamic.building.Stairs;
import entity.pathfinding.Path;
import entity.pathfinding.PathFinder;

import java.util.ArrayList;

public class MoveJob extends Job {
    private ArrayList<Path> paths;
    private int counter = 0;

    public MoveJob(int xloc, int yloc, int zloc, Villager worker) {
        super(xloc, yloc, zloc, worker);
    }

    @Override
    protected void start() {
        paths = new ArrayList<>();
        started = true;
        if (worker.onSpot(xloc, yloc, zloc)) {
            completed = true;
            return;
        }
        if (zloc == worker.getZ()) {
            Path path = worker.getPath(xloc / 16, yloc / 16);
            if (path != null) {
                paths.add(path);
            } else { //no path
                completed = true;
                return;
            }

        } else {
            int stairsX = -1;
            int stairsY = -1;
            boolean up = worker.getZ() < zloc;
            for (int i = 0; i < Math.abs(zloc - worker.getZ()); i++) {
                Stairs stairs = worker.levels[worker.getZ() + (up ? i : -i)].getNearestStairs(worker.getX(), worker.getY(), zloc > worker.getZ());
                if (stairs != null) {
                    int startx = stairsX == -1 ? worker.getX() : stairsX;
                    int starty = stairsY == -1 ? worker.getY() : stairsY;
                    stairsX = stairs.getX();
                    stairsY = stairs.getY();
                    Path path = PathFinder.findPath(startx / 16, starty / 16, stairsX / 16, stairsY / 16, worker.levels[worker.getZ() + (up ? i : -i)]);
                    if (path != null) {
                        paths.add(path);
                    } else { //no stairs
                        completed = true;
                        return;
                    }
                } else { //no path
                    completed = true;
                    return;
                }

            }
            Path path = PathFinder.findPath(stairsX / 16, stairsY / 16, xloc / 16, yloc / 16, worker.levels[zloc]);
            if (path == null) { //no path
                completed = true;
                return;
            } else {
                paths.add(path);
            }

        }
        worker.setPath(paths.get(counter));
    }

    public void execute() {
        if (!completed && started) {
            if (worker.onSpot(paths.get(counter).getXdest() * 16, paths.get(counter).getYdest() * 16, worker.getZ())) {
                if (counter == paths.size() - 1) {
                    completed = true;
                    return;
                }
                Stairs stairs = worker.levels[worker.getZ()].getEntityOn(worker.getX(), worker.getY());
                if (stairs != null) {
                    stairs.goOnStairs(worker);
                }
                counter++;
                worker.setPath(paths.get(counter));


            } else {
                worker.move();
            }
        } else {
            start();
        }
    }
}
