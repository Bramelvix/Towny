package map;

import entity.Entity;
import entity.dynamic.item.Item;
import graphics.Sprite;
import graphics.SpriteHashtable;
import util.vectors.Vec3f;

import java.util.Optional;

public class Tile {
	public final Sprite sprite; // tile's sprite
	private boolean solid; // is the tile solid
	public static final float SIZE = 48; // fixed size
	private Entity entity;

	// three static tiles voidtile = black, darkgrass is dark green, darkStone has the same function as darkGrass, but is for underground
	static final Tile darkGrass = new Tile(SpriteHashtable.get(4), true);
	static final Tile darkStone = new Tile(SpriteHashtable.get(140),true);
	static final Tile voidTile = new Tile(SpriteHashtable.get(3), true);

	// constructors
	Tile(Sprite sprite, boolean solid) {
		this.solid = solid;
		this.sprite = sprite;
	}

	Tile(Sprite sprite) {
		this(sprite, false);
	}

	// render a tile
	void render(int x, int y) {
		if ((entity == null || entity.isTransparent() || !entity.isVisible())) {
			sprite.draw(new Vec3f(x*Tile.SIZE, y*Tile.SIZE, 0.9f));
		}
		if (entity != null) {
			entity.render();
		}
	}


	// setters
	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public <T extends Item> void setItem(T item) {
		setEntity(item, false);
	}

	<T extends Entity> void setEntity(T entity, boolean solid) {
		this.entity = entity;
		this.solid = solid;
	}

	//getters
	public boolean isSolid() {
		return solid;
	}

	public Optional<Item> getItem() {
		return getEntity(Item.class);
	}

	<T extends Entity> Optional<T> getEntity(Class<T> type) {
		return type.isInstance(entity) ? Optional.of(type.cast(entity)) : Optional.empty();
	}

	<T extends Entity> boolean has(Class<T> type) {
		return getEntity(type).isPresent();
	}

	boolean entityIsNull() {
		return entity == null;
	}

	//removes the entity from the tile
	void removeEntity() {
		this.entity = null;
		this.solid = false;
	}

	public int getAvgColour() {
		//TODO multisprites getavg is broken right now
		return (entity != null) ? entity.sprite.getAvgColour() : sprite.getAvgColour();

	}

}
