package map;

import java.util.ArrayList;
import entity.Entity;
import entity.Ore;
import entity.OreType;
import entity.Tree;
import entity.building.wall.Wall;
import entity.item.Item;
import entity.pathfinding.Point;
import entity.building.workstations.Workstation;
import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteHashtable;

public class Level {
	private Tile[][] tiles; // array of tiles on the map
	public int width, height; // map with and height

	// basic constructor
	public Level(int height, int width) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width][height];
		generateLevel();

	}

	public ArrayList<Item> getItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		for (Tile[] row : tiles) {
			for (Tile tile : row) {
				if (tile.getItem() != null) {
					items.add(tile.getItem());
				}
			}
		}
		return items;
	}

	// adding an item to the spritesheets
	public <T extends Item> void addItem(T e) {
		if (e != null) {
			tiles[e.getX() / 16][e.getY() / 16].setItem(e);
		}
	}

	// removing an item from the spritesheets
	public <T extends Item> void removeItem(T e) {
		if (e != null) {
			tiles[e.getX() / 16][e.getY() / 16].setItem(null);
		}
	}

	// is the tile on X and Y clear (No items or entities or walls blocking it)
	public boolean isClearTile(int x, int y) {
		return tiles[x][y].getItem() == null ? isWalkAbleTile(x, y) : false;

	}

	// is the tile on X and Y walkable (items can still be there)
	public boolean isWalkAbleTile(int x, int y) {
		return !tiles[x][y].solid();
	}

	// if there is a wall on x and y, return it
	public Entity getHardEntityOn(int x, int y) {
		return tiles[x / 16][y / 16].solid() ? tiles[x / 16][y / 16].getEntity() : null;
	}

	public Point getNearestEmptySpot(int xloc, int yloc) {
		int x0 = xloc / 16;
		int y0 = yloc / 16;
		if (isClearTile(x0, y0)) {
			return new Point(x0, y0);
		} else {
			for (int layer = 1; layer < 100; layer++) {
				int x = layer - 1;
				int y = 0;
				int dx = 1;
				int dy = 1;
				int err = dx - (layer << 1);

				while (x >= y) {
					if (isClearTile(x0 + x, y0 + y)) {
						return new Point(x0 + x, y0 + y);
					}
					if (isClearTile(x0 + y, y0 + x)) {
						return new Point(x0 + y, y0 + x);
					}
					if (isClearTile(x0 - y, y0 + x)) {
						return new Point(x0 - y, y0 + x);
					}
					if (isClearTile(x0 - x, y0 + y)) {
						return new Point(x0 - x, y0 + y);
					}
					if (isClearTile(x0 - x, y0 - y)) {
						return new Point(x0 - x, y0 - y);
					}
					if (isClearTile(x0 - y, y0 - x)) {
						return new Point(x0 - y, y0 - x);
					}
					if (isClearTile(x0 + y, y0 - x)) {
						return new Point(x0 + y, y0 - x);
					}
					if (isClearTile(x0 + x, y0 - y)) {
						return new Point(x0 + x, y0 - y);
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
			return null;
		}
	}

	public Wall getWallOn(int x, int y) {
		Entity entity = tiles[x / 16][y / 16].getEntity();
		return entity instanceof Wall ? (Wall) entity : null;
	}

	public boolean itemAlreadyThere(int x, int y, Item i) {
		return (getItemOn(x, y, i) != null);
	}

	public <T extends Workstation> T getNearestWorkstation(Class<T> workstation) {
		for (Tile[] row : tiles) {
			for (Tile e : row) {
				if (workstation.isInstance(e.getEntity())) {
					return workstation.cast(e.getEntity());
				}
			}
		}
		return null;
	}

	public Item getItemOn(int x, int y) {
		return tiles[x / 16][y / 16].getItem();
	}

	public Item getItemOn(int x, int y, Item item) {
		if (getItemOn(x, y) != null && getItemOn(x, y).isSameType(item)) {
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
					tiles[x][y] = new Tile(SpriteHashtable.getDirt(), false, x, y);
					randOre(x, y);
				} else {
					tiles[x][y] = new Tile(SpriteHashtable.getGrass(), false, x, y);
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
		for (Tile[] row : tiles) {
			for (Tile e : row) {
				if (e.getEntity() instanceof Tree) {
					if (e.getEntity().getX() / 16 == x / 16) {
						if (e.getEntity().getY() / 16 == y / 16) {
							return (Tree) e.getEntity();
						}
						if (seperate) {
							if ((e.getEntity().getY() - 1) / 16 == y / 16) {
								return (Tree) e.getEntity();
							}
						}

					}
				}
			}
		}
		return null;

	}

	// if there is ore on X and Y, return it
	public Ore selectOre(int x, int y) {
		Entity entity = tiles[x / 16][y / 16].getEntity();
		return entity instanceof Ore ? (Ore) entity : null;

	}

	// 10% chance of there being a tree on each grass tile
	private void randForest(int x, int y) {
		if (x == 0 || x == width || y == 0 || y == height) {
			return;
		}
		int rand = Entity.RANDOM.nextInt(10);
		if (rand == 1) {
			addEntity(new Tree(x * 16, y * 16), true);
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
				tiles[x][y].setEntity(new Ore(x * 16, y * 16, OreType.GOLD), false);
				break;
			case 1:
				tiles[x][y].setEntity(new Ore(x * 16, y * 16, OreType.IRON), false);
				break;
			case 2:
				tiles[x][y].setEntity(new Ore(x * 16, y * 16, OreType.COAL), false);
				break;
			case 3:
				tiles[x][y].setEntity(new Ore(x * 16, y * 16, OreType.COPPER), false);
				break;
			case 4:
				tiles[x][y].setEntity(new Ore(x * 16, y * 16, OreType.CRYSTAL), false);
				break;
			default:
				tiles[x][y].setEntity(new Ore(x * 16, y * 16, OreType.STONE), false);
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

			}
		}

	}

	public void renderHardEntities(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll / 16;
		int x1 = (xScroll + screen.width + Sprite.SIZE) / 16;
		int y0 = yScroll / 16;
		int y1 = (yScroll + screen.height + Sprite.SIZE) / 16;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).renderHard(screen);

			}
		}
	}

	// return the tile on x and y
	public Tile getTile(int x, int y) {
		return (x < 0 || x >= width || y < 0 || y >= height) ? Tile.voidTile : tiles[x][y];
	}

	public void addEntity(Entity entity, boolean solid) {
		if (entity != null) {
			tiles[entity.getX() / 16][entity.getY() / 16].setEntity(entity, solid);
		}
	}

	public void removeEntity(Entity entity) {
		tiles[entity.getX() / 16][entity.getY() / 16].removeEntity();
	}

}
