package entity.dynamic.mob.work;

import entity.nonDynamic.building.container.Chest;
import entity.dynamic.item.Item;
import entity.dynamic.mob.Villager;

public class MoveItemJob extends Job {
    private boolean pickUpJob; // is the job a pickup or drop job
    private Chest chest;
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
        this.xloc = (xloc/16)*16; //locations are in pixels and have to be divisible by 16
        this.yloc = (yloc/16)*16;
        this.zloc = (zloc/16)*16;

    }


    @Override
    protected void start() {
        started = true;
        if (!pickUpJob && (worker.getHolding() == null || (!worker.levels[zloc].isClearTile(xloc / 16, yloc / 16) && !(worker.levels[zloc].getEntityOn(xloc, yloc) instanceof Chest)))) {
            completed = true;
            return;
        }
        if (worker.levels[zloc].getEntityOn(xloc, yloc) instanceof Chest) {
            chest = worker.levels[zloc].getEntityOn(xloc, yloc);
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
                    if (chest != null) {
                        if (worker.aroundTile(material.getX(), material.getY(), material.getZ())) {
                            if (worker.pickUp(material, chest)) {
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
                if (chest != null) {
                    if (worker.aroundTile(xloc, yloc, zloc)) {
                        worker.drop(chest);
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
