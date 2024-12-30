package entity.dynamic.mob.work.recipe;

import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.dynamic.item.ItemInfo;
import entity.dynamic.item.weapon.Weapon;
import entity.dynamic.item.weapon.WeaponMaterial;
import entity.nondynamic.building.container.Workstation;

import java.util.List;

public class ItemRecipe<T extends Item> extends Recipe {
	//TODO Maybe put these in a hashtable or file too

	private final ItemInfo<T> product;
	private final String name;

	public static final ItemRecipe<Item> IRON_BAR = new ItemRecipe<>(ItemHashtable.get(2, Item.class), Workstation.Type.FURNACE, List.of(ItemHashtable.get(5), ItemHashtable.get(8)));
	public static final ItemRecipe<Item> GOLD_BAR = new ItemRecipe<>(ItemHashtable.get(3, Item.class), Workstation.Type.FURNACE, List.of(ItemHashtable.get(7), ItemHashtable.get(8)));
	public static final ItemRecipe<Item> COPPER_BAR = new ItemRecipe<>(ItemHashtable.get(4, Item.class), Workstation.Type.FURNACE, List.of(ItemHashtable.get(6), ItemHashtable.get(8)));
	public static final List<ItemRecipe<Item>> FURNACE_RECIPES = List.of(IRON_BAR, GOLD_BAR, COPPER_BAR);
	private static final ItemRecipe<Weapon> IRON_SWORD = new ItemRecipe<>(ItemHashtable.get(44, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(2)));
	private static final ItemRecipe<Weapon> IRON_SPEAR = new ItemRecipe<>(ItemHashtable.get(43, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(2), ItemHashtable.get(2)));
	private static final ItemRecipe<Weapon> IRON_HALBERT = new ItemRecipe<>(ItemHashtable.get(40, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(2), ItemHashtable.get(2)));
	private static final ItemRecipe<Weapon> IRON_AXE = new ItemRecipe<>(ItemHashtable.get(37, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(2)));
	private static final ItemRecipe<Weapon> IRON_DAGGER = new ItemRecipe<>(ItemHashtable.get(39, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(2)));
	private static final ItemRecipe<Weapon> IRON_SCIMITAR = new ItemRecipe<>(ItemHashtable.get(42, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(2)));

	private static final ItemRecipe<Weapon> COPPER_SWORD = new ItemRecipe<>(ItemHashtable.get(36, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(4)));
	private static final ItemRecipe<Weapon> COPPER_SPEAR = new ItemRecipe<>(ItemHashtable.get(35, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(4), ItemHashtable.get(4)));
	private static final ItemRecipe<Weapon> COPPER_HALBERT = new ItemRecipe<>(ItemHashtable.get(32, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(4), ItemHashtable.get(4)));
	private static final ItemRecipe<Weapon> COPPER_AXE = new ItemRecipe<>(ItemHashtable.get(29, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(4)));
	private static final ItemRecipe<Weapon> COPPER_DAGGER = new ItemRecipe<>(ItemHashtable.get(31, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(4)));
	private static final ItemRecipe<Weapon> COPPER_SCIMITAR = new ItemRecipe<>(ItemHashtable.get(34, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(4)));

	private static final ItemRecipe<Weapon> GOLD_SWORD = new ItemRecipe<>(ItemHashtable.get(52, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(3)));
	private static final ItemRecipe<Weapon> GOLD_SPEAR = new ItemRecipe<>(ItemHashtable.get(51, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(3), ItemHashtable.get(3)));
	private static final ItemRecipe<Weapon> GOLD_HALBERT = new ItemRecipe<>(ItemHashtable.get(48, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(3), ItemHashtable.get(3)));
	private static final ItemRecipe<Weapon> GOLD_AXE = new ItemRecipe<>(ItemHashtable.get(45, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(3)));
	private static final ItemRecipe<Weapon> GOLD_DAGGER = new ItemRecipe<>(ItemHashtable.get(47, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(3)));
	private static final ItemRecipe<Weapon> GOLD_SCIMITAR = new ItemRecipe<>(ItemHashtable.get(50, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(3)));

	private static final ItemRecipe<Weapon> CRYSTAL_SWORD = new ItemRecipe<>(ItemHashtable.get(60, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(11)));
	private static final ItemRecipe<Weapon> CRYSTAL_SPEAR = new ItemRecipe<>(ItemHashtable.get(59, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(11), ItemHashtable.get(11)));
	private static final ItemRecipe<Weapon> CRYSTAL_HALBERT = new ItemRecipe<>(ItemHashtable.get(56, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(11), ItemHashtable.get(11)));
	private static final ItemRecipe<Weapon> CRYSTAL_AXE = new ItemRecipe<>(ItemHashtable.get(53, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(11)));
	private static final ItemRecipe<Weapon> CRYSTAL_DAGGER = new ItemRecipe<>(ItemHashtable.get(55, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(11)));
	private static final ItemRecipe<Weapon> CRYSTAL_SCIMITAR = new ItemRecipe<>(ItemHashtable.get(58, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(11)));

	private static final ItemRecipe<Weapon> WOOD_SWORD = new ItemRecipe<>(ItemHashtable.get(28, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(1)));
	private static final ItemRecipe<Weapon> WOOD_SPEAR = new ItemRecipe<>(ItemHashtable.get(27, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(1), ItemHashtable.get(1)));
	private static final ItemRecipe<Weapon> WOOD_HALBERT = new ItemRecipe<>(ItemHashtable.get(24, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(1), ItemHashtable.get(1)));
	private static final ItemRecipe<Weapon> WOOD_AXE = new ItemRecipe<>(ItemHashtable.get(20, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(1)));
	private static final ItemRecipe<Weapon> WOOD_DAGGER = new ItemRecipe<>(ItemHashtable.get(23, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(1)));
	private static final ItemRecipe<Weapon> WOOD_SCIMITAR = new ItemRecipe<>(ItemHashtable.get(26, Weapon.class), Workstation.Type.ANVIL, List.of(ItemHashtable.get(1)));

	private final Workstation.Type type;

	private ItemRecipe(ItemInfo<T> product, Workstation.Type type, List<ItemInfo<?>> resources) {
		super(resources);
		this.product = product;
		this.type = type;
		this.name = getProduct().getName();
	}

	public T getProduct() {
		return product.createInstance();
	}

	public static ItemRecipe[] smithingRecipesFromWeaponMaterial(WeaponMaterial type) {
		return switch (type) {
			case COPPER ->
					new ItemRecipe[]{COPPER_SWORD, COPPER_SPEAR, COPPER_HALBERT, COPPER_AXE, COPPER_DAGGER, COPPER_SCIMITAR};
			case IRON -> new ItemRecipe[]{IRON_SWORD, IRON_SPEAR, IRON_HALBERT, IRON_AXE, IRON_DAGGER, IRON_SCIMITAR};
			case GOLD -> new ItemRecipe[]{GOLD_SWORD, GOLD_SPEAR, GOLD_HALBERT, GOLD_AXE, GOLD_DAGGER, GOLD_SCIMITAR};
			case WOOD -> new ItemRecipe[]{WOOD_SWORD, WOOD_SPEAR, WOOD_HALBERT, WOOD_AXE, WOOD_DAGGER, WOOD_SCIMITAR};
			case CRYSTAL ->
					new ItemRecipe[]{CRYSTAL_SWORD, CRYSTAL_SPEAR, CRYSTAL_HALBERT, CRYSTAL_AXE, CRYSTAL_DAGGER, CRYSTAL_SCIMITAR};
		};
	}

	public Workstation.Type getWorkstationType() {
		return type;
	}

	@Override
	public String getRecipeName() {
		return name;
	}
}
