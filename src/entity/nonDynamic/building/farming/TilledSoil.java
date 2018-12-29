package entity.nonDynamic.building.farming;

import entity.nonDynamic.building.BuildAbleObject;
import graphics.SpriteHashtable;

public class TilledSoil extends FarmingObj {

    public TilledSoil() {
        super();
        sprite = SpriteHashtable.get(141);
    }
    @Override
    public BuildAbleObject clone() {
        return new TilledSoil();
    }

}
