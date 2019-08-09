package entity.dynamic.mob.work;

import entity.dynamic.mob.Villager;

public abstract class Job implements Workable {
    boolean completed; // is the job done
    int xloc, yloc, zloc; // the x and y location of the job
    final Villager worker; // the villager doing the job
    boolean started = false;

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
        worker.addJob(new MoveJob(xloc,yloc,zloc,worker,false),0);
        started = true;
    }

    // execute the job
    public abstract void execute();

}
