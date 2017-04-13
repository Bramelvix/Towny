package entity.item.weapon;

import entity.Entity;

public enum WeaponMaterial {
	WOOD,GOLD,IRON,BRONZE,CRYSTAL;
	
	public static WeaponMaterial getWeaponRandMat() {
		return WeaponMaterial.values()[Entity.getRand().nextInt(WeaponMaterial.values().length)];
	}

}
