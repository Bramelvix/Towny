package graphics;

import util.vectors.Vec2f;
import util.vectors.Vec3f;

public class AnimationSprite implements Sprite {
	private final StaticSprite[] sprites;
	private final int[] counters;
	private int current = 0;
	private int frameCounter = 0;

	public AnimationSprite(StaticSprite[] sprites, int[] counters) {
		this.sprites = sprites;
		this.counters = counters;
	}

	@Override
	public int getAvgColour() {
		return sprites[current].getAvgColour();
	}

	@Override
	public Vec2f getTexCoords() {
		return sprites[current].getTexCoords();
	}

	@Override
	public void draw(Vec3f pos) {
		sprites[current].draw(pos);
		frameCounter++;
		if (frameCounter > counters[current]) {
			frameCounter = 0;
			current++;
			if (current >= counters.length) {
				current = 0;
			}
		}
	}
}
