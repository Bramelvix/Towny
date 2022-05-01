package entity.dynamic.mob.work;

import entity.dynamic.mob.Villager;
import entity.non_dynamic.building.farming.TilledSoil;

public class FarmJob extends Job {
	public FarmJob(Villager worker, TilledSoil plot) {
		super(worker, plot.getTileX(), plot.getTileY(), plot.getZ(), plot.isPlanted() ? plot::harvest : plot::sow);
		plot.setSelected(true);
	}
}
