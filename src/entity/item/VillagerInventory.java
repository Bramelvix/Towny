package entity.item;

import entity.item.weapon.Weapon;
import entity.mob.Villager;
import graphics.Screen;

public class VillagerInventory {
	private Clothing[] clothes;
	private Weapon[] weapons;
	private Villager wearer;

	public VillagerInventory(Villager wearer) {
		this.wearer = wearer;
		clothes = new Clothing[4]; // 0 = hat, 1 = shirt, 2 = trousers, 3 =
									// shoes
		weapons = new Weapon[2]; // 0 = weaponhand, 1 = shieldhand
	}

	public void render(Screen screen) {
		for (Clothing i : clothes) {
			if (i != null) {
				i.render(screen);
			}
		}
		for (Weapon i : weapons) {
			if (i != null) {
				i.render(screen);
			}
		}
	}

	public float getWeaponDamage() {
		return (weapons[0] != null ? weapons[0].getDamage(wearer) : wearer.getMeleeDamage());
	}

	public void addClothing(Clothing item) {
		if (alreadyWearing(item)) {
			removeClothing(item.getType());
		}
		switch (item.getType()) {
		case HAT:
			clothes[0] = item;
			break;
		case SHIRT:
			clothes[1] = item;
			break;
		case TROUSERS:
			clothes[2] = item;
			break;
		case SHOES:
			clothes[3] = item;
		}
	}

	public void addWeapon(Weapon item) {
		if (alreadyHolding(item)) {
			removeWeapon(item);
		}
		weapons[item.isShield() ? 1 : 0] = item;
	}

	public void removeWeapon(Weapon item) {
		int num = item.isShield() ? 1 : 0;
		wearer.dropItem(weapons[num]);
		weapons[num].removeReserved();
		weapons[num] = null;
	}

	private boolean alreadyHolding(Weapon item) {
		return ((item.isShield() && weapons[1] != null) || (!(item.isShield()) && weapons[0] != null));
	}

	private boolean alreadyWearing(Clothing item) {
		return ((item.getType() == ClothingType.HAT && clothes[0] != null)
				|| (item.getType() == ClothingType.SHIRT && clothes[1] != null)
				|| (item.getType() == ClothingType.TROUSERS && clothes[2] != null)
				|| (item.getType() == ClothingType.SHOES && clothes[3] != null));
	}

	public void removeClothing(ClothingType type) {
		switch (type) {
		case HAT:
			wearer.dropItem(clothes[0]);
			clothes[0].removeReserved();
			clothes[0] = null;
			break;
		case SHIRT:
			wearer.dropItem(clothes[1]);
			clothes[1].removeReserved();
			clothes[1] = null;
			break;
		case TROUSERS:
			wearer.dropItem(clothes[2]);
			clothes[2].removeReserved();
			clothes[2] = null;
			break;
		case SHOES:
			wearer.dropItem(clothes[3]);
			clothes[3].removeReserved();
			clothes[3] = null;
			break;
		}
	}

	public boolean isHoldingShield() {
		return weapons[1] != null;
	}

	public boolean isHoldingWeapon() {
		return weapons[0] != null;
	}

	public void dropAll() {
		for (Clothing i : clothes) {
			wearer.dropItem(i);
			i.removeReserved();
			i = null;
		}
		for (Weapon i : weapons) {
			wearer.dropItem(i);
			i.removeReserved();
			i = null;
		}

	}

	public void update(int x, int y) {
		for (Clothing i : clothes) {
			if (i != null) {
				i.setLocation(x, y);
			}
		}
		for (Weapon i : weapons) {
			if (i != null) {
				i.setLocation(x, y);
			}
		}
	}

}
