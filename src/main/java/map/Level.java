package map;

import java.util.ArrayList;
import java.util.Optional;
import entity.*;
import entity.nonDynamic.building.Stairs;
import entity.nonDynamic.building.farming.TilledSoil;
import entity.nonDynamic.building.wall.Wall;
import entity.dynamic.item.Item;
import entity.nonDynamic.building.container.workstations.Workstation;
import entity.nonDynamic.resources.Ore;
import entity.nonDynamic.resources.OreType;
import entity.nonDynamic.resources.Tree;
import graphics.Sprite;
import graphics.SpriteHashtable;
import main.Game;
import util.BiPredicateInteger;
import util.vectors.Vec2f;
import util.vectors.Vec2i;

public class Level {

	private final Tile[][] tiles; // array of tiles on the map
	public final int width;
	public final int height; // map with and height
	private final int depth;

	// basic constructor
	public Level(int height, int width, int elevation) {
		this(height, width, elevation, true);
	}

	public Level(int height, int width, int elevation, boolean hardObjects) {
		this.width = width;
		this.height = height;
		depth = elevation;
		tiles = new Tile[width][height];
		generateLevel(elevation, hardObjects);
	}

	public ArrayList<Item> getItems() {
		ArrayList<Item> items = new ArrayList<>();
		for (Tile[] row : tiles) {
			for (Tile tile : row) {
				tile.getItem().ifPresent(items::add);
			}
		}
		return items;
	}

	// adding an item to the tile
	public <T extends Item> void addItem(T e) {
		if (e != null) {
			tiles[e.getTileX()][e.getTileY()].setItem(e);
		}
	}

	// removing an item from the tile
	public void removeItem(int x, int y) {
		tiles[x][y].removeEntity();
	}

	// is the tile on X and Y clear (No items or entities or walls blocking it)
	public boolean isClearTile(int x, int y) {
		return tiles[x][y].getItem().isEmpty() && isWalkAbleTile(x, y);
	}

	// is the tile on X and Y walkable (items can still be there)
	public boolean isWalkAbleTile(int x, int y) {
		return x > 0 && x < width && y > 0 && y < height && !tiles[x][y].isSolid();
	}

	public boolean tileIsEmpty(int x, int y) {//no mobs, no items, no buildings,...
		return isWalkAbleTile(x, y) && isClearTile(x, y) && tiles[x][y].entityIsNull();
	}

	public <T extends Entity> Optional<T> getEntityOn(int x, int y, Class<T> type) {
		return tiles[x][y].getEntity(type);
	}

	public <T extends Entity> Vec2i getNearestSpotThatHasX(int xloc, int yloc, Class<T> clazz) {
		return getNearestSpotThatHasX(xloc, yloc, (x, y) -> has(x, y, clazz));
	}

	private Vec2i getNearestSpotThatHasX(int xloc, int yloc, BiPredicateInteger p) { //p is the function that you want to run on the tile (for instance isEmpty or hasFurnace or whatever)
		if (p.test(xloc, yloc)) {
			return new Vec2i(xloc, yloc);
		} else {
			for (int layer = 1; layer < 100; layer++) {
				int x = layer - 1;
				int y = 0;
				int dx = 1;
				int dy = 1;
				int err = dx - (layer << 1);
				while (x >= y) {
					if (p.test(xloc + x, yloc + y)) {
						return new Vec2i( xloc + x, yloc + y);
					}
					if (p.test(xloc + y, yloc + x)) {
						return new Vec2i( xloc + y, yloc + x );
					}
					if (p.test(xloc - y, yloc + x)) {
						return new Vec2i( xloc - y, yloc + x);
					}
					if (p.test(xloc - x, yloc + y)) {
						return new Vec2i( xloc - x, yloc + y);
					}
					if (p.test(xloc - x, yloc - y)) {
						return new Vec2i( xloc - x, yloc - y);
					}
					if (p.test(xloc - y, yloc - x)) {
						return new Vec2i( xloc - y, yloc - x);
					}
					if (p.test(xloc + y, yloc - x)) {
						return new Vec2i( xloc + y, yloc - x);
					}
					if (p.test(xloc + x, yloc - y)) {
						return new Vec2i( xloc + x, yloc - y);
					}

					if (err <= 0) {
						y++;
						err += dy;
						dy += 2;
					}
					if (err > 0) {
						x--;
						dx += 2;
						err += dx - (layer << 1);
					}
				}
			}
		}
		return null;
	}

	public Optional<Wall> getWallOn(int x, int y) {
		return getEntityOn(x, y, Wall.class);
	}

	public <T extends Workstation> Optional<T> getNearestWorkstation(Class<T> workstation, int x, int y) {
		Vec2i point = getNearestSpotThatHasX(x, y, workstation);
		return point != null ? tiles[point.x][point.y].getEntity(workstation) : Optional.empty();
	}

	public Optional<Stairs> getNearestStairs(int x, int y, boolean top) { //gets the nearest stairs object on the map
		Vec2i point = top ? getNearestSpotThatHasX(x, y, this::hasTopStairs) : getNearestSpotThatHasX(x, y, this::hasBottomStairs);
		return point != null ? tiles[point.x][point.y].getEntity(Stairs.class) : Optional.empty();
	}

	private boolean hasBottomStairs(int x, int y) {
		return hasStairs(x, y, false);
	}

	private boolean hasStairs(int x, int y, boolean top) {
		if (!has(x, y, Stairs.class)) {
			return false;
		}
		Optional<Stairs> stairs = tiles[x][y].getEntity(Stairs.class);
		return stairs.isPresent() && (!top || stairs.get().isTop());
	}

	private boolean hasTopStairs(int x, int y) {
		return hasStairs(x, y, true);
	}

	private <T extends Entity> boolean has(int x, int y, Class<T> clazz) {
		return x <= width - 1 && x >= 0 && y <= height - 1 && y >= 0 && tiles[x][y].has(clazz);
	}

	public Optional<Item> getItemOn(int x, int y) {
		return tiles[x][y].getItem();
	}

	// generate the green border around the map
	private void generateBorder(int depth) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
					tiles[x][y] = depth == 0 ? Tile.darkGrass : Tile.darkStone;
				}
			}
		}
	}

	// generates a (slighty less) shitty random level
	private void generateLevel(int elevation, boolean hardObjects) {
		generateBorder(elevation);
		float[] noise = Generator.generateSimplexNoise(
			width, height, 11,
			Entity.RANDOM.nextInt(1000),
			Entity.RANDOM.nextBoolean()
		);
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
				if (noise[x + y * width] > 0.5) {
					tiles[x][y] = new Tile(SpriteHashtable.getDirt());
					if (elevation > 0) {
						tiles[x][y] = new Tile(SpriteHashtable.getStone());
					}
				} else {
					if (elevation > 0) {
						tiles[x][y] = new Tile(SpriteHashtable.getStone());
						if (hardObjects) {
							randOre(x, y);
						}

					} else {
						tiles[x][y] = new Tile(SpriteHashtable.getGrass());
						if (hardObjects) {
							randForest(x, y);
						}
					}
				}
			}
		}
		if (hardObjects) {
			for (int y = 1; y < height - 1; y++) {
				for (int x = 1; x < width - 1; x++) {
					selectOre(x, y).ifPresent(Ore::checkSides);
				}
			}
		}
	}

	private boolean outOfMapBounds(int x, int y) {
		return (x <= 0 || x > (width - 1) || y <= 0 || y > (height - 1));
	}


	public Optional<Tree> selectTree(int x, int y, boolean seperate) {
		if (outOfMapBounds(x,y)) {
			return Optional.empty();
		}
		Optional<Tree> result = tiles[x][y].getEntity(Tree.class);
		if (result.isPresent()) {
			return result;
		} else {
			Optional<Tree> entity = tiles[x][y+1].getEntity(Tree.class);
			return seperate ? entity : Optional.empty();
		}
	}

	// if there is a tree on X and Y (IN TILES), return it
	public Optional<Tree> selectTree(int x, int y) {
		return selectTree(x, y, true);
	}

	public Optional<TilledSoil> selectTilledSoil(int x, int y) {
		return tiles[x][y].getEntity(TilledSoil.class);
	}

	// if there is ore on X and Y, return it
	public Optional<Ore> selectOre(int x, int y) {
		return outOfMapBounds(x, y) ? Optional.empty() : tiles[x][y].getEntity(Ore.class);
	}

	// 10% chance of there being a tree on each grass tile
	private void randForest(int x, int y) {
		if (x <= 0 || x >= width - 1 || y <= 0 || y >= height - 1) {
			return;
		}
		if (Entity.RANDOM.nextInt(10) == 1) {
			addEntity(new Tree(x * Tile.SIZE, y * Tile.SIZE, depth, this), true);
		}
	}

	// 10% chance of there being ore on a dirt tile
	private void randOre(int x, int y) {
		if (x <= 0 || x >= width - 1 || y <= 0 || y >= height - 1) {
			return;
		}
		int rand = Entity.RANDOM.nextInt(50);
		if (rand <= 4) {
			addEntity(new Ore(x * Tile.SIZE, y * Tile.SIZE, depth, this, decideOreType(rand)), true);
			return;
		}
		spawnRock(x, y);
	}

	private OreType decideOreType (int nr) {
		return switch (nr) {
			case 0 -> OreType.GOLD;
			case 1 -> OreType.IRON;
			case 2 -> OreType.COAL;
			case 3 -> OreType.COPPER;
			case 4 -> OreType.CRYSTAL;
			default -> OreType.STONE;
		};
	}

	private void spawnRock(int x, int y) {
		if (outOfMapBounds(x, y)) { return; }
		addEntity(new Ore(x * Tile.SIZE, y * Tile.SIZE, depth, this, OreType.STONE), true);
	}

	// render the tiles
	public void render(Vec2f scroll) {
		int x0 = (int) (scroll.x / Tile.SIZE);
		int x1 = (int) ((scroll.x + Game.width + Sprite.SIZE) / Tile.SIZE) + 1;
		int y0 = (int) (scroll.y / Tile.SIZE);
		int y1 = (int) ((scroll.y + Game.height + Sprite.SIZE) / Tile.SIZE) + 1;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y);
			}
		}
	}

	// return the tile on x and y
	public Tile getTile(int x, int y) {
		return (x < 0 || x >= width || y < 0 || y >= height) ? Tile.voidTile : tiles[x][y];
	}

	public <T extends Entity> void addEntity(T entity, boolean solid) {
		if (entity != null) {
			tiles[entity.getTileX()][entity.getTileY()].setEntity(entity, solid);
		}
	}

	public void removeEntity(int x, int y) {
		tiles[x][y].removeEntity();
	}

	public void removeEntity(Entity entity) {
		removeEntity(entity.getTileX(), entity.getTileY());
	}

}
