package entity.nonDynamic.resources;

import entity.dynamic.item.ItemHashtable;
import entity.dynamic.mob.Villager;
import graphics.opengl.OpenGLUtils;
import graphics.Sprite;
import graphics.SpriteHashtable;
import map.Level;
import sound.Sound;
import util.vectors.Vec2f;
import util.vectors.Vec3f;

public class Tree extends Resource {

	private byte chopped = 100;
	private final Sprite topsprite;

	// basic constructor
	public Tree(int x, int y, int z, Level level) {
		super(x, y, z, level);
		sprite = SpriteHashtable.get(12);
		topsprite = SpriteHashtable.get(13);
		setVisible(true);
		setTransparent(true);
		setName("tree");
		location.z = -0.8f;
	}

	// render method to render onto the screen
	public void render() {
		super.render();
		topsprite.draw(new Vec3f(location.x, location.y-Sprite.SIZE, location.z));
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
	protected void drawSelection() {
		if (this.isSelected()) {
			OpenGLUtils.addOutline(new Vec2f(location.x, location.y-Sprite.SIZE), new Vec2f(Sprite.SIZE, Sprite.SIZE*2)); // render the red square around selected resources
		}
	}

}
