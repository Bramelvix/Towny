package entity.item;

import entity.mob.Villager;
import graphics.Sprite;

//villager clothing class
public class Clothing extends Item {
	public float defence; // defence rating for armour
	private ClothingType type;

	// basic constructor
	public Clothing(String name, int x, int y, Sprite sprite, String tooltip, boolean visible) {
		super(name, x, y, sprite, tooltip, visible, 1);
	}

	public Clothing(String name, Villager vil, Sprite sprite, String tooltip, ClothingType type) {
		this(name, vil.getX(), vil.getY(), sprite, tooltip, true);
		this.type = type;
	}

	public ClothingType getType() {
		return type;
	}

}
