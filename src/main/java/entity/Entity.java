package entity;

import java.util.Random;

import graphics.Sprite;
import graphics.opengl.InstanceData;
import input.PointerInput;
import map.Tile;
import util.vectors.Vec2f;
import util.vectors.Vec2i;
import util.vectors.Vec3f;

public abstract class Entity {

	protected Vec3f location = new Vec3f(-1, -1, -1);
	protected int z; // This needs to be renamed
	public static final Random RANDOM = new Random(); // random needed for various chance calculations
	public Sprite sprite; // the entity's sprite
	private boolean visible; // is the entity visible or not
	private boolean transparent = true; // non transparent entities will stop the Tile sprite under them from rendering unnecessarily
	private boolean selected = false;
	private String displayName;

	// setters
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setTransparent(boolean transparent) { this.transparent = transparent; }

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getName() {
		return displayName;
	}

	public void setName(String name) {
		displayName = name;
	}

	public void setLocation(float x, float y, int z) {
		location.x = x;
		location.y = y;
		this.z = z;
	}

	public Entity() {
		setVisible(true);
	}

	// getters
	public boolean isVisible() {
		return visible;
	}

	public Vec3f getXY() {
		return location;
	}

	public float getX() {
		return location.x;
	}

	public float getY() {
		return location.y;
	}

	public int getZ() {
		return z;
	}

	public int getTileX() {
		return (int) location.x/ Tile.SIZE;
	}

	public int getTileY() {
		return (int) location.y / Tile.SIZE;
	}

	protected boolean isSelected() {
		return selected;
	}

	public boolean isTransparent() { return transparent; }

	// basic constructor
	public Entity(float x, float y, int z) {
		this();
		location = new Vec3f(x, y, z);
		setLocation(x, y, z);
	}

	public Entity(String name, Sprite sprite) {
		this();
		setName(name);
		this.sprite = sprite;
	}

	// does the mouse hover over the entity
	public boolean hoverOn(PointerInput pointer, int z) {
		return (z == this.z && pointer.getX() >= location.x && pointer.getX() <= location.x + Sprite.SIZE && pointer.getY() >= location.y && pointer.getY() <= location.y + Sprite.SIZE);
	}

	// render method
	public void render(InstanceData instanceData) {
	   if (isVisible()) {
		   sprite.draw(location, instanceData);
	   }
	}

	public void renderIf(boolean ifCondition, InstanceData instanceData) {
		if (ifCondition) {
			render(instanceData);
		}
	}

}
