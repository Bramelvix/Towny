package entity.mob.work;

import entity.BuildAbleObject;
import entity.Wall;
import entity.WallType;
import entity.item.Item;
import entity.workstations.Anvil;
import entity.workstations.Furnace;

public class BuildingRecipe extends Recipe {
    private static final BuildingRecipe WOOD_WALL = new BuildingRecipe(new Wall(WallType.WOOD), Item.logs);
    private static final BuildingRecipe STONE_WALL = new BuildingRecipe(new Wall(WallType.STONE), Item.stone);
    private static final BuildingRecipe WOOD_DOOR = new BuildingRecipe(new Wall(WallType.WOOD, true), Item.logs);
    private static final BuildingRecipe STONE_DOOR = new BuildingRecipe(new Wall(WallType.STONE, true), Item.stone);
    private static final BuildingRecipe FURNACE = new BuildingRecipe(new Furnace(), Item.stone);
    private static final BuildingRecipe ANVIL = new BuildingRecipe(new Anvil(), Item.iron_bar);

	public static final BuildingRecipe[] RECIPES = { WOOD_WALL, STONE_WALL, WOOD_DOOR, STONE_DOOR, FURNACE, ANVIL };

	private <T extends BuildAbleObject> BuildingRecipe(T product, Item... resources) {
		super(product, resources);
	}

    public BuildAbleObject getProduct() {
		// TODO: Instead of using .clone, find some other, cleaner way, to show seperate sprites
		return ((BuildAbleObject) product).clone();
	}

}
