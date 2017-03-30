package entity.mob;

import entity.item.Item;
import entity.item.weapon.Weapon;
import entity.pathfinding.Path;
import entity.pathfinding.Point;
import graphics.Screen;
import graphics.Sprite;
import map.Level;

public class Zombie extends Mob {
	public Item holding;
	public Path movement; // path for the villager to follow
	private int counter; // counter of steps along the path
	private boolean arrived = false;
	private byte animationtimer = 0;

	public Zombie(Level level, int x, int y) {
		super(level);
		while (!level.isWalkAbleTile(x >> 4, y >> 4)) {
			x += 16;
			y += 16;
		}
		this.x = x;
		this.y = y;
		this.sprite = Sprite.zombie;
		setName("zombie");
		this.holding = Weapon.getRandomWeapon(this, random);

	}

	private boolean idleTime() {
		if (idletimer <= 0) {
			idletimer = getIdleTimer();
			return true;
		} else {
			idletimer--;
			return false;
		}
	}

	public void idle() {
		while (movement == null) {
			movement = getPath((x >> 4) + random.nextInt(4) - 2, (y >> 4) + random.nextInt(4) - 2);
		}

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

	// method to move the zombie
	public void move() {
		if (movement == null) {
			counter = 0;
			return;
		}
		if (arrived) {
			counter++;
			arrived = false;
		}
		if (movement.getLength() == counter) {
			counter = 0;
			movement = null;
			arrived = false;
			return;
		} else {
			if (!arrived) {
				Point step = movement.getStep(counter);
				if (step == null || !level.isWalkAbleTile(step.x, step.y)) {
					int destx = movement.getXdest();
					int desty = movement.getYdest();
					movement = getShortest(destx, desty);
					return;
				}
				moveTo(step.x << 4, step.y << 4);
				if (x == step.x << 4 && y == step.y << 4) {
					arrived = true;
				}

			}
		}

	}

	protected final void moveTo(int x, int y) {
		super.moveTo(x, y);
		if (!(holding == null)) {
			holding.setX(x);
			holding.setY(y);
		}

	}

	// resets the zombie's path
	public void resetMove() {
		counter = 0;
		arrived = false;
		movement = null;
	}

	@Override
	public void render(Screen screen) {
		screen.renderSprite(x, y, this.sprite); // renders the body
		if (holding != null)
			screen.renderSprite(x, y, holding.sprite); // renders the item the
														// zombie is holding
		screen.renderSelection(x, y, this); // render the red square around
											// selected zombies

	}

	@Override
	public void die() {
		if (holding != null) {
			level.addItem((Weapon) holding);
		}

	}

	@Override
	public float getDamage() {
		return 0;
	}

}
