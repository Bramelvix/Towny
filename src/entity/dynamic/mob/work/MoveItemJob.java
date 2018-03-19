package entity.dynamic.mob.work;

import entity.nonDynamic.building.container.Chest;
import entity.dynamic.item.Item;
import entity.dynamic.mob.Villager;

public class MoveItemJob extends Job {
    private boolean pickUpJob; // is the job a pickup or drop job
    private Chest chest;

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
        }
    }

    private MoveItemJob(Villager worker) {
        super(worker);
    }

    public MoveItemJob(int xloc, int yloc, Villager worker) {
        this(worker);
        pickUpJob = false;
        this.xloc = xloc;
        this.yloc = yloc;

    }


    @Override
    protected void start() {
        started = true;
        if (!pickUpJob && (worker.getHolding() == null || (!worker.level.isClearTile(xloc / 16, yloc / 16) && !(worker.level.getHardEntityOn(xloc, yloc) instanceof Chest)))) {
            completed = true;
            return;
        }
        if (worker.level.getHardEntityOn(xloc, yloc) instanceof Chest) {
            chest = (Chest) worker.level.getHardEntityOn(xloc, yloc);
            worker.setMovement(worker.getPath(worker.level.getNearestEmptySpot(xloc, yloc)));
        } else {
            worker.setMovement(worker.getPath(xloc / 16, yloc / 16));
        }

    }

    public void execute() {
        if (!completed && started) {
            if (pickUpJob) {
                if (worker.isHolding(material)) {
                    completed = true;
                } else {
                    if (chest != null) {
                        if (worker.aroundTile(material.getX(), material.getY())) {
                            if (worker.pickUp(material, chest)) {
                                completed = true;
                            }
                            return;
                        }
                    } else if (worker.onSpot(material.getX(), material.getY())) {
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
                    if (worker.aroundTile(xloc, yloc)) {
                        worker.drop(chest);
                        completed = true;
                        return;
                    }
                } else if (worker.onSpot(xloc, yloc)) {
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
