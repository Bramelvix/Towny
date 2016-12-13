package map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;

import entity.Entity;
import entity.Ore;
import entity.OreType;
import entity.Tree;
import entity.Wall;
import entity.item.Item;
import graphics.Screen;
import graphics.Sprite;

public class Map {
	public Tile[] tiles;
	public int width, height;
	private final Random random;
	public List<Entity> entities;
	private List<Item> items;

	public Map(int height, int width) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width * height];
		random = new Random();
		entities = new ArrayList<Entity>();
		items = new ArrayList<Item>();
		generateLevel();

	}

	public List<Item> getItemList() {
		return items;
	}

	public void addItem(Item e) {
		Item o = new Item(e);
		items.add(o);
	}

	public Item getItem(int index) {
		return items.get(index);
	}

	public void removeItem(Item e) {
		if (items.contains(e))
			items.remove(e);
	}

	public boolean isClearTile(int x, int y) {
		for (Entity e : entities) {
			if (e.x >> 4 == x && e.y >> 4 == y)
				return false;
		}
		for (Item e : items) {
			if (e.x >> 4 == x && e.y >> 4 == y)
				return false;
		}
		return !getTile(x, y).solid();
	}

	public Wall getWallOn(int x, int y) {
		for (Entity e : entities) {
			if ((e.x == x) && (e.y == y) && e instanceof Wall) {
				return (Wall) e;
			}
		}
		return null;
	}

	private void generateBorder() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
					tiles[x + y * width] = Tile.darkGrass;
				}
			}
		}
	}

	private void generateLevel() {
		generateBorder();
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < height - 1; x++) {
				if (random.nextInt(2) == 1) {
					tiles[x + y * width] = new Tile(Sprite.getGrass(), false, x, y);
					randForest(x, y);
				} else {
					tiles[x + y * width] = new Tile(Sprite.getDirt(), false, x, y);
					randOre(x, y);
				}
				if (x == 4 && y == 4) {
					tiles[x + y * width] = new Tile(Sprite.getGrass(), false, x, y);
				}
			}
		}

	}

	public Tree selectTree(int x, int y) {
		for (int s = 0; s < entities.size(); s++) {
			Entity i = entities.get(s);
			if (i instanceof Tree) {
				if (i.x >> 4 == x >> 4 && i.y >> 4 == y >> 4)
					return (Tree) i;
			}
		}
		return null;

	}

	public Ore selectOre(int x, int y) {
		for (int s = 0; s < entities.size(); s++) {
			Entity i = entities.get(s);
			if (i instanceof Ore) {
				if (i.x >> 4 == x >> 4 && i.y >> 4 == y >> 4)
					return (Ore) i;
			}
		}
		return null;

	}

	private void generateRandomLevel() {
		float[][] diamond = new float[width][height];
		float frequency = 1.0f / width;
		for (int x = 0; x < width - 1; x++) {
			for (int y = 0; y < height - 1; y++) {
				diamond[x][y] = Generator.Generate(x * frequency, y * frequency);
			}
		}
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (diamond[x][y] < 0f) {
					tiles[x + y * width] = new Tile(Sprite.grass, false, x, y);
					randForest(x, y);
				}
				if (diamond[x][y] >= -0f) {
					tiles[x + y * width] = new Tile(Sprite.water, false, x, y);
				}
			}
		}

	}

	public void renderEntites(Screen screen) {
		entities.forEach((Entity i) -> i.render(screen));
	}

	public void renderItems(Screen screen) {
		items.forEach((Item i) -> i.render(screen));
	}

	private void loadLevel(String path) {

	}

	private void randForest(int x, int y) {
		int rand = random.nextInt(10);
		if (rand == 1) {
			entities.add(new Tree(x << 4, y << 4));
			getTile(x, y).setSolid(true);
		}

	}

	private void randOre(int x, int y) {
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

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}

	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return Tile.voidTile;
		}
		return tiles[x + y * width];
	}

}
