package entity.mob.work;

import entity.item.Item;
import entity.item.weapon.Weapon;
import entity.item.weapon.WeaponMaterial;
import entity.item.weapon.WeaponType;
import entity.workstations.Anvil;
import entity.workstations.Furnace;
import entity.workstations.Workstation;
import graphics.Sprite;

public class ItemRecipe extends Recipe {

	public static final ItemRecipe IRON_BAR = new ItemRecipe(new Item("iron bar", Sprite.ironBar, false), Furnace.class,
			"iron ore", "coal ore");
	public static final ItemRecipe GOLD_BAR = new ItemRecipe(new Item("gold bar", Sprite.goldBar, false), Furnace.class,
			"gold ore", "coal ore");
	public static final ItemRecipe COPPER_BAR = new ItemRecipe(new Item("copper bar", Sprite.copperBar, false),
			Furnace.class, "copper ore", "coal ore");
	public static final ItemRecipe[] FURNACE_RECIPES = { IRON_BAR, GOLD_BAR, COPPER_BAR };
	public static final ItemRecipe IRON_SWORD = new ItemRecipe(Weapon.getWeapon(WeaponType.SWORD, WeaponMaterial.IRON),
			Anvil.class, "iron bar");
	public static final ItemRecipe[] IRON_ANVIL_RECIPES = { IRON_SWORD };

	private Class<? extends Workstation> workstation;

	private <T extends Item> ItemRecipe(T product, Class<? extends Workstation> workstation, String... resources) {
		super(product, resources);
		this.workstation = workstation;
	}

	public <T extends Item> Item getProduct() {
		return ((Item) product).copy();
	}

	public Class<? extends Workstation> getWorkstationClass() {
		return workstation;
	}
}
