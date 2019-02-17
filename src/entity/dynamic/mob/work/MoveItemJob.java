package entity.dynamic.mob.work;

import entity.dynamic.item.Item;
import entity.dynamic.mob.Villager;
import entity.nonDynamic.building.container.Container;
import map.Tile;

public class MoveItemJob extends Job {
    private boolean pickUpJob; // is the job a pickup or drop job
    private Container container;
    private Item material;

    public MoveItemJob(Item material, Villager worker) {
        this(worker);
        pickUpJob = true;
        this.material = material;
        if (this.worker.isHolding(this.material) || !this.material.isReserved(this.worker)) {
            completed = true;
        } else {
            this.material.setReserved(this.worker);
            xloc = material.getX();
            yloc = material.getY();
            zloc = material.getZ();
        }
    }

    private MoveItemJob(Villager worker) {
        super(worker);
    }

    public MoveItemJob(int xloc, int yloc, int zloc, Villager worker) {
        this(worker);
        pickUpJob = false;
        this.xloc = (xloc/Tile.SIZE)*Tile.SIZE; //locations are in tile numbers
        this.yloc = (yloc/Tile.SIZE)*Tile.SIZE;
        this.zloc = zloc;

    }


    @Override
    protected void start() {
        started = true;
        if (!pickUpJob && (worker.getHolding() == null || (!worker.levels[zloc].isClearTile(xloc / Tile.SIZE, yloc / Tile.SIZE) && !(worker.levels[zloc].getEntityOn(xloc/Tile.SIZE, yloc/Tile.SIZE) instanceof Container)))) {
            completed = true;
            return;
        }
        if (worker.levels[zloc].getEntityOn(xloc/Tile.SIZE, yloc/Tile.SIZE) instanceof Container) {
            container = worker.levels[zloc].getEntityOn(xloc/Tile.SIZE, yloc/Tile.SIZE);
            worker.addJob(new MoveJob(xloc, yloc, zloc, worker, false), 0);
        } else {
            worker.addJob(new MoveJob(xloc, yloc, zloc, worker), 0);
        }

    }

    public void execute() {
        if (!completed && started) {
            if (pickUpJob) {
                if (worker.isHolding(material)) {
                    completed = true;
                } else {
                    if (container != null) {
                        if (worker.aroundTile(material.getX(), material.getY(), material.getZ())) {
                            if (worker.pickUp(material, container)) {
                                completed = true;
                            }
                            return;
                        }
                    } else if (worker.onSpot(material.getX(), material.getY(), material.getZ())) {
                        if (worker.pickUp(material)) {
                            completed = true;
                        }
                        return;
                    }

                    if (worker.isMovementNull()) {
                        completed = true;
                        if (pickUpJob) {
                            material.removeReserved();
                        }
                    }
                    worker.move();
                }

            } else {
                if (container != null) {
                    if (worker.aroundTile(xloc, yloc, zloc)) {
                        worker.drop(container);
                        completed = true;
                        return;
                    }
                } else if (worker.onSpot(xloc, yloc, zloc)) {
                    worker.drop();
                    completed = true;
                    return;
                }
                if (worker.isMovementNull()) {
                    completed = true;
                    if (pickUpJob) {
                        material.removeReserved();
                    }
                }
                worker.move();

            }

        } else {
            start();
        }
    }

}
