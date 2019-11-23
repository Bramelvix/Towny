package entity.dynamic.mob.work;

import entity.dynamic.mob.Villager;
import entity.nonDynamic.resources.Resource;

public class GatherJob extends Job {
    private final Resource jobObj; // the resource the worker needs to gather

    public <T extends Resource> GatherJob(T jobObj, Villager worker) {
        super(jobObj.getX(),jobObj.getY(),jobObj.getZ(), worker);
        this.jobObj = jobObj;
        this.jobObj.setSelected(true);
    }

    @Override
    public void execute() {
        if (!completed && started) {
            if (!worker.aroundTile(xloc, yloc, zloc)) {
                worker.addJob(new MoveJob(xloc,yloc,zloc,worker,false),0);
            } else if (jobObj.work(worker)) { completed = true; }
        } else {
            start();
        }
    }
}
