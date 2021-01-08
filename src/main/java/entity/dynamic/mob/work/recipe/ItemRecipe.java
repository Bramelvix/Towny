package entity.dynamic.mob.work.recipe;

import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.dynamic.item.weapon.WeaponMaterial;
import entity.nonDynamic.building.container.Workstation;

public class ItemRecipe extends Recipe {
	//TODO Maybe put these in a hashtable or file too

	public static final ItemRecipe IRON_BAR = new ItemRecipe(ItemHashtable.get(2), Workstation.Type.FURNACE, ItemHashtable.get(5), ItemHashtable.get(8));
	public static final ItemRecipe GOLD_BAR = new ItemRecipe(ItemHashtable.get(3), Workstation.Type.FURNACE, ItemHashtable.get(7), ItemHashtable.get(8));
	public static final ItemRecipe COPPER_BAR = new ItemRecipe(ItemHashtable.get(4), Workstation.Type.FURNACE, ItemHashtable.get(6), ItemHashtable.get(8));
	public static final ItemRecipe[] FURNACE_RECIPES = {IRON_BAR, GOLD_BAR, COPPER_BAR};
	private static final ItemRecipe IRON_SWORD = new ItemRecipe(ItemHashtable.get(44), Workstation.Type.ANVIL, ItemHashtable.get(2));
	private static final ItemRecipe IRON_SPEAR = new ItemRecipe(ItemHashtable.get(43), Workstation.Type.ANVIL, ItemHashtable.get(2), ItemHashtable.get(2));
	private static final ItemRecipe IRON_HALBERT = new ItemRecipe(ItemHashtable.get(40), Workstation.Type.ANVIL, ItemHashtable.get(2), ItemHashtable.get(2));
	private static final ItemRecipe IRON_AXE = new ItemRecipe(ItemHashtable.get(37), Workstation.Type.ANVIL, ItemHashtable.get(2));
	private static final ItemRecipe IRON_DAGGER = new ItemRecipe(ItemHashtable.get(39), Workstation.Type.ANVIL, ItemHashtable.get(2));
	private static final ItemRecipe IRON_SCIMITAR = new ItemRecipe(ItemHashtable.get(42), Workstation.Type.ANVIL, ItemHashtable.get(2));

	private static final ItemRecipe COPPER_SWORD = new ItemRecipe(ItemHashtable.get(36), Workstation.Type.ANVIL, ItemHashtable.get(4));
	private static final ItemRecipe COPPER_SPEAR = new ItemRecipe(ItemHashtable.get(35), Workstation.Type.ANVIL, ItemHashtable.get(4), ItemHashtable.get(4));
	private static final ItemRecipe COPPER_HALBERT = new ItemRecipe(ItemHashtable.get(32), Workstation.Type.ANVIL, ItemHashtable.get(4), ItemHashtable.get(4));
	private static final ItemRecipe COPPER_AXE = new ItemRecipe(ItemHashtable.get(29), Workstation.Type.ANVIL, ItemHashtable.get(4));
	private static final ItemRecipe COPPER_DAGGER = new ItemRecipe(ItemHashtable.get(31), Workstation.Type.ANVIL, ItemHashtable.get(4));
	private static final ItemRecipe COPPER_SCIMITAR = new ItemRecipe(ItemHashtable.get(34), Workstation.Type.ANVIL, ItemHashtable.get(4));

	private static final ItemRecipe GOLD_SWORD = new ItemRecipe(ItemHashtable.get(52), Workstation.Type.ANVIL, ItemHashtable.get(3));
	private static final ItemRecipe GOLD_SPEAR = new ItemRecipe(ItemHashtable.get(51), Workstation.Type.ANVIL, ItemHashtable.get(3), ItemHashtable.get(3));
	private static final ItemRecipe GOLD_HALBERT = new ItemRecipe(ItemHashtable.get(48), Workstation.Type.ANVIL, ItemHashtable.get(3), ItemHashtable.get(3));
	private static final ItemRecipe GOLD_AXE = new ItemRecipe(ItemHashtable.get(45), Workstation.Type.ANVIL, ItemHashtable.get(3));
	private static final ItemRecipe GOLD_DAGGER = new ItemRecipe(ItemHashtable.get(47), Workstation.Type.ANVIL, ItemHashtable.get(3));
	private static final ItemRecipe GOLD_SCIMITAR = new ItemRecipe(ItemHashtable.get(50), Workstation.Type.ANVIL, ItemHashtable.get(3));

	private static final ItemRecipe CRYSTAL_SWORD = new ItemRecipe(ItemHashtable.get(60), Workstation.Type.ANVIL, ItemHashtable.get(11));
	private static final ItemRecipe CRYSTAL_SPEAR = new ItemRecipe(ItemHashtable.get(59), Workstation.Type.ANVIL, ItemHashtable.get(11), ItemHashtable.get(11));
	private static final ItemRecipe CRYSTAL_HALBERT = new ItemRecipe(ItemHashtable.get(56), Workstation.Type.ANVIL, ItemHashtable.get(11), ItemHashtable.get(11));
	private static final ItemRecipe CRYSTAL_AXE = new ItemRecipe(ItemHashtable.get(53), Workstation.Type.ANVIL, ItemHashtable.get(11));
	private static final ItemRecipe CRYSTAL_DAGGER = new ItemRecipe(ItemHashtable.get(55), Workstation.Type.ANVIL, ItemHashtable.get(11));
	private static final ItemRecipe CRYSTAL_SCIMITAR = new ItemRecipe(ItemHashtable.get(58), Workstation.Type.ANVIL, ItemHashtable.get(11));

	private static final ItemRecipe WOOD_SWORD = new ItemRecipe(ItemHashtable.get(28), Workstation.Type.ANVIL, ItemHashtable.get(1));
	private static final ItemRecipe WOOD_SPEAR = new ItemRecipe(ItemHashtable.get(27), Workstation.Type.ANVIL, ItemHashtable.get(1), ItemHashtable.get(1));
	private static final ItemRecipe WOOD_HALBERT = new ItemRecipe(ItemHashtable.get(24), Workstation.Type.ANVIL, ItemHashtable.get(1), ItemHashtable.get(1));
	private static final ItemRecipe WOOD_AXE = new ItemRecipe(ItemHashtable.get(20), Workstation.Type.ANVIL, ItemHashtable.get(1));
	private static final ItemRecipe WOOD_DAGGER = new ItemRecipe(ItemHashtable.get(23), Workstation.Type.ANVIL, ItemHashtable.get(1));
	private static final ItemRecipe WOOD_SCIMITAR = new ItemRecipe(ItemHashtable.get(26), Workstation.Type.ANVIL, ItemHashtable.get(1));

	private final Workstation.Type type;

	private <T extends Item> ItemRecipe(T product, Workstation.Type type, Item... resources) {
		super(product, resources);
		this.type = type;
	}

	public <T extends Item> T getProduct() {
		return (T) ((T)product).copy(); //TODO yeahhhhh
	}

	public static ItemRecipe[] smithingRecipesFromWeaponMaterial(WeaponMaterial type) {
		return switch (type) {
			case COPPER -> new ItemRecipe[]{COPPER_SWORD, COPPER_SPEAR, COPPER_HALBERT, COPPER_AXE, COPPER_DAGGER, COPPER_SCIMITAR};
			case IRON -> new ItemRecipe[]{IRON_SWORD, IRON_SPEAR, IRON_HALBERT, IRON_AXE, IRON_DAGGER, IRON_SCIMITAR};
			case GOLD -> new ItemRecipe[]{GOLD_SWORD, GOLD_SPEAR, GOLD_HALBERT, GOLD_AXE, GOLD_DAGGER, GOLD_SCIMITAR};
			case WOOD -> new ItemRecipe[]{WOOD_SWORD, WOOD_SPEAR, WOOD_HALBERT, WOOD_AXE, WOOD_DAGGER, WOOD_SCIMITAR};
			case CRYSTAL -> new ItemRecipe[]{CRYSTAL_SWORD, CRYSTAL_SPEAR, CRYSTAL_HALBERT, CRYSTAL_AXE, CRYSTAL_DAGGER, CRYSTAL_SCIMITAR};
		};
	}

	public Workstation.Type getWorkstationType() {
		return type;
	}

}
