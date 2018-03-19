package entity.nonDynamic.building;

import graphics.SpriteHashtable;
import map.Level;

public class Stairs extends BuildAbleObject {

    public Stairs() {
        super();
        sprites.add(SpriteHashtable.get(40));
        setName("stairs");

    }

    public void initialise(int x, int y, Level level) {
        super.initialise(x, y, level);
        setOpened(true);
    }

    @Override
    public BuildAbleObject clone() {
        return new Stairs();
    }
}
