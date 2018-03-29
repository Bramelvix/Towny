package entity.dynamic.mob.work;

import entity.dynamic.mob.Villager;
import entity.nonDynamic.building.Stairs;
import entity.pathfinding.Path;
import entity.pathfinding.PathFinder;

import java.util.ArrayList;

public class MoveJob extends Job {
    private ArrayList<Path> paths;
    private int counter = 0;
    private boolean exactLocation;

    public MoveJob(int xloc, int yloc, int zloc, Villager worker) { //movejob to move to this exact tile
        this(xloc, yloc, zloc, worker, true);
    }

    public MoveJob(int xloc, int yloc, int zloc, Villager worker, boolean exactLocation) { //movejob to move to a tile or around this tile
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
            Path path = exactLocation ? worker.getPath(xloc / 16, yloc / 16) : worker.getPathAround(xloc / 16, yloc / 16);
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
                    } else { //no path
                        completed = true;
                        return;
                    }
                } else { //no stairs
                    completed = true;
                    return;
                }
            }
            Path path = exactLocation ? PathFinder.findPath(stairsX / 16, stairsY / 16, xloc / 16, yloc / 16, worker.levels[zloc]) : PathFinder.findPathAround(stairsX / 16, stairsY / 16, xloc / 16, yloc / 16, worker.levels[zloc]);
            if ((exactLocation &&(xloc / 16) == (stairsX / 16) && (yloc / 16) == (stairsY / 16)) || (!exactLocation && (stairsX <= ((xloc + 16))) && (stairsX >= ((xloc - 16)) && ((stairsY >= ((yloc - 16))) && (stairsY <= ((yloc + 16))))))) {
            } else if (path == null) { //no path
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
            // System.out.println(worker.getX()/16 + ":" + worker.getY()/16 + ":::" + paths.get(counter).getXdest()+ ":" + paths.get(counter).getYdest());
            if (worker.onSpot(paths.get(counter).getXdest() * 16, paths.get(counter).getYdest() * 16, worker.getZ())) {
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
        Stairs stairs = worker.levels[worker.getZ()].getEntityOn(worker.getX(), worker.getY());
        if (stairs != null) {
            stairs.goOnStairs(worker);
        }
    }
}
