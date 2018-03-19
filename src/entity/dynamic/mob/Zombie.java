package entity.dynamic.mob;

import entity.dynamic.item.weapon.Weapon;
import graphics.Screen;
import graphics.SpriteHashtable;
import map.Level;

public class Zombie extends Humanoid {
	private byte animationtimer = 0;

	public Zombie(Level level, int x, int y) {
		super(level, x, y);
        this.sprite = SpriteHashtable.get(51);
		setName("zombie");
		setHolding(Weapon.getRandomWeapon(this));

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
		move(0, -1);
		move(0, -1);
		move(0, -1);
		move(0, -1);
		move(0, -1);

	}

	@Override
	public void render(Screen screen) {
		super.render(screen);
		if (getHolding() != null) {
            screen.renderSprite(x, y, getHolding().sprites.get(0)); // renders the item the zombie is holding
		}
	}

	@Override
	public float getDamage() {
		return 0;
	}

}
