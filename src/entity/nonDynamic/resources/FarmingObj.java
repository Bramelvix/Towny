package entity.nonDynamic.resources;

import entity.dynamic.mob.Villager;
import map.Level;

public abstract class FarmingObj extends Resource {

    public FarmingObj(int x, int y, int z, Level level) {
        super(x, y, z, level);
    }
    public abstract boolean work(Villager worker);
}
