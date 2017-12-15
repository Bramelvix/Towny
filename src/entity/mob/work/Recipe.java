package entity.mob.work;

import entity.item.Item;
import entity.item.weapon.Weapon;
import entity.item.weapon.WeaponMaterial;
import entity.item.weapon.WeaponType;
import entity.workstations.Anvil;
import entity.workstations.Furnace;
import entity.workstations.Workstation;
import graphics.Sprite;

public class Recipe {

	public static final Recipe IRON_BAR = new Recipe(new Item("iron bar", Sprite.ironBar, false), Furnace.class,
			"iron ore", "coal ore");
	public static final Recipe GOLD_BAR = new Recipe(new Item("gold bar", Sprite.goldBar, false), Furnace.class,
			"gold ore", "coal ore");
	public static final Recipe COPPER_BAR = new Recipe(new Item("copper bar", Sprite.copperBar, false), Furnace.class,
			"copper ore", "coal ore");
	public static final Recipe[] FURNACE_RECIPES = { IRON_BAR, GOLD_BAR, COPPER_BAR };
	public static final Recipe IRON_SWORD = new Recipe(Weapon.getWeapon(WeaponType.SWORD, WeaponMaterial.IRON),
			Anvil.class, "iron bar");
	public static final Recipe[] IRON_ANVIL_RECIPES = { IRON_SWORD };

	private String[] resources;
	private Item product;
	private Class<? extends Workstation> workstation;

	private Recipe(Item product, Class<? extends Workstation> workstation, String... resources) {
		this.product = product;
		this.workstation = workstation;
		this.resources = resources;
	}

	public String[] getResources() {
		return resources;
	}

	public Item getProduct() {
		return new Item(product);
	}

	public String getRecipeName() {
		return product.getName();
	}

	public Class<? extends Workstation> getWorkstationClass() {
		return workstation;
	}
}
