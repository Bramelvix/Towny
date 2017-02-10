package entity.item;

import graphics.Sprite;

//villager clothing class
public class Clothing extends Item {
	public float defence; // defence rating for armour

	//basic constructor
	public Clothing(String name, int x, int y, Sprite sprite, String tooltip, boolean visible) {
		super(name, x, y, sprite, tooltip, visible,1);
	}

}
