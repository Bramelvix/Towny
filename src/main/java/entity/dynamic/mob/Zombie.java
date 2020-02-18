package entity.dynamic.mob;

import entity.dynamic.item.ItemHashtable;
import graphics.SpriteHashtable;
import map.Level;
import util.vectors.Vec2f;

public class Zombie extends Humanoid {

	private byte animationtimer = 0;

	public Zombie(Level[] levels, int x, int y, int z) {
		super(levels, x, y, z);
		sprite = SpriteHashtable.get(51);
		setName("zombie");
		setHolding(ItemHashtable.getRandomWeapon());
	}

	@Override
	public void update() {
		if (animationtimer != 0) {
			animationtimer--;
			if (animationtimer == 0) {
				sprite = SpriteHashtable.get(51);
			}
		}
		if (idleTime()) { idle(); }
		move();
	}

	@Override
	public void hit(float damage) {
		super.hit(damage);
		sprite = SpriteHashtable.get(52);
		animationtimer = 30;
	}

	@Override
	public void render(Vec2f offset) {
		super.render(offset);
		if (getHolding() != null) {
			getHolding().sprite.draw(new Vec2f(x,y), offset);// renders the item the zombie is holding
		}
	}

	@Override
	public float getDamage() {
		return 0;
	}

}
