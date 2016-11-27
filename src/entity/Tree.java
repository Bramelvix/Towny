package entity;

import entity.item.Item;
import graphics.Screen;
import graphics.Sprite;
import map.Map;
import sound.Sound;

public class Tree extends Entity {
	private float chopped = 100;

	public Tree(int x, int y) {
		super(x, y);
		sprite1 = Sprite.treebottom;
		sprite2 = Sprite.treetop;
	}

	public void render(Screen screen) {
		screen.renderEntity(x, y, sprite1);
		screen.renderEntity(x, y - 16, sprite2);
	}

	public boolean chop(Map level) {
		if (chopped > 0) {
			if (chopped%20==0) Sound.speelGeluid(Sound.woodChopping);
			chopped--;
			return false;
		} else {
			remove();
			level.entities.remove(this);
			level.items.add(new Item("Logs", this.x, this.y, Sprite.logs, "Wooden logs", true));
			level.getTile(x>>4, y>>4).setSolid(false);
			return true;
		}

	}

}
