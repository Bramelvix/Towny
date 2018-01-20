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
	private static final ItemRecipe IRON_SWORD = new ItemRecipe(Weapon.getWeapon(WeaponType.SWORD, WeaponMaterial.IRON),
			Anvil.class, "iron bar");
	private static final ItemRecipe IRON_SPEAR = new ItemRecipe(Weapon.getWeapon(WeaponType.SPEAR, WeaponMaterial.IRON),
			Anvil.class, "iron bar", "iron bar");
	private static final ItemRecipe IRON_HALBERT = new ItemRecipe(
			Weapon.getWeapon(WeaponType.HALBERT, WeaponMaterial.IRON), Anvil.class, "iron bar", "iron bar");
	private static final ItemRecipe IRON_AXE = new ItemRecipe(Weapon.getWeapon(WeaponType.AXE, WeaponMaterial.IRON),
			Anvil.class, "iron bar");
	private static final ItemRecipe IRON_DAGGER = new ItemRecipe(
			Weapon.getWeapon(WeaponType.DAGGER, WeaponMaterial.IRON), Anvil.class, "iron bar");
	private static final ItemRecipe IRON_SCIMITAR = new ItemRecipe(
			Weapon.getWeapon(WeaponType.SCIMITAR, WeaponMaterial.IRON), Anvil.class, "iron bar");
	private static final ItemRecipe IRON_WARHAMMER = new ItemRecipe(
			Weapon.getWeapon(WeaponType.WARHAMMER, WeaponMaterial.IRON), Anvil.class, "iron bar");
	private static final ItemRecipe IRON_PICK = new ItemRecipe(Weapon.getWeapon(WeaponType.PICK, WeaponMaterial.IRON),
			Anvil.class, "iron bar");

	private static final ItemRecipe COPPER_SWORD = new ItemRecipe(
			Weapon.getWeapon(WeaponType.SWORD, WeaponMaterial.COPPER), Anvil.class, "copper bar");
	private static final ItemRecipe COPPER_SPEAR = new ItemRecipe(
			Weapon.getWeapon(WeaponType.SPEAR, WeaponMaterial.COPPER), Anvil.class, "copper bar", "copper bar");
	private static final ItemRecipe COPPER_HALBERT = new ItemRecipe(
			Weapon.getWeapon(WeaponType.HALBERT, WeaponMaterial.COPPER), Anvil.class, "copper bar", "copper bar");
	private static final ItemRecipe COPPER_AXE = new ItemRecipe(Weapon.getWeapon(WeaponType.AXE, WeaponMaterial.COPPER),
			Anvil.class, "bronze bar");
	private static final ItemRecipe COPPER_DAGGER = new ItemRecipe(
			Weapon.getWeapon(WeaponType.DAGGER, WeaponMaterial.COPPER), Anvil.class, "copper bar");
	private static final ItemRecipe COPPER_SCIMITAR = new ItemRecipe(
			Weapon.getWeapon(WeaponType.SCIMITAR, WeaponMaterial.COPPER), Anvil.class, "copper bar");
	private static final ItemRecipe COPPER_WARHAMMER = new ItemRecipe(
			Weapon.getWeapon(WeaponType.WARHAMMER, WeaponMaterial.COPPER), Anvil.class, "copper bar");
	private static final ItemRecipe COPPER_PICK = new ItemRecipe(
			Weapon.getWeapon(WeaponType.PICK, WeaponMaterial.COPPER), Anvil.class, "copper bar");

	private static final ItemRecipe GOLD_SWORD = new ItemRecipe(Weapon.getWeapon(WeaponType.SWORD, WeaponMaterial.GOLD),
			Anvil.class, "gold bar");
	private static final ItemRecipe GOLD_SPEAR = new ItemRecipe(Weapon.getWeapon(WeaponType.SPEAR, WeaponMaterial.GOLD),
			Anvil.class, "gold bar", "gold bar");
	private static final ItemRecipe GOLD_HALBERT = new ItemRecipe(
			Weapon.getWeapon(WeaponType.HALBERT, WeaponMaterial.GOLD), Anvil.class, "gold bar", "gold bar");
	private static final ItemRecipe GOLD_AXE = new ItemRecipe(Weapon.getWeapon(WeaponType.AXE, WeaponMaterial.GOLD),
			Anvil.class, "gold bar");
	private static final ItemRecipe GOLD_DAGGER = new ItemRecipe(
			Weapon.getWeapon(WeaponType.DAGGER, WeaponMaterial.GOLD), Anvil.class, "gold bar");
	private static final ItemRecipe GOLD_SCIMITAR = new ItemRecipe(
			Weapon.getWeapon(WeaponType.SCIMITAR, WeaponMaterial.GOLD), Anvil.class, "gold bar");
	private static final ItemRecipe GOLD_WARHAMMER = new ItemRecipe(
			Weapon.getWeapon(WeaponType.WARHAMMER, WeaponMaterial.GOLD), Anvil.class, "gold bar");
	private static final ItemRecipe GOLD_PICK = new ItemRecipe(Weapon.getWeapon(WeaponType.PICK, WeaponMaterial.GOLD),
			Anvil.class, "gold bar");

	private static final ItemRecipe CRYSTAL_SWORD = new ItemRecipe(
			Weapon.getWeapon(WeaponType.SWORD, WeaponMaterial.CRYSTAL), Anvil.class, "cut crystal");
	private static final ItemRecipe CRYSTAL_SPEAR = new ItemRecipe(
			Weapon.getWeapon(WeaponType.SPEAR, WeaponMaterial.CRYSTAL), Anvil.class, "cut crystal", "cut crystal");
	private static final ItemRecipe CRYSTAL_HALBERT = new ItemRecipe(
			Weapon.getWeapon(WeaponType.HALBERT, WeaponMaterial.CRYSTAL), Anvil.class, "cut crystal", "cut crystal");
	private static final ItemRecipe CRYSTAL_AXE = new ItemRecipe(
			Weapon.getWeapon(WeaponType.AXE, WeaponMaterial.CRYSTAL), Anvil.class, "cut crystal");
	private static final ItemRecipe CRYSTAL_DAGGER = new ItemRecipe(
			Weapon.getWeapon(WeaponType.DAGGER, WeaponMaterial.CRYSTAL), Anvil.class, "cut crystal");
	private static final ItemRecipe CRYSTAL_SCIMITAR = new ItemRecipe(
			Weapon.getWeapon(WeaponType.SCIMITAR, WeaponMaterial.CRYSTAL), Anvil.class, "cut crystal");
	private static final ItemRecipe CRYSTAL_WARHAMMER = new ItemRecipe(
			Weapon.getWeapon(WeaponType.WARHAMMER, WeaponMaterial.CRYSTAL), Anvil.class, "cut crystal");
	private static final ItemRecipe CRYSTAL_PICK = new ItemRecipe(
			Weapon.getWeapon(WeaponType.PICK, WeaponMaterial.CRYSTAL), Anvil.class, "cut crystal");

	private static final ItemRecipe WOOD_SWORD = new ItemRecipe(Weapon.getWeapon(WeaponType.SWORD, WeaponMaterial.WOOD),
			Anvil.class, "logs");
	private static final ItemRecipe WOOD_SPEAR = new ItemRecipe(Weapon.getWeapon(WeaponType.SPEAR, WeaponMaterial.WOOD),
			Anvil.class, "logs", "logs");
	private static final ItemRecipe WOOD_HALBERT = new ItemRecipe(
			Weapon.getWeapon(WeaponType.HALBERT, WeaponMaterial.WOOD), Anvil.class, "logs", "logs");
	private static final ItemRecipe WOOD_AXE = new ItemRecipe(Weapon.getWeapon(WeaponType.AXE, WeaponMaterial.WOOD),
			Anvil.class, "logs");
	private static final ItemRecipe WOOD_DAGGER = new ItemRecipe(
			Weapon.getWeapon(WeaponType.DAGGER, WeaponMaterial.WOOD), Anvil.class, "logs");
	private static final ItemRecipe WOOD_SCIMITAR = new ItemRecipe(
			Weapon.getWeapon(WeaponType.SCIMITAR, WeaponMaterial.WOOD), Anvil.class, "logs");
	private static final ItemRecipe WOOD_WARHAMMER = new ItemRecipe(
			Weapon.getWeapon(WeaponType.WARHAMMER, WeaponMaterial.WOOD), Anvil.class, "logs");
	private static final ItemRecipe WOOD_PICK = new ItemRecipe(Weapon.getWeapon(WeaponType.PICK, WeaponMaterial.WOOD),
			Anvil.class, "logs");

	private Class<? extends Workstation> workstation;

	private <T extends Item> ItemRecipe(T product, Class<? extends Workstation> workstation, String... resources) {
		super(product, resources);
		this.workstation = workstation;
	}

	public <T extends Item> Item getProduct() {
		return ((Item) product).copy();
	}

	public static ItemRecipe[] smithingRecipesFromWeaponMaterial(WeaponMaterial type) {
		ItemRecipe[] recipes = null;
		switch (type) {
		case COPPER:
			recipes = new ItemRecipe[] { COPPER_SWORD, COPPER_SPEAR, COPPER_HALBERT, COPPER_AXE, COPPER_DAGGER,
					COPPER_SCIMITAR, COPPER_WARHAMMER, COPPER_PICK };
			break;

		case IRON:
			recipes = new ItemRecipe[] { IRON_SWORD, IRON_SPEAR, IRON_HALBERT, IRON_AXE, IRON_DAGGER, IRON_SCIMITAR,
					IRON_WARHAMMER, IRON_PICK };
			break;
		case GOLD:
			recipes = new ItemRecipe[] { GOLD_SWORD, GOLD_SPEAR, GOLD_HALBERT, GOLD_AXE, GOLD_DAGGER, GOLD_SCIMITAR,
					GOLD_WARHAMMER, GOLD_PICK };
			break;
		case WOOD:
			recipes = new ItemRecipe[] { WOOD_SWORD, WOOD_SPEAR, WOOD_HALBERT, WOOD_AXE, WOOD_DAGGER, WOOD_SCIMITAR,
					WOOD_WARHAMMER, WOOD_PICK };
			break;
		case CRYSTAL:
			recipes = new ItemRecipe[] { CRYSTAL_SWORD, CRYSTAL_SPEAR, CRYSTAL_HALBERT, CRYSTAL_AXE, CRYSTAL_DAGGER,
					CRYSTAL_SCIMITAR, CRYSTAL_WARHAMMER, CRYSTAL_PICK };
			break;
		}
		return recipes;
	}

	public Class<? extends Workstation> getWorkstationClass() {
		return workstation;
	}
}
