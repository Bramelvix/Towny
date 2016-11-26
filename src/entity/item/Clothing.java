package entity.item;

import graphics.Sprite;

public class Clothing extends Item {
	public float defence;

	public Clothing(String name, int x, int y, Sprite sprite, String tooltip, boolean visible) {
		super(name, x, y, sprite, tooltip, visible);
	}

}
