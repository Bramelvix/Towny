package entity.dynamic.mob;

import entity.Entity;
import entity.pathfinding.Direction;
import entity.pathfinding.Path;
import entity.pathfinding.PathFinder;
import entity.pathfinding.Point;
import map.Level;
import map.Tile;

import java.util.Optional;

//abstract mob class for villagers and monsters/animals to extend
public abstract class Mob extends Entity {

	public Level[] levels; // level in which the entity is placed
	private int idletimer = getIdleTimer();// timer for the mob to idle
	private int health = 100;
	private int armour = 0;
	private Path movement; // path for the Mob to follow
	private int counter; // counter of steps along the path
	private boolean arrived = false; // has the mob arrived at the path's destination

	// move a mob with a combination of x direction and y direction (both between -1 and 1).
	public void move(int xa, int ya) {
		Direction dir;
		if (xa > 0) {
			if (ya > 0) {
				dir = Direction.DOWN_RIGHT;
			} else {
				dir = (ya < 0) ? Direction.UP_RIGHT : Direction.RIGHT;
			}
		} else {
			if (xa < 0) {
				if (ya > 0) {
					dir = Direction.DOWN_LEFT;
				} else {
					dir = (ya < 0) ? Direction.UP_LEFT : Direction.LEFT;
				}
			} else {
				dir = (ya > 0) ? Direction.DOWN : Direction.UP;
			}
		}
		if (!collision(dir)) {
			location.x += xa;
			location.y += ya;
		}
	}

	void idle() {
		while (movement == null) {
			getPath(
				getTileX() + Entity.RANDOM.nextInt(4) - 2,
				getTileY() + Entity.RANDOM.nextInt(4) - 2
			).ifPresent(path -> movement = path);
		}
	}

	boolean idleTime() {
		if (idletimer <= 0) {
			idletimer = getIdleTimer();
			return true;
		} else {
			idletimer--;
			return false;
		}
	}

	public boolean isMovementNull() {
		return movement == null;
	}

	public void setPath(Path path) {
		movement = path;
		counter = 0;
		arrived = false;
	}



	// method to move the villager
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
		} else {
			if (!arrived) {
				Point step = movement.getStep(counter);
				if (step != null) {
					if (!levels[z].isWalkAbleTile(step.x, step.y)) {
						int destx = movement.getXdest();
						int desty = movement.getYdest();
						getPathAround(destx, desty).ifPresent(path -> movement = path);
						return;
					}
					moveTo(step.x, step.y);
					if (onSpot(step.x, step.y, this.z)) {
						arrived = true;
					}
				}
			}
		}
	}

	// is the mob around a tile (x and y in tile numbers)
	public boolean aroundTile(int x, int y, int z) {
		return (
				this.z == z
				&& (getTileX() <= (x + 1)) && (getTileX() >= (x - 1))
				&& (getTileY() >= (y - 1)) && (getTileY() <= (y + 1))
		);
	}

	public boolean onSpot(int x, int y, int z) {
		return (this.z == z && this.location.x == x * Tile.SIZE && this.location.y == y * Tile.SIZE);
	}

	public Optional<Path> getPathAround(int x, int y) {
		return PathFinder.findPathAround(getTileX(), getTileY(), x, y, levels[z]);
	}

	// getter
	private int getIdleTimer() {
		return Entity.RANDOM.nextInt(5) * 60;
	}

	// constructor
	Mob(Level[] levels) {
		super();
		this.levels = levels;
	}

	public abstract void die();

	// update method needed for game logic
	public abstract void update();

	// pathfinder method
	public Optional<Path> getPath(int tx, int ty) {
		return PathFinder.findPath(getTileX(), getTileY(), tx, ty, levels[z]);
	}

	public int getHealth() {
		return health;
	}

	public int getSpeed() {
		return 1;
	}

	public abstract float getDamage();

	public void hit(float damage) {
		health -= (damage - armour);
	}

	// calculates collision
	private boolean collision(Direction dir) {
		return (
			(
				dir == Direction.DOWN
				|| dir == Direction.DOWN_LEFT
				|| dir == Direction.DOWN_RIGHT
				|| levels[z].getTile(getTileX(), getTileY() + 1).isSolid()
			) && (
				dir == Direction.UP || dir == Direction.UP_LEFT || dir == Direction.UP_RIGHT
				|| levels[z].getTile(getTileY(), getTileY() - 1).isSolid()
			) && (
				dir == Direction.LEFT || dir == Direction.UP_LEFT || dir == Direction.DOWN_LEFT
				|| levels[z].getTile(getTileX() - 1, getTileY()).isSolid()
			) && (
				dir == Direction.RIGHT || dir == Direction.UP_RIGHT || dir == Direction.DOWN_RIGHT
				|| levels[z].getTile(getTileX() + 1, getTileY()).isSolid()
			) && (
				levels[z].getTile(getTileX(), getTileY()).isSolid()
				|| levels[z].getTile(getTileX() + 1, getTileY() + 1).isSolid()
			)
		);
	}

	// DO NOT TOUCH THIS. SET THE MOVEMENT TO THE PATH OBJ USE move()!! DO NOT USE!!!
	protected void moveTo(int x, int y) {
		int xmov = Integer.compare((int) (x*Tile.SIZE), (int) this.location.x);
		int ymov = Integer.compare((int) (y*Tile.SIZE), (int) this.location.y);
		move(xmov*3, ymov*3);
	}

}
