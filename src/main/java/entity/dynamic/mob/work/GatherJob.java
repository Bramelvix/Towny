package entity.dynamic.mob.work;

import entity.dynamic.mob.Villager;
import entity.nonDynamic.resources.Resource;

public class GatherJob extends Job {
	public <T extends Resource> GatherJob(T jobObj, Villager worker) {
		super(jobObj.getTileX(), jobObj.getTileY(), jobObj.getZ(), worker);
		jobObj.setSelected(true);
		work = jobObj::work;
	}
}
