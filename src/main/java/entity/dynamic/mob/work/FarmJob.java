package entity.dynamic.mob.work;

import entity.dynamic.mob.Villager;
import entity.nonDynamic.building.farming.TilledSoil;

public class FarmJob extends Job {
	public FarmJob(TilledSoil plot, Villager worker) {
		super(plot.getTileX(), plot.getTileY(), plot.getZ(), worker);
		plot.setSelected(true);
		work = plot.isPlanted() ? plot::harvest : plot::sow;
	}
}
