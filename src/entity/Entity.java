package entity;

import java.util.Random;

import graphics.Screen;
import graphics.Sprite;
import map.Map;

public abstract class Entity {
	public int x, y;
	private boolean removed = false;
	protected Map level;
	protected final Random random = new Random();
	public Sprite sprite1;
	public Sprite sprite2;

	public void remove() {
		removed = true;
	}
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
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
