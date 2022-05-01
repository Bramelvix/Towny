package entity.dynamic.mob.work;

import entity.dynamic.item.Item;
import entity.dynamic.mob.Villager;
import entity.non_dynamic.building.BuildAbleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuildJob extends Job {

	final Logger logger = LoggerFactory.getLogger(BuildJob.class);
	private final BuildAbleObject buildJobObj; // the buildable entity the worker needs to build
	private boolean needsMaterial; // does the worker still need the materials
	private boolean goingToPickUpItem = false;
	private Item material; // what materials are needed for the job (like logs for a wall)

	public BuildJob(Villager worker, int xloc, int yloc, int zloc, Item mat, BuildAbleObject object) {
		this(worker, xloc, yloc, zloc, object);
		needsMaterial = true;
		material = mat;
		if (material == null) {
			completed = true;
		}
	}

	public BuildJob(Villager worker, int xloc, int yloc, int zloc, BuildAbleObject object) { //construction job that requires no building materials
		super(worker, xloc, yloc, zloc, object::work);
		buildJobObj = object;
		material = null;
		needsMaterial = false;
	}

	private void goPickupItem() {
		if (!goingToPickUpItem) {
			worker.prependJobToChain(new MoveItemJob(worker, material));
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

	@Override
	public void execute() {
		checkItem();
		if (needsMaterial) {
			goPickupItem();
			return;
		}

		if (completed || !started) {
			start();
			return;
		}

		if (!worker.aroundTile(xloc, yloc, zloc)) {
			worker.move();
			return;
		}

		if (buildJobObj != null) {
			if (!worker.levels[zloc].tileIsEmpty(xloc, yloc) && !buildJobObj.isInitialised()) {
				// wait if the buildLocation is blocked by an item or entity
				logger.debug("Postponing Construction of: {}", buildJobObj);
				return;
			}
			if (!buildJobObj.isInitialised()) {
				buildJobObj.initialise(xloc, yloc, worker.levels, zloc);
			}
			completed = work.getAsBoolean();
			if (material != null) {
				worker.setHolding(null);
			}
		}

	}

}
