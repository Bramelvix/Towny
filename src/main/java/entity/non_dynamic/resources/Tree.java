package entity.non_dynamic.resources;

import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import graphics.Sprite;
import graphics.SpriteHashtable;
import graphics.opengl.OpenGLUtils;
import map.Level;
import sound.Sound;
import util.vectors.Vec2f;
import util.vectors.Vec3f;

public class Tree extends Resource {

	private final Sprite topsprite;

	// basic constructor
	public Tree(float x, float y, int z, Level level) {
		super(x, y, z, level, true, "tree");
		sprite = SpriteHashtable.get(12);
		topsprite = SpriteHashtable.get(13);
		setVisible(true);
		location.z = -0.8f;
	}

	// render method to render onto the screen
	@Override
	public void render() {
		super.render();
		topsprite.draw(new Vec3f(location.x, location.y - Sprite.SIZE, location.z));
	}

	@Override
	protected Item getDroppedResource() {
		return ItemHashtable.get(1);
	}

	@Override
	protected void drawSelection() {
		if (this.isSelected()) {
			OpenGLUtils.addOutline(new Vec2f(location.x, location.y - Sprite.SIZE), new Vec2f(Sprite.SIZE, Sprite.SIZE * 2)); // render the red square around selected resources
		}
	}

}
