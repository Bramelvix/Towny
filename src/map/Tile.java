package map;

import graphics.Screen;
import graphics.Sprite;

public class Tile {
	public Sprite sprite;
	public int x, y;
	private boolean solid;
	private boolean visible;
	public static final int SIZE = 16;

	public static Tile darkGrass = new Tile(Sprite.darkGrass, true,0,0);
	public static Tile voidTile = new Tile(Sprite.voidSprite, true,0,0);

	public Tile(Sprite sprite, boolean solid, int x, int y) {
		this.x = x;
		this.y = y;
		this.solid = solid;
		this.sprite = sprite;
	}

	public Tile(Sprite sprite, boolean solid, boolean visible, int x, int y) {
		this(sprite, solid, x,y);
		this.visible = visible;
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean visible() {
		return visible;
	}

	public boolean solid() {
		return solid;
	}


}
