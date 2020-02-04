package entity.dynamic.mob.work;

import entity.nonDynamic.building.BuildAbleObject;
import entity.nonDynamic.building.Stairs;
import entity.nonDynamic.building.container.Chest;
import entity.nonDynamic.building.farming.TilledSoil;
import entity.nonDynamic.building.wall.Wall;
import entity.nonDynamic.building.wall.WallType;
import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.nonDynamic.building.container.workstations.Anvil;
import entity.nonDynamic.building.container.workstations.Furnace;

public class BuildingRecipe extends Recipe {
	private static final BuildingRecipe WOOD_WALL = new BuildingRecipe(new Wall(WallType.WOOD), ItemHashtable.get(1));
	private static final BuildingRecipe STONE_WALL = new BuildingRecipe(new Wall(WallType.STONE), ItemHashtable.get(10));
	private static final BuildingRecipe WOOD_DOOR = new BuildingRecipe(new Wall(WallType.WOOD, true), ItemHashtable.get(1));
	private static final BuildingRecipe STONE_DOOR = new BuildingRecipe(new Wall(WallType.STONE, true), ItemHashtable.get(10));
	private static final BuildingRecipe FURNACE = new BuildingRecipe(new Furnace(), ItemHashtable.get(10));
	private static final BuildingRecipe ANVIL = new BuildingRecipe(new Anvil(), ItemHashtable.get(2));
	private static final BuildingRecipe CHEST = new BuildingRecipe(new Chest(), ItemHashtable.get(1));
	public static final BuildingRecipe STAIRSDOWN = new BuildingRecipe(new Stairs(true));
	private static final BuildingRecipe STAIRSUP = new BuildingRecipe(new Stairs(false), ItemHashtable.get(1));
	public static final BuildingRecipe TILLED_SOIL = new BuildingRecipe(new TilledSoil());

	public static final BuildingRecipe[] RECIPES = {WOOD_WALL, STONE_WALL, WOOD_DOOR, STONE_DOOR, FURNACE, ANVIL, CHEST, STAIRSUP};

	private <T extends BuildAbleObject> BuildingRecipe(T product, Item... resources) {
		super(product, resources);
	}

	private <T extends BuildAbleObject> BuildingRecipe(T product) {
		super(product);
	}

	public BuildAbleObject getProduct() {
		return ((BuildAbleObject) product).instance();
	}

}
