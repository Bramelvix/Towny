package entity;

import entity.item.Item;
import entity.mob.Villager;
import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteHashtable;
import sound.Sound;

import java.io.FileNotFoundException;

public class Tree extends Resource {
	private byte chopped = 100;

	// basic constructor
	public Tree(int x, int y) {
		super(x, y);
		sprite = SpriteHashtable.get(12);
		extraSprite = SpriteHashtable.get(13);
		setVisible(true);
		setName("tree");
	}

	// render method to render onto the screen
	public void render(Screen screen) {
		screen.renderTree(x, y, this);
	}

	// work method (same as in the Ore class)
	public boolean work(Villager worker) {
		if (chopped > 0) {
			if (chopped % 20 == 0) {
				Sound.speelGeluid(Sound.woodChopping);
			}
			chopped--;
			return false;
		} else {
			worker.level.removeEntity(this);
			worker.level.addItem(new Item(1, this.x, this.y, true));
			return true;
		}

	}

}
