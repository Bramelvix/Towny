package entity.dynamic.mob.work;

import entity.dynamic.mob.Villager;
import entity.nondynamic.resources.Resource;

public class GatherJob extends Job {
	public GatherJob(Villager worker, Resource jobObj) {
		super(worker, jobObj.getTileX(), jobObj.getTileY(), jobObj.getZ(), jobObj::work);
		jobObj.setSelected(true);
	}
}
