package entity.item.weapon;

import java.util.Random;

public enum WeaponMaterial {
	WOOD,GOLD,IRON,BRONZE,CRYSTAL;
	
	public static WeaponMaterial getWeaponRandMat(Random r) {
		return WeaponMaterial.values()[r.nextInt(WeaponMaterial.values().length)];
	}

}
