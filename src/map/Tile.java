package map;

import entity.Entity;
import entity.dynamic.item.Item;
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
	public void render(int x, int y) {
    	sprite.draw(x,y);
		if (entity != null && !solid) {
			entity.sprite.draw(x,y);
		}
		if (item != null) {
			item.sprite.draw(x,y);
		}
	}

	public void renderHard() {
		if (solid && entity != null) {
			entity.sprite.draw(x,y);
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

	public <T extends Entity> T getEntity() {
		return (T) entity;
	}

	public void removeEntity() {
		this.entity = null;
		this.solid = false;
	}

}
