package map;

import entity.Entity;
import entity.dynamic.item.Item;
import graphics.Sprite;
import graphics.SpriteHashtable;
import util.vectors.Vec3f;

public class Tile {
	public Sprite sprite; // tile's sprite
	private boolean solid; // is the tile solid
	public static final int SIZE = 48; // fixed size
	private Item item;
	private Entity entity;

	// three static tiles voidtile = black, darkgrass is dark green, darkStone has the same function as darkGrass, but is for underground
	static Tile darkGrass = new Tile(SpriteHashtable.get(4), true);
	static Tile darkStone = new Tile(SpriteHashtable.get(140),true);
	static Tile voidTile = new Tile(SpriteHashtable.get(3), true);

	// constructors
	Tile(Sprite sprite, boolean solid) {
		this.solid = solid;
		this.sprite = sprite;
	}

	// render a tile
	void render(int x, int y) {
		if (entity == null || entity.isTransparent() || !entity.isVisible()) {
			sprite.draw(new Vec3f(x*Tile.SIZE, y*Tile.SIZE, 0.9f));
		}
		if (entity != null) {
			entity.render();
		}
		if (item != null) {
			item.render();
		}
	}


	// setters
	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public <T extends Item> void setItem(T item) {
		this.item = item;
	}

	<T extends Entity> void setEntity(T entity, boolean solid) {
		this.entity = entity;
		this.solid = solid;
	}

	//getters
	public boolean isSolid() {
		return solid;
	}

	public Item getItem() {
		return item;
	}

	<T extends Entity> T getEntity() {
		return (T) entity;
	}

	public boolean hasEntity() {return entity != null;}

	//removes the entity from the tile
	void removeEntity() {
		this.entity = null;
		this.solid = false;
	}

}
