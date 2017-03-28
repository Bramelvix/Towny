package entity.item.weapon;

import java.util.Random;

public enum WeaponType {
	SWORD, SPEAR, HALBERT, AXE, DAGGER, SCIMITAR, WARHAMMER, BOW, BUCKLER, HEATER, PICK;

	public static WeaponType getWeaponRandType(Random r) {
		return WeaponType.values()[r.nextInt(WeaponType.values().length)];
	}

}
