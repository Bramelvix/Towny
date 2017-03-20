package entity.mob;

import entity.Entity;
import entity.pathfinding.Direction;
import entity.pathfinding.Path;
import entity.pathfinding.PathFinder;
import graphics.Screen;
import graphics.Sprite;
import map.Level;

//abstract mob class for villagers and monsters/animals to extend
public abstract class Mob extends Entity {
	public Sprite sprite;
	protected Direction dir;
	protected boolean moving = false;
	private static PathFinder finder;
	private int health = 100;
	private int armour = 0;

	// move a mob with a combination of x
	// direction and y direction (both
	// between -1 and 1). example: for x -1
	// is left and 1 is right.
	public void move(int xa, int ya) { // extreem korte notatie
		dir = (xa > 0) ? (ya > 0) ? Direction.RECHTS_OMLAAG : (ya < 0) ? Direction.RECHTS_OMHOOG : Direction.RECHTS
				: (xa < 0) ? (ya > 0) ? Direction.LINKS_OMLAAG : (ya < 0) ? Direction.LINKS_OMHOOG : Direction.LINKS
						: (ya > 0) ? Direction.OMLAAG : Direction.OMHOOG;
		if (!collision()) {
			x += xa;
			y += ya;
		}

	}

	// constructor
	public Mob(Level level, int x, int y) {
		super(x, y);
		this.level = level;
		finder = new PathFinder(level);
	}

	public Mob(Level level) {
		super();
		this.level = level;
		finder = new PathFinder(level);
	}

	// update method needed for game logic
	public abstract void update();

	// pathfinder method
	public Path getPath(int tx, int ty) {
		return finder.findPath(x >> 4, y >> 4, tx, ty);

	}

	public int getHealth() {
		return health;
	}

	public void hit(int damage) {
		health -= (damage - armour);
	}

	// calculates collision
	private boolean collision() {
		if (((dir != Direction.OMLAAG && dir != Direction.LINKS_OMLAAG && dir != Direction.RECHTS_OMLAAG)
				&& !level.getTile((x >> 4), (y + 1 >> 4)).solid())
				|| ((dir != Direction.OMHOOG && dir != Direction.LINKS_OMHOOG && dir != Direction.RECHTS_OMHOOG)
						&& !level.getTile((x >> 4), (y - 1 >> 4)).solid())
				|| ((dir != Direction.LINKS && dir != Direction.LINKS_OMHOOG && dir != Direction.LINKS_OMLAAG)
						&& !level.getTile((x - 1 >> 4), (y >> 4)).solid())
				|| ((dir != Direction.RECHTS && dir != Direction.RECHTS_OMHOOG && dir != Direction.RECHTS_OMLAAG)
						&& !level.getTile((x + 1 >> 4), (y >> 4)).solid()))
			return false;

		return level.getTile((x >> 4), (y >> 4)).solid() || level.getTile((x + 1 >> 4), (y + 1 >> 4)).solid();

	}

	// method to render onto the screen
	public abstract void render(Screen screen);

}
