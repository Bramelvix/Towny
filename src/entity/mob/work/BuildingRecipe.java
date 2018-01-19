package entity.mob.work;

import entity.BuildAbleObject;
import entity.Wall;
import entity.WallType;
import entity.workstations.Anvil;
import entity.workstations.Furnace;

public class BuildingRecipe extends Recipe {
	public static final BuildingRecipe WOOD_WALL = new BuildingRecipe(new Wall(WallType.WOOD), "logs");
	public static final BuildingRecipe STONE_WALL = new BuildingRecipe(new Wall(WallType.STONE), "stones");
	public static final BuildingRecipe WOOD_DOOR = new BuildingRecipe(new Wall(WallType.WOOD, true), "logs");
	public static final BuildingRecipe STONE_DOOR = new BuildingRecipe(new Wall(WallType.STONE, true), "stones");
	public static final BuildingRecipe FURNACE = new BuildingRecipe(new Furnace(), "stones");
	public static final BuildingRecipe ANVIL = new BuildingRecipe(new Anvil(), "iron bar");

	public static final BuildingRecipe[] RECIPES = { WOOD_WALL, STONE_WALL, WOOD_DOOR, STONE_DOOR, FURNACE, ANVIL };

	private <T extends BuildAbleObject> BuildingRecipe(T product, String... resources) {
		super(product, resources);
	}

	public <T extends BuildAbleObject> BuildAbleObject getProduct() {
		return (BuildAbleObject) product;
	}

}
