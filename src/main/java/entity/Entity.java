package entity;

import graphics.Sprite;
import input.PointerInput;
import map.Tile;
import util.vectors.Vec3f;

import java.util.Random;

public abstract class Entity {

	protected Vec3f location = new Vec3f(-1, -1, 0.8f);
	protected int z; // The level number the entity is on
	public static final Random RANDOM = new Random(); // random needed for various chance calculations
	protected Sprite sprite; // the entity's sprite
	private boolean visible; // is the entity visible or not
	private boolean transparent = true; // non-transparent entities will stop the Tile sprite under them from rendering unnecessarily
	private boolean selected = false;
	private String displayName;

	// setters
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

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

	protected Entity() {
		setVisible(true);
	}

	// getters
	public boolean isVisible() {
		return visible;
	}

	public Vec3f getLocation() {
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
		return Math.round(location.x / Tile.SIZE);
	}

	public int getTileY() {
		return Math.round(location.y / Tile.SIZE);
	}

	protected boolean isSelected() {
		return selected;
	}

	public boolean isTransparent() {
		return transparent;
	}

	// basic constructor
	protected Entity(float x, float y, int z) {
		this();
		location = new Vec3f(-1);
		setLocation(x, y, z);
	}

	protected Entity(String name, Sprite sprite) {
		this();
		setName(name);
		this.sprite = sprite;
	}

	// does the mouse hover over the entity
	public boolean hoverOn(PointerInput pointer, int z) {
		return (z == this.z && pointer.getX() >= location.x && pointer.getX() <= location.x + Sprite.SIZE && pointer.getY() >= location.y && pointer.getY() <= location.y + Sprite.SIZE);
	}

	// render method
	public void render() {
		if (isVisible()) {
			sprite.draw(location);
		}
	}

	public Sprite getSprite() {
		return sprite;
	}
}
