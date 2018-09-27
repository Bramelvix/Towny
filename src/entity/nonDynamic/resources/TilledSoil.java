package entity.nonDynamic.resources;


import entity.dynamic.mob.Villager;
import graphics.SpriteHashtable;
import map.Level;

public class TilledSoil extends FarmingObj {
    private byte tilled = 20;

    public TilledSoil(int x, int y, int z, Level level) {
        super(x, y, z, level);
        sprite = SpriteHashtable.get(141);
    }

    public boolean work(Villager worker) {
        if (tilled > 0) {
            if (tilled == 10) {
                //play soil tilling sound
            }
            tilled--;
            return false;
        } else {
            setVisible(true);
            level.addEntity(this,false);
            return true;
        }
    }
}
