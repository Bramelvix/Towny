package entity;

import entity.item.Item;
import entity.mob.Villager;
import graphics.Screen;
import graphics.Sprite;
import sound.Sound;

public class Tree extends Resource {
	private byte chopped = 100;

	// basic constructor
	public Tree(int x, int y) {
		super(x, y);
		sprite = Sprite.treebottom;
		extraSprite = Sprite.treetop;
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
			worker.level.addItem(Item.logs.copy(this.x,this.y));
			return true;
		}

	}

}
