package entity.building.container;

import entity.building.BuildAbleObject;
import graphics.SpriteHashtable;

public class Chest extends Container {
    @Override
    public BuildAbleObject clone() {
        return new Chest();
    }

    public Chest() {
        super(10);
        sprite = SpriteHashtable.get(140);
        setName("chest");
    }
}
