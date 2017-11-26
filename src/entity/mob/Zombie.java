package entity.mob;

import entity.item.weapon.Weapon;
import graphics.Screen;
import graphics.Sprite;
import map.Level;

public class Zombie extends Humanoid {
	private byte animationtimer = 0;

	public Zombie(Level level, int x, int y) {
		super(level, x, y);
		this.sprite = Sprite.zombie;
		setName("zombie");
		this.holding = Weapon.getRandomWeapon(this);

	}

	@Override
	public void update() {
		if (animationtimer != 0) {
			animationtimer--;
			if (animationtimer == 0) {
				sprite = Sprite.zombie;
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
		sprite = Sprite.zombiehit;
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
		if (holding != null) {
			screen.renderSprite(x, y, holding.sprite); // renders the item the
														// zombie is holding
		}
	}

	@Override
	public float getDamage() {
		return 0;
	}

}
