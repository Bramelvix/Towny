package entity.dynamic.mob.work;

import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.dynamic.item.weapon.WeaponMaterial;
import entity.nonDynamic.building.container.workstations.Anvil;
import entity.nonDynamic.building.container.workstations.Furnace;
import entity.nonDynamic.building.container.workstations.Workstation;

public class ItemRecipe extends Recipe {

	public static final ItemRecipe IRON_BAR = new ItemRecipe(ItemHashtable.get(2), Furnace.class, ItemHashtable.get(5), ItemHashtable.get(8));
	public static final ItemRecipe GOLD_BAR = new ItemRecipe(ItemHashtable.get(3), Furnace.class, ItemHashtable.get(7), ItemHashtable.get(8));
	public static final ItemRecipe COPPER_BAR = new ItemRecipe(ItemHashtable.get(4), Furnace.class, ItemHashtable.get(6), ItemHashtable.get(8));
	public static final ItemRecipe[] FURNACE_RECIPES = {IRON_BAR, GOLD_BAR, COPPER_BAR};
	private static final ItemRecipe IRON_SWORD = new ItemRecipe(ItemHashtable.get(44), Anvil.class, ItemHashtable.get(2));
	private static final ItemRecipe IRON_SPEAR = new ItemRecipe(ItemHashtable.get(43), Anvil.class, ItemHashtable.get(2), ItemHashtable.get(2));
	private static final ItemRecipe IRON_HALBERT = new ItemRecipe(ItemHashtable.get(40), Anvil.class, ItemHashtable.get(2), ItemHashtable.get(2));
	private static final ItemRecipe IRON_AXE = new ItemRecipe(ItemHashtable.get(37), Anvil.class, ItemHashtable.get(2));
	private static final ItemRecipe IRON_DAGGER = new ItemRecipe(ItemHashtable.get(39), Anvil.class, ItemHashtable.get(2));
	private static final ItemRecipe IRON_SCIMITAR = new ItemRecipe(ItemHashtable.get(42), Anvil.class, ItemHashtable.get(2));

	private static final ItemRecipe COPPER_SWORD = new ItemRecipe(ItemHashtable.get(36), Anvil.class, ItemHashtable.get(4));
	private static final ItemRecipe COPPER_SPEAR = new ItemRecipe(ItemHashtable.get(35), Anvil.class, ItemHashtable.get(4), ItemHashtable.get(4));
	private static final ItemRecipe COPPER_HALBERT = new ItemRecipe(ItemHashtable.get(32), Anvil.class, ItemHashtable.get(4), ItemHashtable.get(4));
	private static final ItemRecipe COPPER_AXE = new ItemRecipe(ItemHashtable.get(29), Anvil.class, ItemHashtable.get(4));
	private static final ItemRecipe COPPER_DAGGER = new ItemRecipe(ItemHashtable.get(31), Anvil.class, ItemHashtable.get(4));
	private static final ItemRecipe COPPER_SCIMITAR = new ItemRecipe(ItemHashtable.get(34), Anvil.class, ItemHashtable.get(4));

	private static final ItemRecipe GOLD_SWORD = new ItemRecipe(ItemHashtable.get(52), Anvil.class, ItemHashtable.get(3));
	private static final ItemRecipe GOLD_SPEAR = new ItemRecipe(ItemHashtable.get(51), Anvil.class, ItemHashtable.get(3), ItemHashtable.get(3));
	private static final ItemRecipe GOLD_HALBERT = new ItemRecipe(ItemHashtable.get(48), Anvil.class, ItemHashtable.get(3), ItemHashtable.get(3));
	private static final ItemRecipe GOLD_AXE = new ItemRecipe(ItemHashtable.get(45), Anvil.class, ItemHashtable.get(3));
	private static final ItemRecipe GOLD_DAGGER = new ItemRecipe(ItemHashtable.get(47), Anvil.class, ItemHashtable.get(3));
	private static final ItemRecipe GOLD_SCIMITAR = new ItemRecipe(ItemHashtable.get(50), Anvil.class, ItemHashtable.get(3));

	private static final ItemRecipe CRYSTAL_SWORD = new ItemRecipe(ItemHashtable.get(60), Anvil.class, ItemHashtable.get(11));
	private static final ItemRecipe CRYSTAL_SPEAR = new ItemRecipe(ItemHashtable.get(59), Anvil.class, ItemHashtable.get(11), ItemHashtable.get(11));
	private static final ItemRecipe CRYSTAL_HALBERT = new ItemRecipe(ItemHashtable.get(56), Anvil.class, ItemHashtable.get(11), ItemHashtable.get(11));
	private static final ItemRecipe CRYSTAL_AXE = new ItemRecipe(ItemHashtable.get(53), Anvil.class, ItemHashtable.get(11));
	private static final ItemRecipe CRYSTAL_DAGGER = new ItemRecipe(ItemHashtable.get(55), Anvil.class, ItemHashtable.get(11));
	private static final ItemRecipe CRYSTAL_SCIMITAR = new ItemRecipe(ItemHashtable.get(58), Anvil.class, ItemHashtable.get(11));

	private static final ItemRecipe WOOD_SWORD = new ItemRecipe(ItemHashtable.get(28), Anvil.class, ItemHashtable.get(1));
	private static final ItemRecipe WOOD_SPEAR = new ItemRecipe(ItemHashtable.get(27), Anvil.class, ItemHashtable.get(1), ItemHashtable.get(1));
	private static final ItemRecipe WOOD_HALBERT = new ItemRecipe(ItemHashtable.get(24), Anvil.class, ItemHashtable.get(1), ItemHashtable.get(1));
	private static final ItemRecipe WOOD_AXE = new ItemRecipe(ItemHashtable.get(20), Anvil.class, ItemHashtable.get(1));
	private static final ItemRecipe WOOD_DAGGER = new ItemRecipe(ItemHashtable.get(23), Anvil.class, ItemHashtable.get(1));
	private static final ItemRecipe WOOD_SCIMITAR = new ItemRecipe(ItemHashtable.get(26), Anvil.class, ItemHashtable.get(1));

	private Class<? extends Workstation> workstation;

	private <T extends Item> ItemRecipe(T product, Class<? extends Workstation> workstation, Item... resources) {
		super(product, resources);
		this.workstation = workstation;
	}

	public <T extends Item> T getProduct() {
		return (T) ((T)product).copy(); //TODO yeahhhhh
	}

	public static ItemRecipe[] smithingRecipesFromWeaponMaterial(WeaponMaterial type) {
		ItemRecipe[] recipes = null;
		switch (type) {
			case COPPER:
				recipes = new ItemRecipe[]{COPPER_SWORD, COPPER_SPEAR, COPPER_HALBERT, COPPER_AXE, COPPER_DAGGER, COPPER_SCIMITAR};
				break;
			case IRON:
				recipes = new ItemRecipe[]{IRON_SWORD, IRON_SPEAR, IRON_HALBERT, IRON_AXE, IRON_DAGGER, IRON_SCIMITAR};
				break;
			case GOLD:
				recipes = new ItemRecipe[]{GOLD_SWORD, GOLD_SPEAR, GOLD_HALBERT, GOLD_AXE, GOLD_DAGGER, GOLD_SCIMITAR};
				break;
			case WOOD:
				recipes = new ItemRecipe[]{WOOD_SWORD, WOOD_SPEAR, WOOD_HALBERT, WOOD_AXE, WOOD_DAGGER, WOOD_SCIMITAR};
				break;
			case CRYSTAL:
				recipes = new ItemRecipe[]{CRYSTAL_SWORD, CRYSTAL_SPEAR, CRYSTAL_HALBERT, CRYSTAL_AXE, CRYSTAL_DAGGER, CRYSTAL_SCIMITAR};
				break;
		}
		return recipes;
	}

	public Class<? extends Workstation> getWorkstationClass() {
		return workstation;
	}

}
