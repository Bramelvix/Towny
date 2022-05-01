package entity.dynamic.mob;

import entity.dynamic.item.ItemHashtable;
import graphics.SpriteHashtable;
import map.Level;

public class Zombie extends Humanoid {

	private byte animationtimer = 0;

	public Zombie(Level[] levels, float x, float y, int z) {
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
		if (idleTime()) {
			idle();
		}
		move();
	}

	@Override
	public void hit(float damage) {
		super.hit(damage);
		sprite = SpriteHashtable.get(52);
		animationtimer = 30;
	}

	@Override
	public void render() {
		super.render();
		if (getHolding() != null) {
			getHolding().getSprite().draw(location);// renders the item the zombie is holding
		}
	}

	@Override
	public float getDamage() {
		return 0;
	}

}
