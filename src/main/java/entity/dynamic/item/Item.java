package entity.dynamic.item;

import entity.Entity;
import entity.dynamic.mob.Villager;
import graphics.Sprite;
import org.jetbrains.annotations.NotNull;

public class Item extends Entity {
	private final int id; //item id
	protected String tooltip; // the item's tooltip
	private Villager reservedVil; // the villager that plans to pick the item up, or is already holding it

	// basic constructor
	protected Item(String name, Sprite sprite, String tooltip, int id) {
		super(name, sprite, true);
		this.tooltip = tooltip;
		this.id = id;
	}

	protected Item copy() {
		return this.copy(this.location.x, this.location.y, this.z);
	}

	protected Item copy(float x, float y, int z) {
		Item copy = new Item(this.getName(), this.sprite, this.getToolTip(), this.getId());
		copy.setLocation(x, y, z);
		return copy;
	}

	public boolean isSameType(@NotNull ItemInfo<?> itemInfo) {
		return itemInfo.getId() == getId();
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
		setReserved(null);
	}

}