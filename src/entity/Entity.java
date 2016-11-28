package entity;

import java.util.Random;

import graphics.Screen;
import graphics.Sprite;
import input.Mouse;
import map.Map;

public abstract class Entity {
	public int x, y;
	private boolean removed = false;
	protected Map level;
	protected final Random random = new Random();
	public Sprite sprite1;
	public Sprite sprite2;
	private boolean visible;

	public void remove() {
		removed = true;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean isVisible() {
		return visible;
	}

	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean hoverOn(Mouse mouse) {
		return ((mouse.getX()) >= x && ((mouse.getX()) <= x + 16)
				&& ((mouse.getY()) >= y && ((mouse.getY()) <= y + 16)));
	}
	

	public Entity() {

	}

	public boolean IsRemoved() {
		return removed;
	}

	public void render(Screen screen) {
		screen.renderEntity(x, y, sprite1);
	}
}
