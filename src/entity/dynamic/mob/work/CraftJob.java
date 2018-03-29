package entity.dynamic.mob.work;

import entity.dynamic.item.Item;
import entity.dynamic.mob.Villager;
import entity.nonDynamic.building.workstations.Workstation;
import map.Tile;

public class CraftJob extends Job {
    private Item[] resources;
    private Item product;
    private Workstation station;
    private byte craftTimer = 100;
    private boolean itemsUpdated = false;

    public <T extends Item> CraftJob(Villager worker, Item[] resources, T product, Workstation station) {
        super(worker);
        this.product = product;
        this.station = station;
        this.resources = resources;
        for (Item item : this.resources) {
            if (item == null) {
                completed = true;
                return;
            }
            Tile empty = worker.levels[worker.getZ()].getNearestEmptySpot(station.getX(), station.getY());
            if (empty != null) {
                int stationDropPuntx = empty.x * 16;
                int stationDropPunty = empty.y * 16;
                worker.addJob(new MoveItemJob(item, worker));
                worker.addJob(new MoveItemJob(stationDropPuntx, stationDropPunty, worker));
            } else {
                completed = true;
            }

        }

    }

    @Override
    public void execute() {
        if (started && !completed) {
            if (!worker.aroundTile(station.getX(), station.getY(), station.getZ())) {
                if (worker.isMovementNull()) {
                    completed = true;
                } else {
                    worker.move();
                }
            } else {
                if (!itemsUpdated) {
                    station.setRunning(true);
                    for (Item i : resources) {
                        worker.levels[worker.getZ()].removeItem(i);
                    }
                    itemsUpdated = true;
                }
                if (craft()) {
                    worker.setHolding(product);
                    product.setVisible(true);
                    if (worker.levels[worker.getZ()].isClearTile(worker.getX() / 16, worker.getY() / 16)) {
                        worker.drop();
                    }
                    completed = true;
                }

            }
        } else {
            start();
        }
    }

    private boolean craft() {
        craftTimer--;
        if (craftTimer == 0) {
            station.setRunning(false);
            return true;
        }
        return false;
    }

    @Override
    protected void start() {
        worker.setPath(worker.getPathAround(station.getX() , station.getY() ));
        completed = worker.isMovementNull();
        started = true;
    }

}
