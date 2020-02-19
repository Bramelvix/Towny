package entity.nonDynamic.resources;

import entity.dynamic.item.ItemHashtable;
import entity.dynamic.mob.Villager;
import graphics.opengl.OpenGLUtils;
import graphics.Sprite;
import graphics.SpriteHashtable;
import map.Level;
import sound.Sound;
import util.vectors.Vec2f;
import util.vectors.Vec4f;

public class Tree extends Resource {

	private byte chopped = 100;
	private final Sprite topsprite;

	// basic constructor
	public Tree(int x, int y, int z, Level level) {
		super(x, y, z, level);
		sprite = SpriteHashtable.get(12);
		topsprite = SpriteHashtable.get(13);
		setVisible(true);
		setName("tree");
	}

	// render method to render onto the screen
	public void render(Vec2f offset) {
		super.render(offset);
		topsprite.draw(new Vec2f(location.x, location.y-Sprite.SIZE), offset);
	}

	// work method (same as in the Ore class)
	public boolean work(Villager worker) {
		if (chopped > 0) {
			if (chopped % 20 == 0) {
				Sound.playSound(Sound.woodChopping);
			}
			chopped--;
			return false;
		} else {
			level.removeEntity(this);
			level.addItem(ItemHashtable.get(1, location.x, location.y, this.z));
			return true;
		}
	}

	@Override
	protected void drawSelection(Vec2f offset) {
		if (this.isSelected()) {
			OpenGLUtils.drawOutline(new Vec2f(location.x, location.y-Sprite.SIZE), new Vec2f(Sprite.SIZE, Sprite.SIZE*2), offset, new Vec4f(1,0,0,1)); // render the red square around selected resources
		}
	}

}
