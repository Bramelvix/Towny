package map;

import entity.Entity;
import entity.dynamic.item.Item;
import graphics.Sprite;
import graphics.SpriteHashtable;
import graphics.SpritesheetHashtable;
import graphics.opengl.InstanceData;
import graphics.opengl.OpenGLUtils;
import util.vectors.Vec2f;
import util.vectors.Vec3f;

import java.nio.ByteBuffer;
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
	void render(int xi, int yi, InstanceData tileData, InstanceData entityData, InstanceData itemData) {
		if (entity == null || entity.isTransparent() || !entity.isVisible()) {
			Vec3f pos = new Vec3f(OpenGLUtils.pToGL(xi*Tile.SIZE,'w'), OpenGLUtils.pToGL(yi*Tile.SIZE,'h'), -10.f);
			tileData.put(pos, sprite.getTexCoords());
		}
		if (entity != null && !solid) {
			Vec3f pos = new Vec3f(OpenGLUtils.pToGL(entity.getX(),'w'), OpenGLUtils.pToGL(entity.getY(),'h'), (float)entity.getZ());
			entityData.put(pos, entity.sprite.getTexCoords());
		}
		if (item != null) {
			Vec3f pos = new Vec3f(OpenGLUtils.pToGL(item.getX(),'w'), OpenGLUtils.pToGL(item.getY(),'h'), (float)item.getZ());
			itemData.put(pos, item.sprite.getTexCoords());
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
