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

	public static final Recipe IRON_BAR = new Recipe(new String[] { "iron ore", "coal ore" },
			new Item("iron bar", Sprite.ironBar, false), Furnace.class);
	public static final Recipe GOLD_BAR = new Recipe(new String[] { "gold ore", "coal ore" },
			new Item("gold bar", Sprite.goldBar, false), Furnace.class);
	public static final Recipe[] FURNACE_RECIPES = { IRON_BAR, GOLD_BAR };
	public static final Recipe IRON_SWORD = new Recipe("iron bar",
			Weapon.getWeapon(WeaponType.SWORD, WeaponMaterial.IRON), Anvil.class);
	public static final Recipe[] IRON_ANVIL_RECIPES = { IRON_SWORD };

	public String[] resources;
	private Item product;
	private Class<? extends Workstation> workstation;

	private Recipe(String resource, Item product, Class<? extends Workstation> workstation) {
		this(product, workstation);
		this.resources = new String[] { resource };
	}

	private Recipe(Item product, Class<? extends Workstation> workstation) {
		this.product = product;
		this.workstation = workstation;
	}

	private Recipe(String resources[], Item product, Class<? extends Workstation> workstation) {
		this(product, workstation);
		this.resources = resources;
	}

	public String[] getResources() {
		return resources;
	}

	public Item getProduct() {
		return product;
	}

	public String getRecipeName() {
		return product.getName();
	}

	public Class<? extends Workstation> getWorkstationClass() {
		return workstation;
	}
}
