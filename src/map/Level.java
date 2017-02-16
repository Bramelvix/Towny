package map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.Entity;
import entity.Ore;
import entity.OreType;
import entity.Tree;
import entity.Wall;
import entity.item.Item;
import graphics.Screen;
import graphics.Sprite;

public class Level {
	public Tile[] tiles; // array of tiles on the map
	public int width, height; // map with and height
	private final Random random; // random used for generating
	public List<Entity> entities; // list of entities on the map
	private List<Item> items; // list of items on the map

	// basic constructor
	public Level(int height, int width) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width * height];
		random = new Random();
		entities = new ArrayList<Entity>();
		items = new ArrayList<Item>();
		generateLevel();

	}

	// getters
	public List<Item> getItemList() {
		return items;
	}

	public Item getItem(int index) {
		return items.get(index);
	}

	// adding an item to the list
	public void addItem(Item e) {
		Item o = new Item(e);
		items.add(o);
	}

	// removing an item from the list
	public void removeItem(Item e) {
		if (items.contains(e))
			items.remove(e);
	}

	// is the tile on X and Y clear (No items or entities or walls blocking it)
	public boolean isClearTile(int x, int y) {
		for (Item e : items) {
			if (e.getX() >> 4 == x && e.getY() >> 4 == y)
				return false;
		}
		if (!isWalkAbleTile(x, y)) {
			return false;
		}
		return !getTile(x, y).solid();
	}

	// is the tile on X and Y walkable (items can still be there)
	public boolean isWalkAbleTile(int x, int y) {
		for (Entity e : entities) {
			if ((e.getX() >> 4 == x && e.getY() >> 4 == y)|| getTile(x, y).solid())
				return false;
		}
		return true;
	}

	// if there is a wall on x and y, return it
	public Wall getWallOn(int x, int y) {
		for (Entity e : entities) {
			if ((e.getX() == x) && (e.getY() == y) && e instanceof Wall) {
				return (Wall) e;
			}
		}
		return null;
	}

	public Item getItemOn(int x, int y) {
		for (Item e : items) {
			if ((e.getX() >> 4 == x >> 4) && (e.getY() >> 4 == y >> 4)) {
				return e;
			}
		}
		return null;
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
		float[] noise = Generator.generateSimplexNoise(width, height, 11, random.nextInt(1000), random.nextBoolean());
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
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
		for (int s = 0; s < entities.size(); s++) {
			Entity i = entities.get(s);
			if (i instanceof Tree) {
				if (i.getX() >> 4 == x >> 4 && i.getY() >> 4 == y >> 4)
					return (Tree) i;
			}
		}
		return null;

	}

	// if there is ore on X and Y, return it
	public Ore selectOre(int x, int y) {
		for (int s = 0; s < entities.size(); s++) {
			Entity i = entities.get(s);
			if (i instanceof Ore) {
				if (i.getX() >> 4 == x >> 4 && i.getY() >> 4 == y >> 4)
					return (Ore) i;
			}
		}
		return null;

	}

	// render the entities
	public void renderEntites(Screen screen) {
		entities.forEach((Entity i) -> i.render(screen));
	}

	// render the items
	public void renderItems(Screen screen) {
		items.forEach((Item i) -> i.render(screen));
	}

	// if there is an entity on X and Y, return it
	public Entity getEntityOn(int x, int y) {
		for (int s = 0; s < entities.size(); s++) {
			Entity i = entities.get(s);
			if (i.getX() >> 4 == x >> 4 && i.getY() >> 4 == y >> 4)
				return i;
		}
		return null;

	}

	// 10% chance of there being a tree on each grass tile
	private void randForest(int x, int y) {
		if (x == 0 || x == width || y == 0 || y == height)
			return;
		int rand = random.nextInt(10);
		if (rand == 1) {
			entities.add(new Tree(x << 4, y << 4));
			getTile(x, y).setSolid(true);
		}

	}

	// 10% chance of there being ore on a dirt tile
	private void randOre(int x, int y) {
		if (x == 0 || x == width || y == 0 || y == height)
			return;
		int rand = random.nextInt(20);
		if (rand == 1) {
			entities.add(new Ore(x << 4, y << 4, OreType.GOLD));
			getTile(x, y).setSolid(true);
		} else {
			if (rand == 2) {
				entities.add(new Ore(x << 4, y << 4, OreType.IRON));
				getTile(x, y).setSolid(true);

			}
		}

	}

	// render the tiles
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + Sprite.SIZE) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + Sprite.SIZE) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}

	}

	// return the tile on x and y
	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return Tile.voidTile;
		}
		return tiles[x + y * width];
	}

}
