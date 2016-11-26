package entity.item;

import entity.Entity;
import graphics.Sprite;

public class Item extends Entity {
	private String name;
	private String tooltip;
	private boolean visible;

	public Item(String name, int x, int y, Sprite sprite, String tooltip, boolean visible) {
		super(x, y);
		this.sprite1 = sprite;
		this.visible = true;
		this.name = name;
		this.tooltip = tooltip;
	}

	public Item(String name, int x, int y, Sprite sprite, boolean visible) {
		this(name, x, y, sprite, "", visible);
	}

	public String getName() {
		return name;
	}
}
