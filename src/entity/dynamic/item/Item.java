package entity.dynamic.item;

import entity.Entity;
import entity.dynamic.mob.Villager;
import graphics.Sprite;

public class Item extends Entity {

    private final int id;
    protected String tooltip; // the item's tooltip
    private Villager reservedVil; // the villager that plans to pick the item up, or is already holding it

    // basic constructors
    protected Item(String name, int x, int y, int z, Sprite sprite, String tooltip, boolean visible, int id) {
        super(x, y, z);
        sprites.add(sprite);
        setVisible(visible);
        super.setName(name);
        this.tooltip = tooltip;
        this.id = id;
    }

    public boolean isSameType(Item item) {
        return item.getId() == getId();
    }

    protected Item(String name, int x, int y, int z, Sprite sprite, boolean visible, int id) {
        this(name, x, y, z, sprite, "", visible, id);
    }

    protected Item(String name, Sprite sprite, String tooltip, int id) {
        super.setName(name);
        sprites.add(sprite);
        this.tooltip = tooltip;
        setVisible(true);
        this.id = id;
    }

    public Item copy() {
        return new Item(this.getName(), this.x, this.y, this.z, this.sprites.get(0), this.getToolTip(), this.isVisible(), this.getId());
    }

    public Item copy(int x, int y, int z) {
        Item copy = this.copy();
        copy.setLocation(x, y, z);
        return copy;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    String getToolTip() {
        return tooltip;
    }

    public boolean isReserved(Villager vil) {
        return reservedVil == null || reservedVil.equals(vil);
    }

    public void setReserved(Villager vil) {
        reservedVil = vil;
    }

    public void removeReserved() {
        reservedVil = null;
    }


}