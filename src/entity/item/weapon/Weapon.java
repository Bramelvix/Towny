package entity.item.weapon;

import entity.item.Item;
import entity.mob.Mob;
import entity.mob.Villager;
import graphics.Sprite;

public class Weapon extends Item {
	private float crush;
	private float pierce;
	private float cut;
	private float def;
	private float mat_strong;
	private float speed;
	private int range;
	private WeaponType type;
	private WeaponMaterial mat;

	private Weapon(String name, int x, int y, Sprite sprite, String tooltip, WeaponType type, WeaponMaterial mat) {
		super(name, x, y, sprite, tooltip, true);
		this.mat = mat;
		this.type = type;
		mat_strong = calcMatStrong(mat);
		calcDam(type);

	}

	public float getDamage(Villager user) {
		return mat_strong * cut * 10;
	}

	private void calcDam(WeaponType type) {
		switch (type) {
		case AXE:
			setStats(0.25f, 0f, 3f, 0f, 3f, 3);
			break;
		case BOW:
			setStats(0f, 3f, 0f, 0f, 2f, 10);
			break;
		case BUCKLER:
			setStats(0.75f, 0f, 0f, 4f, -0.75f, 1);
			break;
		case DAGGER:
			setStats(0f, 2f, 1f, 0f, 5f, 1);
			break;
		case HALBERT:
			setStats(0.5f, 1f, 5f, 0.5f, 1f, 5);
			break;
		case HEATER:
			setStats(1f, 0f, 0f, 5f, -1.5f, 1);
			break;
		case PICK:
			setStats(2f, 2.5f, 0f, 0f, 2f, 3);
			break;
		case SCIMITAR:
			setStats(0f, 1f, 5f, 0.5f, 4f, 3);
			break;
		case SPEAR:
			setStats(0.25f, 5f, 0.25f, 2f, 2.5f, 5);
			break;
		case SWORD:
			setStats(0.5f, 4f, 4f, 2.5f, 3f, 4);
			break;
		case WARHAMMER:
			setStats(5f, 1f, 2f, 0f, 1.5f, 3);
			break;
		}
	}

	private void setStats(float crush, float pierce, float cut, float def, float speed, int range) {
		this.crush = crush;
		this.pierce = pierce;
		this.cut = cut;
		this.def = def;
		this.speed = speed;
		this.range = range;
	}

	public static Weapon getRandomWeapon(int x, int y) {
		Weapon weapon = getRandomWeapon();
		weapon.setLocation(x, y);
		return weapon;
	}

	public static Weapon getRandomWeapon() {
		return getWeapon(WeaponType.getWeaponRandType(), WeaponMaterial.getWeaponRandMat());
	}

	public static Weapon getRandomWeapon(Mob a) {
		return getRandomWeapon(a.getX(), a.getY());
	}

	private float calcMatStrong(WeaponMaterial mat) {
		float strong = 0;
		switch (mat) {
		case WOOD:
			strong = 0.5f;
			break;
		case GOLD:
			strong = 1.25f;
			break;
		case IRON:
			strong = 1f;
			break;
		case COPPER:
			strong = 0.75f;
			break;
		case CRYSTAL:
			strong = 1.5f;
			break;
		}
		return strong;
	}

	public static Weapon getWeapon(WeaponType type, WeaponMaterial mat) {
		String name = "";
		switch (mat) {
		case WOOD:
			name = name.concat("Wooden ");
			break;
		case GOLD:
			name = name.concat("Golden ");
			break;
		case IRON:
			name = name.concat("Iron ");
			break;
		case COPPER:
			name = name.concat("Copper ");
			break;
		case CRYSTAL:
			name = name.concat("Crystal ");
			break;
		}
		if (type == WeaponType.BOW && mat == WeaponMaterial.COPPER)
			return getRandomWeapon();
		name = name.concat(type.toString().toLowerCase());
		String tooltip = "a " + name.toLowerCase();
		if (mat == WeaponMaterial.IRON)
			tooltip = "an " + name.toLowerCase();
		return new Weapon(name, 0, 0, Sprite.getWeaponSprite(type, mat), tooltip, type, mat);
	}

	public boolean isShield() {
		return type == WeaponType.BUCKLER || type == WeaponType.HEATER;
	}

	public Weapon copy() {
		return new Weapon(this.getName(), this.x, this.y, this.sprite, this.tooltip, this.type, this.mat);
	}

}
