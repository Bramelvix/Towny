package entity.nondynamic.resources;

import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import graphics.Sprite;
import graphics.SpriteHashtable;
import graphics.opengl.OpenGLUtils;
import map.Level;
import map.Tile;
import util.vectors.Vec2f;
import util.vectors.Vec2i;
import util.vectors.Vec3f;
import util.vectors.Vec3i;

public class Tree extends Resource {

	private Sprite topsprite;
	private boolean hasFruit = false;
	private byte fruitGatheredPercentage = 0;

	// basic constructor
	public Tree(float x, float y, int z, Level level) {
		this(x, y, z, level, RANDOM.nextBoolean(), RANDOM.nextInt(4) == 0);
	}

	public Tree(float x, float y, int z, Level level, boolean isPine, boolean hasFruit) {
		super(x, y, z, level, true, "tree", ItemHashtable.get(1).createInstance());
		this.hasFruit = hasFruit;

		if(!isPine && hasFruit) {
			sprite = SpriteHashtable.get(101);
			topsprite = SpriteHashtable.get(102);
		} else {
			sprite = SpriteHashtable.get(isPine ? 92 : 12);
			topsprite = SpriteHashtable.get(isPine ? 93: 13);
		}

		setVisible(true);
		location.z = -0.8f;
	}

	public boolean hasFruit() {
		return hasFruit;
	}

	@Override
	public boolean work() {
		if (!hasFruit) {
			return super.work();
		}
		if (fruitGatheredPercentage < 100) {
			fruitGatheredPercentage++;
			return false;
		}

		sprite = SpriteHashtable.get(12);
		topsprite = SpriteHashtable.get(13);
		Item cherries = ItemHashtable.get(12).createInstance();
		Vec2i emptySpotCoords = level.getNearestEmptyTile(getTileX(), getTileY());
		cherries.setLocation(emptySpotCoords.x * Tile.SIZE, emptySpotCoords.y * Tile.SIZE, getZ());
		level.addItem(cherries);
		hasFruit  = false;
		setSelected(false);
		return true;
	}

	// render method to render onto the screen
	@Override
	public void render() {
		super.render();
		topsprite.draw(new Vec3f(location.x, location.y - Sprite.SIZE, location.z));
	}

	@Override
	protected void drawSelection() {
		if (this.isSelected()) {
			OpenGLUtils.addOutline(new Vec2f(location.x, location.y - Sprite.SIZE), new Vec2f(Sprite.SIZE, Sprite.SIZE * 2)); // render the red square around selected resources
		}
	}

}
