package entity.item.weapon;

import java.util.Random;

import entity.Entity;
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
	private WeaponType type;
	private WeaponMaterial mat;

	public Weapon(String name, int x, int y, Sprite sprite, String tooltip, WeaponType type, WeaponMaterial mat) {
		super(name, x, y, sprite, tooltip, true, 1);
		this.mat = mat;
		this.type = type;
		mat_strong = calcMatStrong(mat);
		calcDam(type);

	}

	public Weapon(Weapon e) {
		this(e.getName(), e.getX(), e.getY(), e.sprite, e.getToolTip(), e.type, e.mat);

	}

	public float getDamage(Villager user) {
		return mat_strong * cut * 10;
	}

	private void calcDam(WeaponType type) {
		switch (type) {
		case AXE:
			crush = 0.25f;
			pierce = 0f;
			cut = 3f;
			def = 0f;
			speed = 3f;
			break;
		case BOW:
			crush = 0f;
			pierce = 3f;
			cut = 0f;
			def = 0f;
			speed = 2f;
			break;
		case BUCKLER:
			crush = 0.75f;
			pierce = 0f;
			cut = 0f;
			def = 4f;
			speed = -0.75f;
			break;
		case DAGGER:
			crush = 0f;
			pierce = 2f;
			cut = 1f;
			def = 0f;
			speed = 5f;
			break;
		case HALBERT:
			crush = 0.5f;
			pierce = 1f;
			cut = 5f;
			def = 0.5f;
			speed = 1f;
			break;
		case HEATER:
			crush = 1f;
			pierce = 0f;
			cut = 0f;
			def = 5f;
			speed = -1.5f;
			break;
		case PICK:
			crush = 2f;
			pierce = 2.5f;
			cut = 0f;
			def = 0f;
			speed = 2f;
			break;
		case SCIMITAR:
			crush = 0f;
			pierce = 1f;
			cut = 5f;
			def = 0.5f;
			speed = 4f;
			break;
		case SPEAR:
			crush = 0.25f;
			pierce = 5f;
			cut = 0.25f;
			def = 2f;
			speed = 2.5f;
			break;
		case SWORD:
			crush = 0.5f;
			pierce = 4f;
			cut = 4f;
			def = 2.5f;
			speed = 3f;
			break;
		case WARHAMMER:
			crush = 5f;
			pierce = 1f;
			cut = 2f;
			def = 0f;
			speed = 1.5f;
			break;
		}
	}

	public static Weapon getRandomWeapon(int x, int y) {
		return getWeapon(WeaponType.getWeaponRandType(), WeaponMaterial.getWeaponRandMat(), x, y);
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
		case BRONZE:
			strong = 0.75f;
			break;
		case CRYSTAL:
			strong = 1.5f;
			break;
		}
		return strong;
	}

	public static Weapon getWeapon(WeaponType type, WeaponMaterial mat, int x, int y) {
		String name = "";
		switch (mat) {
		case WOOD:
			name.concat("Wooden ");
			break;
		case GOLD:
			name.concat("Golden ");
			break;
		case IRON:
			name.concat("Iron ");
			break;
		case BRONZE:
			name.concat("Bronze ");
			break;
		case CRYSTAL:
			name.concat("Crystal ");
			break;
		}
		if (type == WeaponType.BOW && mat == WeaponMaterial.BRONZE)
			return getRandomWeapon(x, y);
		name.concat(type.toString().toLowerCase());
		String tooltip = "a " + name.toLowerCase();
		if (mat == WeaponMaterial.IRON)
			tooltip = "an " + name.toLowerCase();
		return new Weapon(name, x, y, Sprite.getWeaponSprite(type, mat), tooltip, type, mat);
	}

	public boolean isShield() {
		return type == WeaponType.BUCKLER || type == WeaponType.HEATER;
	}

}
