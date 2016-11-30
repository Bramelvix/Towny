package entity.item;

import entity.Entity;
import graphics.Sprite;
import graphics.Screen;

public class Item extends Entity {
	private String name;
	private String tooltip;
	private boolean visible;
	public int quantity;

	public Item(String name, int x, int y, Sprite sprite, String tooltip, boolean visible, int quantity) {
		super(x, y);
		this.sprite = sprite;
		setVisible(visible);
		this.name = name;
		this.quantity = quantity;
		this.tooltip = tooltip;
	}

	public Item(String name, int x, int y, Sprite sprite, boolean visible, int quantity) {
		this(name, x, y, sprite, "", visible, quantity);
	}
	public Item(Item o) {
		this(o.getName(), o.x, o.y, o.sprite, o.getToolTip(),o.isVisible(), o.quantity);
	}

	public String getToolTip() {
		return tooltip;
	}
	public String getName() {
		return name;
	}
}
