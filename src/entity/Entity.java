package entity;

import java.util.Random;

import graphics.Screen;
import graphics.Sprite;
import input.Mouse;
import map.Level;

public abstract class Entity {
	protected int x, y; // x and y of the entity
	public Level level; // level in which the entity is placed
	protected final Random random = new Random(); // random needed for various
													// chance calculations
	public Sprite sprite; // the entity's sprite
	public Sprite extraSprite; // extra sprite for large entities (like trees)
	protected boolean visible; // is the entity visible or not
	protected boolean selected = false;

	// setters
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
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
		this.x = x;
		this.y = y;
	}

	// does the mouse hover over the entity
	public boolean hoverOn(Mouse mouse) {
		return ((mouse.getX()) >= x && ((mouse.getX()) <= x + Sprite.SIZE)
				&& ((mouse.getY()) >= y && ((mouse.getY()) <= y + Sprite.SIZE)));
	}

	// render method
	public void render(Screen screen) {
		screen.renderEntity(x, y, this);
	}
}
