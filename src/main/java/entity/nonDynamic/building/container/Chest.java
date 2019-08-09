package entity.nonDynamic.building.container;

import entity.nonDynamic.building.BuildAbleObject;
import graphics.SpriteHashtable;

public class Chest extends Container {
    @Override
    public BuildAbleObject instance() {
        return new Chest();
    }

    public Chest() {
        super(10);
        sprite = SpriteHashtable.get(39);
        setName("chest");
    }
}
