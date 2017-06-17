package entity.item;

import entity.Entity;
import entity.mob.Villager;
import graphics.Sprite;

public class Item extends Entity {
	private String tooltip; // the item's tooltip
	private Villager reservedVil; // the villager that plans to pick the item
									// up, or is already holding it
	public int quantity; // the amount of the item (5 logs in a stack for
							// instance)

	// basic constructors
	public Item(String name, int x, int y, Sprite sprite, String tooltip, boolean visible, int quantity) {
		super(x, y);
		this.sprite = sprite;
		setVisible(visible);
		super.setName(name);
		this.quantity = quantity;
		this.tooltip = tooltip;
	}

	public Item(String name, int x, int y, Sprite sprite, boolean visible, int quantity) {
		this(name, x, y, sprite, "", visible, quantity);
	}

	public Item(String name, Sprite sprite, boolean visible, int quantity) {
		super.setName(name);
		this.sprite = sprite;
		super.setVisible(visible);
		this.quantity = quantity;
	}

	public Item(Item o) {
		this(o.getName(), o.x, o.y, o.sprite, o.getToolTip(), o.isVisible(), o.quantity);
	}

	// getters and setters
	public String getToolTip() {
		return tooltip;
	}

	public boolean isReservedVil(Villager vil) {
		return reservedVil == null || reservedVil.equals(vil);
	}

	public void setReservedVil(Villager vil) {
		reservedVil = vil;
	}

	public void resetReservedVil() {
		reservedVil = null;
	}
}