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

	public float getWeaponDamage() {
		return (weaponHand != null ? weaponHand.getDamage(wearer) : wearer.getMeleeDamage());
	}

	public void addClothing(Clothing item) {
		if (alreadyWearing(item))
			removeClothing(item.getType());
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
		if (alreadyHolding(item))
			removeWeapon(item);
		if (item.isShield()) {
			shieldHand = item;
		} else {
			weaponHand = item;
		}
	}

	public void removeWeapon(Weapon item) {
		wearer.dropItem(item.isShield() ? shieldHand : weaponHand);
		(item.isShield() ? shieldHand : weaponHand).setReservedVil(null);
		if (item.isShield()) {
			shieldHand = null;
		} else {
			weaponHand = null;
		}
	}

	private boolean alreadyHolding(Weapon item) {
		return ((item.isShield() && shieldHand != null) || (!(item.isShield()) && weaponHand != null));
	}

	private boolean alreadyWearing(Clothing item) {
		return ((item.getType() == ClothingType.HAT && hat != null)
				|| (item.getType() == ClothingType.SHIRT && shirt != null)
				|| (item.getType() == ClothingType.TROUSERS && trousers != null)
				|| (item.getType() == ClothingType.SHOES && shoes != null));
	}

	public void removeClothing(ClothingType type) {
		switch (type) {
		case HAT:
			wearer.dropItem(hat);
			hat.setReservedVil(null);
			hat = null;
			break;
		case SHIRT:
			wearer.dropItem(shirt);
			shirt.setReservedVil(null);
			shirt = null;
			break;
		case TROUSERS:
			wearer.dropItem(trousers);
			trousers.setReservedVil(null);
			trousers = null;
			break;
		case SHOES:
			wearer.dropItem(shoes);
			shoes.setReservedVil(null);
			shoes = null;
			break;
		}
	}

	public boolean isHoldingShield() {
		return shieldHand != null;
	}

	public boolean isHoldingWeapon() {
		return weaponHand != null;
	}

	public void dropAll() {
		wearer.dropItem(hat);
		hat.setReservedVil(null);
		hat = null;
		wearer.dropItem(shirt);
		shirt.setReservedVil(null);
		shirt = null;
		wearer.dropItem(trousers);
		trousers.setReservedVil(null);
		trousers = null;
		wearer.dropItem(shoes);
		shoes.setReservedVil(null);
		shoes = null;
		wearer.dropItem(weaponHand);
		weaponHand.setReservedVil(null);
		weaponHand = null;
		wearer.dropItem(shieldHand);
		shieldHand.setReservedVil(null);
		shieldHand = null;

	}

	public void update(int x, int y) {
		if (hat != null)
			hat.setLocation(x, y);
		if (shirt != null)
			shirt.setLocation(x, y);
		if (trousers != null)
			trousers.setLocation(x, y);
		if (shoes != null)
			shoes.setLocation(x, y);
		if (weaponHand != null)
			weaponHand.setLocation(x, y);
		if (shieldHand != null)
			shieldHand.setLocation(x, y);

	}

}
