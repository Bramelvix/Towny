package map;

import java.util.ArrayList;
import java.util.function.BiPredicate;

import entity.*;
import entity.nonDynamic.building.Stairs;
import entity.nonDynamic.building.wall.Wall;
import entity.dynamic.item.Item;
import entity.nonDynamic.building.workstations.Workstation;
import entity.nonDynamic.Ore;
import entity.nonDynamic.OreType;
import entity.nonDynamic.Tree;
import graphics.Sprite;
import graphics.SpriteHashtable;
import main.Game;

public class Level {
    private Tile[][] tiles; // array of tiles on the map
    public int width, height; // map with and height
    public int depth;

    // basic constructor
    public Level(int height, int width, int elevation) {
        this.width = width;
        this.height = height;
        depth = elevation;
        tiles = new Tile[width][height];
        generateLevel(elevation);

    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
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
        return tiles[x][y].getItem() == null && isWalkAbleTile(x, y);

    }

    // is the tile on X and Y walkable (items can still be there)
    public boolean isWalkAbleTile(int x, int y) {
        return !tiles[x][y].solid();
    }

    public boolean tileIsEmpty(int x, int y) {//no mobs, no items, no buildings,...
        return isWalkAbleTile(x, y) && isClearTile(x, y) && tiles[x][y].getEntity() == null;
    }

    public <T extends Entity> T getEntityOn(int x, int y) {
        return tiles[x / 16][y / 16].getEntity();
    }

    public Tile getNearestEmptySpot(int xloc, int yloc) {
        return getNearestSpotThatHasX(xloc, yloc, this::isClearTile);
    }

    public Tile getNearestSpotThatHasX(int xloc, int yloc, BiPredicate<Integer, Integer> p) { //p is the function that you want to run on the tile (for instance isEmpty or hasFurnace or whatever)
        int x0 = xloc / 16;
        int y0 = yloc / 16;
        if (p.test(x0, y0)) {
            return tiles[x0][y0];
        } else {
            for (int layer = 1; layer < 100; layer++) {
                int x = layer - 1;
                int y = 0;
                int dx = 1;
                int dy = 1;
                int err = dx - (layer << 1);
                while (x >= y) {
                    if (p.test(x0 + x, y0 + y)) {
                        return tiles[x0 + x][y0 + y];
                    }
                    if (p.test(x0 + y, y0 + x)) {
                        return tiles[x0 + y][y0 + x];
                    }
                    if (p.test(x0 - y, y0 + x)) {
                        return tiles[x0 - y][y0 + x];
                    }
                    if (p.test(x0 - x, y0 + y)) {
                        return tiles[x0 - x][y0 + y];
                    }
                    if (p.test(x0 - x, y0 - y)) {
                        return tiles[x0 - x][y0 - y];
                    }
                    if (p.test(x0 - y, y0 - x)) {
                        return tiles[x0 - y][y0 - x];
                    }
                    if (p.test(x0 + y, y0 - x)) {
                        return tiles[x0 + y][y0 - x];
                    }
                    if (p.test(x0 + x, y0 - y)) {
                        return tiles[x0 + x][y0 - y];
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
        return getEntityOn(x, y) instanceof Wall ? (Wall) getEntityOn(x, y) : null;
    }

    public <T extends Workstation> T getNearestWorkstation(Class<T> workstation, int x, int y) {
        if (getNearestSpotThatHasX(x, y, this::hasWorkStation) != null) {
            return workstation.cast(getNearestSpotThatHasX(x, y, this::hasWorkStation).getEntity());
        } else {
            return null;
        }
    }

    public Stairs getNearestStairs(int x, int y, boolean top) { //gets the nearest stairs object on the map
        if (top) {
            if (getNearestSpotThatHasX(x, y, this::hasTopStairs) != null) {
                return (Stairs) getNearestSpotThatHasX(x, y, this::hasTopStairs).getEntity();
            } else {
                return null;
            }
        } else {
            if (getNearestSpotThatHasX(x, y, this::hasBottomStairs) != null) {
                return (Stairs) getNearestSpotThatHasX(x, y, this::hasBottomStairs).getEntity();
            } else {
                return null;
            }
        }
    }

    private boolean hasBottomStairs(int x, int y) {
        return x <= width - 1 && x >= 0 && y <= height - 1 && y >= 0 && tiles[x][y].getEntity() instanceof Stairs && !((Stairs) (tiles[x][y].getEntity())).isTop();
    }

    private boolean hasTopStairs(int x, int y) {
        return x <= width - 1 && x >= 0 && y <= height - 1 && y >= 0 && tiles[x][y].getEntity() instanceof Stairs && ((Stairs) (tiles[x][y].getEntity())).isTop();
    }


    private boolean hasWorkStation(int x, int y) {
        return x <= width - 1 && x >= 0 && y <= height - 1 && y >= 0 && tiles[x][y].getEntity() instanceof Workstation;
    }

    public Item getItemOn(int x, int y) {
        return tiles[x / 16][y / 16].getItem();
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
    private void generateLevel(int elevation) {
        generateBorder();
        float[] noise = Generator.generateSimplexNoise(width, height, 11, Entity.RANDOM.nextInt(1000),
                Entity.RANDOM.nextBoolean());
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (noise[x + y * width] > 0.5) {
                    tiles[x][y] = new Tile(SpriteHashtable.getDirt(), false, x, y);
                    if (elevation > 0) {
                        tiles[x][y] = new Tile(SpriteHashtable.getStone(), false, x, y);
                    }
                } else {
                    if (elevation > 0) {
                        tiles[x][y] = new Tile(SpriteHashtable.getStone(), false, x, y);
                        if (!randOre(x, y)) {
                            spawnRock(x, y);
                        }
                    } else {
                        tiles[x][y] = new Tile(SpriteHashtable.getGrass(), false, x, y);
                        randForest(x, y);
                    }
                }
            }
        }
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                Entity e = tiles[x][y].getEntity();
                if (e != null && e instanceof Ore) {
                    ((Ore) e).checkSides(this);
                }
            }
        }


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
        if (x <= 0 || x > (width - 1) * 16 || y <= 0 || y > (height - 1) * 16) {
            return null;
        }
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
            addEntity(new Tree(x * 16, y * 16, depth, this), true);
        }

    }

    // 10% chance of there being ore on a dirt tile
    private boolean randOre(int x, int y) {
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
            return false;
        }
        int rand = Entity.RANDOM.nextInt(32);
        if (rand <= 4) {
            switch (rand) {
                case 0:
                    addEntity(new Ore(x * 16, y * 16, depth, this, OreType.GOLD), true);
                    break;
                case 1:
                    addEntity(new Ore(x * 16, y * 16, depth, this, OreType.IRON), true);
                    break;
                case 2:
                    addEntity(new Ore(x * 16, y * 16, depth, this, OreType.COAL), true);
                    break;
                case 3:
                    addEntity(new Ore(x * 16, y * 16, depth, this, OreType.COPPER), true);
                    break;
                case 4:
                    addEntity(new Ore(x * 16, y * 16, depth, this, OreType.CRYSTAL), true);
                    break;
            }
            return true;
        }
        return false;
    }

    private void spawnRock(int x, int y) {
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
            return;
        }
        addEntity(new Ore(x * 16, y * 16, depth, this, OreType.STONE), true);

    }

    // render the tiles
    public void render(int xScroll, int yScroll) {
        int x0 = xScroll/3 / 16;
        int x1 = (xScroll/3 + Game.width + Sprite.SIZE) / 16;
        int y0 = yScroll/3 / 16;
        int y1 = (yScroll/3 + Game.height + Sprite.SIZE) / 16;
        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                getTile(x, y).render(x*Sprite.SIZE, y*Sprite.SIZE);

            }
        }

    }

    public void renderHardEntities(int xScroll, int yScroll) {
        int x0 = xScroll / 16;
        int x1 = (xScroll + Game.width  + Sprite.SIZE) / 16;
        int y0 = yScroll / 16;
        int y1 = (yScroll + Game.height + Sprite.SIZE) / 16;

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                getTile(x, y).renderHard();

            }
        }
    }

    // return the tile on x and y
    public Tile getTile(int x, int y) {
        return (x < 0 || x >= width || y < 0 || y >= height) ? Tile.voidTile : tiles[x][y];
    }

    public <T extends Entity> void addEntity(T entity, boolean solid) {
        if (entity != null) {
            tiles[entity.getX() / 16][entity.getY() / 16].setEntity(entity, solid);
        }
    }

    public void removeEntity(int x, int y) {
        tiles[x][y].removeEntity();
    }

    public void removeEntity(Entity entity) {
        removeEntity(entity.getX() / 16, entity.getY() / 16);
    }

}
