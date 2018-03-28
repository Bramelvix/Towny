package entity.dynamic.mob.work;

import entity.dynamic.item.Item;
import entity.dynamic.mob.Villager;
import entity.nonDynamic.building.BuildAbleObject;

public class BuildJob extends Job {
    private BuildAbleObject buildJobObj; // the buildable entity the worker needs to build
    private boolean needsMaterial; // does the worker still need the materials
    private boolean goingToPickUpItem = false;
    private Item material; // what materials are needed for the job (like logs for a wall)

    public BuildJob(int xloc, int yloc, int zloc, Item mat, BuildAbleObject object, Villager worker) {
        this(xloc, yloc, zloc, object, worker);
        needsMaterial = true;
        material = mat;
        if (material == null) {
            completed = true;
        }

    }

    public BuildJob(int xloc, int yloc, int zloc, BuildAbleObject object, Villager worker) { //construction job that requires no building materials
        super(xloc, yloc, zloc, worker);
        buildJobObj = object;
        material = null;
        needsMaterial = false;
    }

    private void goPickupItem() {
        if (!goingToPickUpItem) {
            worker.addJob(new MoveItemJob(material, worker), 0);
            goingToPickUpItem = true;
        }
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

    public void execute() {
        checkItem();
        if (needsMaterial) {
            goPickupItem();
        } else {
            if (!completed && started) {
                if (!worker.aroundTile(xloc, yloc, zloc)) {
                    worker.move();
                } else {
                    if (buildJobObj != null) {
                        if (!worker.levels[zloc].tileIsEmpty(xloc / 16, yloc / 16) && !buildJobObj.initialised) {
                            // wait if the buildLocation is blocked by an item or entity
                            System.out.println("Postponing Construction of: " + buildJobObj.toString());
                            return;
                        }
                        if (!buildJobObj.initialised) {
                            buildJobObj.initialise(xloc / 16, yloc / 16, worker.levels, zloc);
                        }
                        completed = buildJobObj.build();
                        if (material != null) {
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
