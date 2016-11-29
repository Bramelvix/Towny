package entity.mob;

import entity.Entity;
import entity.pathfinding.Direction;
import entity.pathfinding.Path;
import entity.pathfinding.PathFinder;
import graphics.Screen;
import graphics.Sprite;
import map.Map;

public abstract class Mob extends Entity {
	public Sprite sprite;
	protected Direction dir;
	protected boolean moving = false;
	private PathFinder finder;

	public void move(int xa, int ya) {
		if (xa > 0) {
			if (ya > 0) {
				dir = Direction.RECHTS_OMLAAG;
			} else {
				if (ya < 0) {
					dir = Direction.LINKS_OMHOOG;
				} else {
					dir = Direction.RECHTS;
				}
			}
		} else {
			if (xa < 0) {
				if (ya > 0) {
					dir = Direction.LINKS_OMLAAG;
				} else {
					if (ya < 0) {
						dir = Direction.LINKS_OMHOOG;
					} else {
						dir = Direction.LINKS;
					}
				}
			} else {
				if (ya > 0) {
					dir = Direction.OMLAAG;
				} else {
					dir = Direction.OMHOOG;
				}
			}
		}
		if (!collision()) {
			x += xa;
			y += ya;
		}

	}

	public abstract void update();

	public Mob(Map level) {
		this.level = level;
		finder = new PathFinder(level);
	}

	public Path getPath(int sx, int sy, int tx, int ty) {
		return finder.findPath(sx, sy, tx, ty);

	}

	private boolean collision() {
		if ((dir != Direction.OMLAAG && dir != Direction.LINKS_OMLAAG && dir != Direction.RECHTS_OMLAAG)
				&& !level.getTile((x >> 4), (y + 1 >> 4)).solid()) {
			return false;
		}
		if ((dir != Direction.OMHOOG && dir != Direction.LINKS_OMHOOG && dir != Direction.RECHTS_OMHOOG)
				&& !level.getTile((x >> 4), (y - 1 >> 4)).solid()) {
			return false;
		}
		if ((dir != Direction.LINKS && dir != Direction.LINKS_OMHOOG && dir != Direction.LINKS_OMLAAG)
				&& !level.getTile((x - 1 >> 4), (y >> 4)).solid()) {
			return false;
		}
		if ((dir != Direction.RECHTS && dir != Direction.RECHTS_OMHOOG && dir != Direction.RECHTS_OMLAAG)
				&& !level.getTile((x + 1 >> 4), (y >> 4)).solid()) {
			return false;
		}

		return level.getTile((x >> 4), (y >> 4)).solid() || level.getTile((x + 1 >> 4), (y + 1 >> 4)).solid();

	}

	public abstract void render(Screen screen);

}
