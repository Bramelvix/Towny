package entity.nonDynamic.building.farming;

import entity.nonDynamic.building.BuildAbleObject;
import graphics.SpriteHashtable;

public class TilledSoil extends BuildAbleObject {

    public TilledSoil() {
        super();
        this.setOpened(true);
        sprite = SpriteHashtable.get(141);
    }
    @Override
    public BuildAbleObject instance() {
        return new TilledSoil();
    }

}
