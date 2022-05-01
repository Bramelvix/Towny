package entity.dynamic.mob.work;

import entity.dynamic.mob.Villager;

import java.util.function.BooleanSupplier;

public abstract class Job {
	boolean completed; // is the job done
	int xloc;
	int yloc;
	int zloc; // the x and y location of the job
	final Villager worker; // the villager doing the job
	boolean started = false;
	protected BooleanSupplier work;

	// constructors
	Job(Villager worker, int xloc, int yloc, int zloc) {
		this(worker);
		this.xloc = xloc;
		this.yloc = yloc;
		this.zloc = zloc;
	}

	Job(Villager worker, int xloc, int yloc, int zloc, BooleanSupplier work) {
		this(worker, xloc, yloc, zloc);
		this.work = work;
	}

	Job(Villager worker) {
		this.worker = worker;
		completed = false;
	}

	public boolean isCompleted() {
		return completed;
	}

	protected void start() {
		worker.prependJobToChain(new MoveJob(worker, xloc, yloc, zloc, false));
		started = true;
	}

	public void execute() {
		if (!completed && started) {
			if (!worker.aroundTile(xloc, yloc, zloc)) {
				worker.prependJobToChain(new MoveJob(worker, xloc, yloc, zloc, false));
			} else if (work.getAsBoolean()) {
				completed = true;
			}
		} else {
			start();
		}
	}

}
