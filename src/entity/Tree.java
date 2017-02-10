package entity;

import entity.item.Item;
import graphics.Screen;
import graphics.Sprite;
import map.Level;
import map.Tile;
import sound.Sound;

public class Tree extends Resource {
	private float chopped = 100;

	//basic constructor
	public Tree(int x, int y) {
		super(x, y);
		sprite = Sprite.treebottom;
		extraSprite = Sprite.treetop;
		setVisible(true);
	}

	//render method to render onto the screen
	public void render(Screen screen) {
		screen.renderEntity(x, y, sprite);
		screen.renderEntity(x, y - Tile.SIZE, extraSprite);
	}

	//work method (same as in the Ore class)
	public boolean work(Level level) {
		if (chopped > 0) {
			if (chopped%20==0) Sound.speelGeluid(Sound.woodChopping);
			chopped--;
			return false;
		} else {
			level.entities.remove(this);
			level.addItem(new Item("Logs", this.x, this.y, Sprite.logs, "Wooden logs", true, 5));
			level.getTile(x>>4, y>>4).setSolid(false);
			return true;
		}

	}

}
