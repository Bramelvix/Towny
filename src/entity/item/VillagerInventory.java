package entity.item;

import entity.item.weapon.Weapon;
import entity.mob.Villager;
import graphics.Screen;

public class VillagerInventory {
	private Clothing hat;
	private Clothing shirt;
	private Clothing trousers;
	private Clothing shoes;
	private Weapon weaponHand;
	private Weapon shieldHand;
	private Villager wearer;

	public VillagerInventory(Villager wearer) {
		this.wearer = wearer;
	}

	public void render(Screen screen) {
		if (hat != null)
			hat.render(screen);
		if (shirt != null)
			shirt.render(screen);
		if (trousers != null)
			trousers.render(screen);
		if (shoes != null)
			shoes.render(screen);
		if (weaponHand != null)
			weaponHand.render(screen);
		if (shieldHand != null)
			shieldHand.render(screen);
	}

	public void addClothing(Clothing item) {
		switch (item.getType()) {
		case HAT:
			hat = item;
			break;
		case SHIRT:
			shirt = item;
			break;
		case TROUSERS:
			trousers = item;
			break;
		case SHOES:
			shoes = item;
		}
	}

	public void addWeapon(Weapon item) {
		if (item.isShield()) {
			shieldHand = item;
		} else {
			weaponHand = item;
		}
	}

	public void removeWeapon(boolean mainHand) {
		if (mainHand) {
			dropItem(weaponHand);
		} else {
			dropItem(shieldHand);
		}
	}

	public void removeClothing(ClothingType type) {
		switch (type) {
		case HAT:
			dropItem(hat);
			break;
		case SHIRT:
			dropItem(shirt);
			break;
		case TROUSERS:
			dropItem(trousers);
			break;
		case SHOES:
			dropItem(trousers);
		}
	}

	private void dropItem(Item item) {
		wearer.dropItem(item);

	}

	public void update(int x, int y) {
		if (hat != null) {
			hat.setX(x);
			hat.setY(y);
		}
		if (shirt != null) {
			shirt.setX(x);
			shirt.setY(y);
		}
		if (trousers != null) {
			trousers.setX(x);
			trousers.setY(y);
		}
		if (shoes != null) {
			shoes.setX(x);
			shoes.setY(y);
		}
		if (weaponHand != null) {
			weaponHand.setX(x);
			weaponHand.setY(y);
		}
		if (shieldHand != null) {
			shieldHand.setX(x);
			shieldHand.setY(y);
		}
	}

}
