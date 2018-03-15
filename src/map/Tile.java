package map;

import entity.Entity;
import entity.item.Item;
import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteHashtable;

public class Tile {
	public Sprite sprite; // tile's sprite
	public int x, y; // x and y
	private boolean solid; // is the tile solid
	public static final int SIZE = 16; // fixed size
	private Item item;
	private Entity entity;

	// two static tiles voidtile = black, darkgrass is dark green
    public static Tile darkGrass = new Tile(SpriteHashtable.get(4), true, 0, 0);
    public static Tile voidTile = new Tile(SpriteHashtable.get(3), true, 0, 0);

	// constructors
    Tile(Sprite sprite, boolean solid, int x, int y) {
		this.x = x;
		this.y = y;
		this.solid = solid;
		this.sprite = sprite;
	}

	// render a tile
	public void render(int x, int y, Screen screen) {
		screen.renderSprite(x * 16, y * 16, sprite);
		if (entity != null && !solid) {
			entity.render(screen);
		}
		if (item != null) {
			item.render(screen);
		}
	}

	public void renderHard(Screen screen) {
		if (solid && entity != null) {
			entity.render(screen);
		}
	}

	// steters
	public void setSolid(boolean solid) {
		this.solid = solid;
	}


	public boolean solid() {
		return solid;
	}

	public Item getItem() {
		return item;
	}

	public <T extends Item> void setItem(T item) {
		this.item = item;
	}

	public <T extends Entity> void setEntity(T entity, boolean solid) {
		this.entity = entity;
		this.solid = solid;
	}

	public Entity getEntity() {
		return entity;
	}

	public void removeEntity() {
		this.entity = null;
		this.solid = false;
	}

}
