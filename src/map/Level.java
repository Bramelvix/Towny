package map;

import java.util.ArrayList;
import java.util.List;
import entity.Entity;
import entity.Ore;
import entity.OreType;
import entity.Tree;
import entity.Wall;
import entity.item.Item;
import entity.pathfinding.Point;
import entity.workstations.Workstation;
import graphics.Screen;
import graphics.Sprite;

public class Level {
	private Tile[][] tiles; // array of tiles on the map
	public int width, height; // map with and height
	private List<Entity> hardEntities; // list of entities on the map
	public List<Entity> walkableEntities;
	public Item[][] items;

	// basic constructor
	public Level(int height, int width) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width][height];
		hardEntities = new ArrayList<Entity>();
		walkableEntities = new ArrayList<Entity>();
		items = new Item[width][height];
		generateLevel();

	}

	public Item[][] getItems() {
		return items;
	}

	// adding an item to the list
	public <T extends Item> void addItem(T e) {
		if (e != null) {
			items[e.getX() / 16][e.getY() / 16] = e;
		}
	}

	// removing an item from the list
	public <T extends Item> void removeItem(T e) {
		if (e != null) {
			items[e.getX() / 16][e.getY() / 16] = null;
		}
	}

	// is the tile on X and Y clear (No items or entities or walls blocking it)
	public boolean isClearTile(int x, int y) {
		return items[x][y] == null ? isWalkAbleTile(x, y) : false;

	}

	// is the tile on X and Y walkable (items can still be there)
	public boolean isWalkAbleTile(int x, int y) {
		return !tiles[x][y].solid();
	}

	// if there is a wall on x and y, return it
	public Entity getHardEntityOn(int x, int y) {
		return hardEntities.stream().filter(t -> (t.getX() / 16) == (x / 16) && (t.getY() / 16) == (y / 16)).findFirst()
				.orElse(null);
	}

	// TODO beter maken, dit is shitty
	public Point getNearestEmptySpot(int x, int y) {
		int gedeeldex = x / 16;
		int gedeeldey = y / 16;
		if (isClearTile(gedeeldex, gedeeldey)) {
			return new Point(gedeeldex, gedeeldey);
		} else {
			for (int i = 1; (i < gedeeldex && i < gedeeldey); i++) {
				if (isClearTile(gedeeldex - i, gedeeldey - i)) {
					return new Point(gedeeldex - i, gedeeldey - i);
				}
				if (isClearTile(gedeeldex, gedeeldey - i)) {
					return new Point(gedeeldex, gedeeldey - i);
				}
				if (isClearTile(gedeeldex + i, gedeeldey - i)) {
					return new Point(gedeeldex + i, gedeeldey - i);
				}
				if (isClearTile(gedeeldex + i, gedeeldey)) {
					return new Point(gedeeldex + i, gedeeldey);
				}
				if (isClearTile(gedeeldex + i, gedeeldey + i)) {
					return new Point(gedeeldex + i, gedeeldey + i);
				}
				if (isClearTile(gedeeldex, gedeeldey + i)) {
					return new Point(gedeeldex, gedeeldey + i);
				}
				if (isClearTile(gedeeldex - i, gedeeldey + i)) {
					return new Point(gedeeldex - i, gedeeldey + i);
				}
				if (isClearTile(gedeeldex - i, gedeeldey)) {
					return new Point(gedeeldex - i, gedeeldey);
				}
			}
			return null;
		}
	}

	public Wall getWallOn(int x, int y) {
		for (Entity e : hardEntities) {
			if (e instanceof Wall && (e.getX() == x) && (e.getY() == y))
				return (Wall) e;
		}
		return null;
	}

	public boolean itemAlreadyThere(int x, int y, Item i) {
		return (getItemWithNameOn(x, y, i.getName()) != null);
	}

	public <T extends Workstation> T getNearestWorkstation(Class<T> workstation) {
		for (Entity e : hardEntities) {
			if (workstation.isInstance(e)) {
				return workstation.cast(e);
			}
		}
		return null;
	}

	public <T extends Item> Item getItemOn(int x, int y) {
		return  items[x / 16][y / 16];
	}

	public <T extends Item> Item getItemWithNameOn(int x, int y, String name) {
		if (getItemOn(x, y) != null && getItemOn(x, y).getName().equals(name)) {
			return getItemOn(x, y);
		} else {
			return null;
		}
	}

	// generate the green border around the map
	private void generateBorder() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
					tiles[x][y] = Tile.darkGrass;
				}
			}
		}
	}

	// generates a (slighty less) shitty random level
	private void generateLevel() {
		float[] noise = Generator.generateSimplexNoise(width, height, 11, Entity.RANDOM.nextInt(1000),
				Entity.RANDOM.nextBoolean());
		for (int y = 0; y < height - 1; y++) {
			for (int x = 0; x < width - 1; x++) {
				if (noise[x + y * width] > 0.5) {
					tiles[x][y] = new Tile(Sprite.getDirt(), false, x, y);
					randOre(x, y);
				} else {
					tiles[x][y] = new Tile(Sprite.getGrass(), false, x, y);
					randForest(x, y);
				}
			}
		}
		generateBorder();

	}

	// if there is a tree on X and Y, return it
	public Tree selectTree(int x, int y) {
		return selectTree(x, y, true);

	}

	public Tree selectTree(int x, int y, boolean seperate) {
		for (int s = 0; s < hardEntities.size(); s++) {
			Entity i = hardEntities.get(s);
			if (i instanceof Tree) {
				if (i.getX() / 16 == x / 16) {
					if (i.getY() / 16 == y / 16) {
						return (Tree) i;
					}
					if (seperate) {
						if ((i.getY() - 1) / 16 == y / 16) {
							return (Tree) i;
						}
					}

				}
			}
		}
		return null;

	}

	// if there is ore on X and Y, return it
	public Ore selectOre(int x, int y) {
		for (int s = 0; s < walkableEntities.size(); s++) {
			Entity i = walkableEntities.get(s);
			if (i instanceof Ore) {
				if (i.getX() / 16 == x >> 4 && i.getY() / 16 == y / 16) {
					return (Ore) i;
				}
			}
		}
		return null;

	}

	// render the hard (not walkable) entities
	public void renderHardEntites(Screen screen) {
		hardEntities.forEach((Entity i) -> i.render(screen));
	}

	// render the soft (walkable) entities
	public void renderSoftEntities(Screen screen) {
		walkableEntities.forEach((Entity i) -> i.render(screen));
	}

	// 10% chance of there being a tree on each grass tile
	private void randForest(int x, int y) {
		if (x == 0 || x == width || y == 0 || y == height) {
			return;
		}
		int rand = Entity.RANDOM.nextInt(10);
		if (rand == 1) {
			addHardEntity(new Tree(x * 16, y * 16));
			getTile(x, y).setSolid(true);
		}

	}

	// 10% chance of there being ore on a dirt tile
	private void randOre(int x, int y) {
		if (x == 0 || x == width || y == 0 || y == height) {
			return;
		}
		int rand = Entity.RANDOM.nextInt(32);
		if (rand <= 12) {
			switch (rand) {
			case 0:
				walkableEntities.add(new Ore(x * 16, y * 16, OreType.GOLD));
				break;
			case 1:
				walkableEntities.add(new Ore(x * 16, y * 16, OreType.IRON));
				break;
			case 2:
				walkableEntities.add(new Ore(x * 16, y * 16, OreType.COAL));
				break;
			case 3:
				walkableEntities.add(new Ore(x * 16, y * 16, OreType.COPPER));
				break;
			case 4:
				walkableEntities.add(new Ore(x * 16, y * 16, OreType.CRYSTAL));
				break;
			default:
				walkableEntities.add(new Ore(x * 16, y * 16, OreType.STONE));
				break;
			}
		}
	}

	// render the tiles
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll / 16;
		int x1 = (xScroll + screen.width + Sprite.SIZE) / 16;
		int y0 = yScroll / 16;
		int y1 = (yScroll + screen.height + Sprite.SIZE) / 16;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
				if (getItemOn(x*16, y*16) != null) {
					getItemOn(x*16, y*16).render(screen);
				}
			}
		}

	}

	// return the tile on x and y
	public Tile getTile(int x, int y) {
		return (x < 0 || x >= width || y < 0 || y >= height) ? Tile.voidTile : tiles[x][y];
	}

	public void addHardEntity(Entity entity) {
		if (entity != null) {
			hardEntities.add(entity);
			tiles[entity.getX() / 16][entity.getY() / 16].setSolid(true);
		}
	}

	public void removeHardEntity(Entity entity) {
		hardEntities.remove(entity);
		tiles[entity.getX() / 16][entity.getY() / 16].setSolid(false);

	}

}
