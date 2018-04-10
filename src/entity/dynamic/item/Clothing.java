package entity.dynamic.item;

import entity.dynamic.mob.Villager;
import graphics.Sprite;

//villager clothing class
public class Clothing extends Item {
    private float defence; // defence rating for armour
    private ClothingType type;

    // basic constructor
    public Clothing(String name, int x, int y, int z, Sprite sprite, String tooltip, ClothingType type) {
        super(name, x, y, z, sprite, tooltip, true, 91); //TODO put all clothing options in itemHashtable
        this.type = type;
    }

    public Clothing(String name, Villager vil, Sprite sprite, String tooltip, ClothingType type) {
        this(name, vil.getX(), vil.getY(), vil.getZ(), sprite, tooltip, type);
    }

    public Clothing copy() {
        return new Clothing(getName(), getX(), getY(), getZ(), sprite, getToolTip(), type);
    }

    public ClothingType getType() {
        return type;
    }

    public float getDefence() {
        return defence;
    }

}
