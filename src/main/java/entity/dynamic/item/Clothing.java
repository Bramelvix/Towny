package entity.dynamic.item;

import graphics.Sprite;

//villager clothing class
public class Clothing extends Item {

	private float defence; // defence rating for armour
	private final ClothingType type;

	// basic constructor
	Clothing(String name, Sprite sprite, String tooltip, ClothingType type, float defence, int id) {
		super(name, sprite, tooltip, id);
		this.defence = defence;
		this.type = type;
	}

	public Clothing copy() {
		Clothing copy = new Clothing(getName(), sprite, getToolTip(), type, getDefence(), getId());
		copy.setLocation(getX(), getY(), getZ());
		return copy;
	}

	ClothingType getType() {
		return type;
	}

	public float getDefence() {
		return defence;
	}

}
