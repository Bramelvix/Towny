package map;

import entity.Entity;
import entity.dynamic.item.Item;
import graphics.Sprite;
import graphics.SpriteHashtable;
import graphics.SpritesheetHashtable;
import util.vectors.Vec2f;

import java.util.ArrayList;

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
	void render(Vec2f pos, Vec2f offset, ArrayList<Vec2f> tileRenderList, ArrayList<Vec2f> entityRenderList, ArrayList<Vec2f> itemRenderList) {
		if (entity == null || entity.isTransparent() || !entity.isVisible()) {
			//sprite.draw(pos, offset);
			tileRenderList.add(sprite.getTexCoords());
			//tileRenderList.add(sprite.getTexCoords().y);
		} else {
			tileRenderList.add(new Vec2f(0f,0f));
		}
		if (entity != null && !solid) {
			//entity.render(offset);
			entityRenderList.add(entity.sprite.getTexCoords());
		} else {
			entityRenderList.add(SpriteHashtable.get(142).getTexCoords());
		}
		if (item != null) {
			itemRenderList.add(item.sprite.getTexCoords());
		} else {
			itemRenderList.add(SpriteHashtable.get(142).getTexCoords());
		}
	}

	void renderHard(Vec2f offset) {
		if (solid && entity != null) {
			entity.render(offset);
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
