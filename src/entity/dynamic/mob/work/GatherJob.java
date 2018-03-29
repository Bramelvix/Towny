package entity.dynamic.mob.work;

import entity.dynamic.mob.Villager;
import entity.nonDynamic.Resource;

public class GatherJob extends Job {
    private Resource jobObj; // the resource the worker needs to gather

    public GatherJob(Resource e, Villager worker) {
        super(e.getX(), e.getY(), e.getZ(), worker);
        jobObj = e;

    }

    public void execute() {
        if (!completed && started) {
            if (!worker.aroundTile(xloc, yloc, zloc)) {
                worker.addJob(new MoveJob(xloc,yloc,zloc,worker,false),0);
            } else {
                if (jobObj.work(worker)) {
                    completed = true;
                }
            }

        } else {
            start();
        }
    }
}
