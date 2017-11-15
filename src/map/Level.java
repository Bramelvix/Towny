package map;

import java.util.ArrayList;
import java.util.List;
import entity.Entity;
import entity.Ore;
import entity.OreType;
import entity.Tree;
import entity.Wall;
import entity.item.Clothing;
import entity.item.Item;
import entity.item.weapon.Weapon;
import entity.pathfinding.Point;
import entity.workstations.Furnace;
import graphics.Screen;
import graphics.Sprite;

public class Level {
	public Tile[] tiles; // array of tiles on the map
	public int width, height; // map with and height
	public List<Entity> hardEntities; // list of entities on the map
	public List<Entity> walkableEntities;
	public List<Item> items;

	// basic constructor
	public Level(int height, int width) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width * height];
		hardEntities = new ArrayList<Entity>();
		walkableEntities = new ArrayList<Entity>();
		items = new ArrayList<Item>();
		generateLevel();

	}

	public List<Item> getItemList() {
		return items;
	}

	// adding an item to the list
	public void addItem(Item e) {
		Item o = new Item(e);
		if (itemAlreadyThere(e.getX(), e.getY(), e)) {
			getItemOn(e.getX(), e.getY()).quantity += e.quantity;
		} else {
			items.add(o);
		}
	}

	public void addItem(Weapon e) {
		Weapon o = new Weapon(e);
		if (itemAlreadyThere(e.getX(), e.getY(), e)) {
			getItemOn(e.getX(), e.getY()).quantity += e.quantity;
		} else {
			items.add(o);
		}
	}

	public void addItem(Clothing e) {
		Clothing o = new Clothing(e);
		if (itemAlreadyThere(e.getX(), e.getY(), e)) {
			getItemOn(e.getX(), e.getY()).quantity += e.quantity;
		} else {
			items.add(o);
		}
	}

	// removing an item from the list
	public void removeItem(Item e) {
		items.remove(e);
	}

	// is the tile on X and Y clear (No items or entities or walls blocking it)
	public boolean isClearTile(int x, int y) {
		return items.stream().filter(t -> (t.getX() / 16) == x && (t.getY() / 16) == y).findFirst().orElse(null) == null
				? isWalkAbleTile(x, y)
				: false;

	}

	// is the tile on X and Y walkable (items can still be there)
	public boolean isWalkAbleTile(int x, int y) {
		return hardEntities.stream().filter(t -> (t.getX() / 16) == x && (t.getY() / 16) == y).findFirst()
				.orElse(null) == null;
	}

	// if there is a wall on x and y, return it
	public Entity getHardEntityOn(int x, int y) {
		return hardEntities.stream().filter(t -> (t.getX() / 16) == (x/16) && (t.getY() / 16) == (y/16)).findFirst().orElse(null);
	}

	// TODO beter maken, dit is shitty
	public Point getNearestEmptySpot(int x, int y) {
		int gedeeldex = x / 16;
		int gedeeldey = y / 16;
		if (isClearTile(gedeeldex, gedeeldey)) {
			return new Point(gedeeldex, gedeeldey);
		} else {
			for (int i = 1; (i < gedeeldex && i < gedeeldey); i++) {
				if (isClearTile(gedeeldex - i, gedeeldey - i))
					return new Point(gedeeldex - i, gedeeldey - i);
				if (isClearTile(gedeeldex, gedeeldey - i))
					return new Point(gedeeldex, gedeeldey - i);
				if (isClearTile(gedeeldex + i, gedeeldey - i))
					return new Point(gedeeldex + i, gedeeldey - i);
				if (isClearTile(gedeeldex + i, gedeeldey))
					return new Point(gedeeldex + i, gedeeldey);
				if (isClearTile(gedeeldex + i, gedeeldey + i))
					return new Point(gedeeldex + i, gedeeldey + i);
				if (isClearTile(gedeeldex, gedeeldey + i))
					return new Point(gedeeldex, gedeeldey + i);
				if (isClearTile(gedeeldex - i, gedeeldey + i))
					return new Point(gedeeldex - i, gedeeldey + i);
				if (isClearTile(gedeeldex - i, gedeeldey))
					return new Point(gedeeldex - i, gedeeldey);
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

	public Furnace getNearestFurnace() {
		for (Entity e : hardEntities) {
			if (e instanceof Furnace)
				return (Furnace) e;
		}
		return null;
	}

	public Item getItemOn(int x, int y) {
		for (Item e : this.items) {
			if (((e.getX() / 16) == (x / 16)) && ((e.getY() / 16) == (y / 16)))
				return e;
		}
		return null;
	}

	public Item getItemWithNameOn(int x, int y, String name) {
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
					tiles[x + y * width] = Tile.darkGrass;
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
					tiles[x + y * width] = new Tile(Sprite.getDirt(), false, x, y);
					randOre(x, y);
				} else {
					tiles[x + y * width] = new Tile(Sprite.getGrass(), false, x, y);
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
					if (i.getY() / 16 == y / 16)
						return (Tree) i;
					if (seperate) {
						if ((i.getY() - 1) / 16 == y / 16)
							return (Tree) i;
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
				if (i.getX() / 16 == x >> 4 && i.getY() / 16 == y / 16)
					return (Ore) i;
			}
		}
		return null;

	}

	// render the entities
	public void renderHardEntites(Screen screen) {
		hardEntities.forEach((Entity i) -> i.render(screen));
	}

	// render the items
	public void renderSoftEntities(Screen screen) {
		walkableEntities.forEach((Entity i) -> i.render(screen));
		items.forEach((Entity i) -> i.render(screen));
	}

	// if there is an entity on X and Y, return it
	public Entity getEntityOn(int x, int y) {
		for (int s = 0; s < items.size(); s++) {
			Entity i = items.get(s);
			if (i.getX() / 16 == x / 16 && i.getY() / 16 == y / 16)
				return i;
		}
		for (int s = 0; s < walkableEntities.size(); s++) {
			Entity i = walkableEntities.get(s);
			if (i.getX() / 16 == x / 16 && i.getY() / 16 == y / 16)
				return i;
		}
		for (int s = 0; s < hardEntities.size(); s++) {
			Entity i = hardEntities.get(s);
			if (i.getX() / 16 == x / 16 && i.getY() / 16 == y / 16)
				return i;
		}
		return null;

	}

	// 10% chance of there being a tree on each grass tile
	private void randForest(int x, int y) {
		if (x == 0 || x == width || y == 0 || y == height)
			return;
		int rand = Entity.RANDOM.nextInt(10);
		if (rand == 1) {
			hardEntities.add(new Tree(x * 16, y * 16));
			getTile(x, y).setSolid(true);
		}

	}

	// 10% chance of there being ore on a dirt tile
	private void randOre(int x, int y) {
		if (x == 0 || x == width || y == 0 || y == height)
			return;
		int rand = Entity.RANDOM.nextInt(30);
		if (rand >= 4 && rand <= 7) {
			walkableEntities.add(new Ore(x * 16, y * 16, OreType.STONE));
		} else {
			if (rand == 1) {
				walkableEntities.add(new Ore(x * 16, y * 16, OreType.GOLD));
			} else {
				if (rand == 2) {
					walkableEntities.add(new Ore(x * 16, y * 16, OreType.IRON));

				} else {
					if (rand == 3) {
						walkableEntities.add(new Ore(x * 16, y * 16, OreType.COAL));
					}
				}
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
			}
		}

	}

	// return the tile on x and y
	public Tile getTile(int x, int y) {
		return (x < 0 || x >= width || y < 0 || y >= height) ? Tile.voidTile : tiles[x + y * width];
	}

}
