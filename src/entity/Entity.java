package entity;

import java.util.Random;

import graphics.Screen;
import graphics.Sprite;
import input.Mouse;
import map.Level;

public abstract class Entity {
	protected int x, y; // x and y of the entity
	public Level level; // level in which the entity is placed
	public static final Random RANDOM = new Random(); // random needed for
														// various chance
														// calculations
	public Sprite sprite; // the entity's sprite
	public Sprite extraSprite; // extra sprite for large entities (like trees)
	private boolean visible; // is the entity visible or not
	private boolean selected = false;
	private String displayName;

	// setters
	public void setVisible(boolean visible) {
		this.visible = visible;
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

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Entity() {
		super();
	}

	// getters
	public boolean isVisible() {
		return visible;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isSelected() {
		return selected;
	}

	// basic constructor
	public Entity(int x, int y) {
		setLocation(x, y);
	}

	// does the mouse hover over the entity
	public boolean hoverOn(Mouse mouse) {
		return hoverOn(mouse.getX(), mouse.getY());
	}

	// does the mouse hover over the entity
	public boolean hoverOn(int x, int y) {
		return (x >= this.x && x <= this.x + Sprite.SIZE && y >= this.y && y <= this.y + Sprite.SIZE);
	}

	// render method
	public void render(Screen screen) {
		screen.renderSprite(x, y, sprite);
	}
}
