package entity.dynamic.mob.work.recipe;

import entity.dynamic.item.ItemHashtable;
import entity.dynamic.item.ItemInfo;
import entity.nondynamic.building.BuildAbleObject;
import entity.nondynamic.building.Stairs;
import entity.nondynamic.building.container.Chest;
import entity.nondynamic.building.container.Workstation;
import entity.nondynamic.building.farming.TilledSoil;
import entity.nondynamic.building.wall.Wall;
import entity.nondynamic.building.wall.WallType;

import java.util.List;

public class BuildingRecipe extends Recipe {
	private final BuildAbleObject product;

	private static final BuildingRecipe WOOD_WALL = new BuildingRecipe(new Wall(WallType.WOOD), List.of(ItemHashtable.get(1)));
	private static final BuildingRecipe STONE_WALL = new BuildingRecipe(new Wall(WallType.STONE), List.of(ItemHashtable.get(10)));
	private static final BuildingRecipe WOOD_DOOR = new BuildingRecipe(new Wall(WallType.WOOD, true), List.of(ItemHashtable.get(1)));
	private static final BuildingRecipe STONE_DOOR = new BuildingRecipe(new Wall(WallType.STONE, true), List.of(ItemHashtable.get(10)));
	private static final BuildingRecipe FURNACE = new BuildingRecipe(Workstation.furnace(), List.of(ItemHashtable.get(10)));
	private static final BuildingRecipe ANVIL = new BuildingRecipe(Workstation.anvil(), List.of(ItemHashtable.get(2)));
	private static final BuildingRecipe CHEST = new BuildingRecipe(new Chest(), List.of(ItemHashtable.get(1)));
	public static final BuildingRecipe STAIRS_DOWN = new BuildingRecipe(new Stairs(true));
	private static final BuildingRecipe STAIRS_UP = new BuildingRecipe(new Stairs(false), List.of(ItemHashtable.get(1)));
	public static final BuildingRecipe TILLED_SOIL = new BuildingRecipe(new TilledSoil());

	public static final BuildingRecipe[] RECIPES = {WOOD_WALL, STONE_WALL, WOOD_DOOR, STONE_DOOR, FURNACE, ANVIL, CHEST, STAIRS_UP};

	private <T extends BuildAbleObject> BuildingRecipe(T product, List<ItemInfo<?>> resources) {
		super(resources);
		this.product = product;
	}

	private <T extends BuildAbleObject> BuildingRecipe(T product) {
		super();
		this.product = product;
	}

	@Override
	public String getRecipeName() {
		return product.getName();
	}

	public BuildAbleObject getProduct() {
		return product.instance();
	}

}
