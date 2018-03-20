package entity.dynamic.mob.work;

import entity.nonDynamic.building.BuildAbleObject;
import entity.nonDynamic.Resource;
import entity.dynamic.item.Item;
import entity.dynamic.mob.Villager;

public class Job implements Workable {
    boolean completed; // is the job done
    Item material; // what materials are needed for the job (like logs for a wall)
    int xloc, yloc, zloc; // the x and y location of the job
    private boolean needsMaterial; // does the worker still need the materials
    Villager worker; // the villager doing the job
    private Resource jobObj; // the resource the worker needs to gather
    private boolean buildJob; // is the job a buildjob
    private BuildAbleObject buildJobObj; // the buildable entity the worker needs to build
    boolean started = false;
    private boolean goingToPickUpItem = false;

    // constructors
    private Job(int xloc, int yloc, int zloc, Villager worker) {
        this(worker);
        completed = false;
        this.xloc = xloc;
        this.yloc = yloc;
        this.zloc = zloc;
        needsMaterial = false;
    }

    protected Job(Villager worker) {
        this.worker = worker;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Job(int xloc, int yloc, int zloc, Item mat, BuildAbleObject object, Villager worker) {
        this(xloc, yloc, zloc, object, worker);
        needsMaterial = true;
        material = mat;
        if (material == null) {
            completed = true;
        }

    }

    public Job(int xloc, int yloc, int zloc, BuildAbleObject object, Villager worker) { //construction job that requires no building materials
        this(xloc, yloc, zloc, worker);
        buildJob = true;
        buildJobObj = object;
        needsMaterial = false;
    }

    public Job(Resource e, Villager worker) {
        this(e.getX(), e.getY(), e.getZ(), worker);
        jobObj = e;

    }

    // checks if the worker still needs the item
    private void checkItem() {
        if (needsMaterial && worker.getHolding() != null) {
            if (worker.getHolding().equals(material)) {
                needsMaterial = false;
            } else {
                worker.drop();
            }
        }
    }

    protected void start() {
        worker.setMovement(worker.getShortest(xloc / 16, yloc / 16));
        completed = worker.isMovementNull();
        started = true;
    }

    private void goPickupItem() {
        if (!goingToPickUpItem) {
            worker.addJob(new MoveItemJob(material, worker), 0);
            goingToPickUpItem = true;
        }
    }

    // execute the job
    public void execute() {
        checkItem();
        if (needsMaterial) {
            goPickupItem();
        } else {
            if (!completed && started) {
                if (!worker.aroundTile(xloc, yloc, zloc)) {
                    if (worker.isMovementNull()) {
                        completed = true;
                    } else {
                        worker.move();
                    }
                } else {
                    if (jobObj != null) {
                        if (jobObj.work(worker)) {
                            completed = true;
                        }
                    } else {
                        if (buildJob && buildJobObj != null) {
                            if (!worker.levels[worker.getZ()].tileIsEmpty(xloc / 16, yloc / 16) && !buildJobObj.initialised) {
                                // wait if the buildLocation is blocked by an item or entity
                                System.out.println("Postponing Construction of: " + buildJobObj.toString());
                                return;
                            }
                            if (!buildJobObj.initialised) {
                                buildJobObj.initialise(xloc / 16, yloc / 16, worker.levels[worker.getZ()]);
                            }
                            completed = buildJobObj.build();
                            worker.setHolding(null);

                        }

                    }
                }

            } else {
                start();
            }
        }
    }

}
