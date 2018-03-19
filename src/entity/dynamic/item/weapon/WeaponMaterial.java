package entity.dynamic.item.weapon;

import entity.Entity;

public enum WeaponMaterial {
	WOOD,GOLD,IRON,COPPER,CRYSTAL;
	
	public static WeaponMaterial getWeaponRandMat() {
		return WeaponMaterial.values()[Entity.RANDOM.nextInt(WeaponMaterial.values().length)];
	}

}
