package map;

import graphics.Screen;
import graphics.Sprite;

public class Tile {
	public Sprite sprite; // tile's sprite
	public int x, y; // x and y
	private boolean solid; // is the tile solid
	private boolean visible; // is the tile visible
	public static final int SIZE = 16; // fixed size

	// two static tiles voidtile = black, darkgrass is dark green
	public static Tile darkGrass = new Tile(Sprite.darkGrass, true, 0, 0);
	public static Tile voidTile = new Tile(Sprite.voidSprite, true, 0, 0);

	// constructors
	public Tile(Sprite sprite, boolean solid, int x, int y) {
		this.x = x;
		this.y = y;
		this.solid = solid;
		this.sprite = sprite;
	}

	public Tile(Sprite sprite, boolean solid, boolean visible, int x, int y) {
		this(sprite, solid, x, y);
		this.visible = visible;
	}

	// render a tile
	public void render(int x, int y, Screen screen) {
		screen.renderSprite(x * 16, y * 16, sprite);
	}

	// steters
	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	// getters
	public boolean visible() {
		return visible;
	}

	public boolean solid() {
		return solid;
	}

}
