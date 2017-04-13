package entity.item.weapon;


import entity.Entity;

public enum WeaponType {
	SWORD, SPEAR, HALBERT, AXE, DAGGER, SCIMITAR, WARHAMMER, BOW, BUCKLER, HEATER, PICK;

	public static WeaponType getWeaponRandType() {
		return WeaponType.values()[Entity.getRand().nextInt(WeaponType.values().length)];
	}

}
