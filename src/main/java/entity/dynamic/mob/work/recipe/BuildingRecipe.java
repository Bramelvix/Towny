package entity.dynamic.mob.work.recipe;

import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.non_dynamic.building.BuildAbleObject;
import entity.non_dynamic.building.Stairs;
import entity.non_dynamic.building.container.Chest;
import entity.non_dynamic.building.container.Workstation;
import entity.non_dynamic.building.farming.TilledSoil;
import entity.non_dynamic.building.wall.Wall;
import entity.non_dynamic.building.wall.WallType;

public class BuildingRecipe extends Recipe<BuildAbleObject> {

	private static final BuildingRecipe WOOD_WALL = new BuildingRecipe(new Wall(WallType.WOOD), ItemHashtable.get(1));
	private static final BuildingRecipe STONE_WALL = new BuildingRecipe(new Wall(WallType.STONE), ItemHashtable.get(10));
	private static final BuildingRecipe WOOD_DOOR = new BuildingRecipe(new Wall(WallType.WOOD, true), ItemHashtable.get(1));
	private static final BuildingRecipe STONE_DOOR = new BuildingRecipe(new Wall(WallType.STONE, true), ItemHashtable.get(10));
	private static final BuildingRecipe FURNACE = new BuildingRecipe(Workstation.furnace(), ItemHashtable.get(10));
	private static final BuildingRecipe ANVIL = new BuildingRecipe(Workstation.anvil(), ItemHashtable.get(2));
	private static final BuildingRecipe CHEST = new BuildingRecipe(new Chest(), ItemHashtable.get(1));
	public static final BuildingRecipe STAIRS_DOWN = new BuildingRecipe(new Stairs(true));
	private static final BuildingRecipe STAIRS_UP = new BuildingRecipe(new Stairs(false), ItemHashtable.get(1));
	public static final BuildingRecipe TILLED_SOIL = new BuildingRecipe(new TilledSoil());

	public static final BuildingRecipe[] RECIPES = {WOOD_WALL, STONE_WALL, WOOD_DOOR, STONE_DOOR, FURNACE, ANVIL, CHEST, STAIRS_UP};

	private <T extends BuildAbleObject> BuildingRecipe(T product, Item... resources) {
		super(product, resources);
	}

	private <T extends BuildAbleObject> BuildingRecipe(T product) {
		super(product);
	}

	public BuildAbleObject getProduct() {
		return product.instance();
	}

}
