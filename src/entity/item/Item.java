package entity.item;

import entity.Entity;
import entity.mob.Villager;
import graphics.Sprite;

public class Item extends Entity {
	public static final Item logs = new Item("logs", Sprite.logs, "Wooden logs");
	public static final Item iron_bar = new Item("iron bar", Sprite.ironBar, "a bar of solid iron");
	public static final Item gold_bar = new Item("gold bar", Sprite.goldBar, "a bar of solid gold");
	public static final Item copper_bar = new Item("copper bar", Sprite.copperBar, "a bar of solid copper");
	public static final Item iron_ore = new Item("iron ore", Sprite.ironChunk, "iron ore");
	public static final Item copper_ore = new Item("copper ore", Sprite.copperOre, "copper ore");
	public static final Item gold_ore = new Item("gold ore", Sprite.goldChunk, "gold ore");
	public static final Item coal_ore = new Item("coal ore", Sprite.coalChunk, "coal ore");
	public static final Item crystal_ore = new Item("uncut crystal", Sprite.crystalChunkUncut, "an uncut crystal");
	public static final Item stone = new Item("stone", Sprite.stoneChunk, "some stones");
	public static final Item cut_crystal = new Item("cut crystal", Sprite.crystalChunkCut, "a cut crystal");

	protected String tooltip; // the item's tooltip
	private Villager reservedVil; // the villager that plans to pick the item
									// up, or is already holding it

	// basic constructors
	protected Item(String name, int x, int y, Sprite sprite, String tooltip, boolean visible) {
		super(x, y);
		this.sprite = sprite;
		setVisible(visible);
		super.setName(name);
		this.tooltip = tooltip;
	}

	public boolean isSameType(Item item) {
		return item.getName().equals(getName());
	}

	protected Item(String name, int x, int y, Sprite sprite, boolean visible) {
		this(name, x, y, sprite, "", visible);
	}

	protected Item(String name, Sprite sprite, String tooltip) {
		super.setName(name);
		this.sprite = sprite;
		this.tooltip = tooltip;
		setVisible(true);
	}

	public Item copy() {
		return new Item(this.getName(), this.x, this.y, this.sprite, this.getToolTip(), this.isVisible());
	}

	public Item copy(int x, int y) {
		Item copy = this.copy();
		copy.setLocation(x, y);
		return copy;
	}

	// getters and setters
	public String getToolTip() {
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