package entity.dynamic.mob;

import entity.dynamic.item.Item;
import map.Level;
import map.Tile;

public abstract class Humanoid extends Mob {

    private Item holding; // item the mob is holding in his hands

    Humanoid(Level[] levels, int x, int y, int z) {
        super(levels);
        while (!levels[z].isWalkAbleTile(x / Tile.SIZE, y / Tile.SIZE)) {
            x += Tile.SIZE;
            y += Tile.SIZE;
        }
        setLocation(x, y, z);
    }

    public <T extends Item> boolean isHolding(T item) {
        return holding != null && holding.equals(item);
    }

    public <T extends Item> void setHolding(T item) {
        holding = item;
        if (holding != null) {
            holding.setLocation(this.x, this.y, this.z);
        }
    }

    public Item getHolding() {
        return holding;
    }

    @Override
    public void die() {
        if (holding != null) {
            levels[z].addItem(holding);
        }

    }

    // DO NOT TOUCH THIS. SET THE MOVEMENT TO THE PATH OBJ USE move()!! DO NOT USE!!!
    @Override
    protected final void moveTo(int x, int y) {
        super.moveTo(x, y);
        if (!(holding == null)) {
            holding.setLocation(this.x, this.y, this.z);
        }

    }

}
