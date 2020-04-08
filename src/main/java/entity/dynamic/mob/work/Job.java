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
	Job(int xloc, int yloc, int zloc, Villager worker) {
		this(worker);
		completed = false;
		this.xloc = xloc;
		this.yloc = yloc;
		this.zloc = zloc;
	}

	Job(Villager worker) {
		this.worker = worker;
	}

	public boolean isCompleted() {
		return completed;
	}

	protected void start() {
		worker.prependJobToChain(new MoveJob(xloc, yloc, zloc, worker,false));
		started = true;
	}

	public void execute() {
		if (!completed && started) {
			if (!worker.aroundTile(xloc, yloc, zloc)) {
				worker.prependJobToChain(new MoveJob(xloc, yloc, zloc, worker,false));
			} else if (work.getAsBoolean()) { completed = true; }
		} else {
			start();
		}
	}

}
