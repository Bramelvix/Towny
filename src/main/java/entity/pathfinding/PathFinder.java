package entity.pathfinding;

import map.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PathFinder {

	private PathFinder() {

	}

	private static final ArrayList<Point> closed = new ArrayList<>();
	private static final ArrayList<Point> open = new ArrayList<>();
	private static Point[][] nodes;

	public static void init(int levelWidth, int levelHeight) {
		nodes = new Point[levelWidth][levelHeight];
		for (int x = 0; x < levelWidth; x++) {
			for (int y = 0; y < levelHeight; y++) {
				nodes[x][y] = new Point(x, y);
			}
		}
	}

	private static Optional<Path> getShortest(Path[] paths) {
		if (paths == null || paths.length < 1) {
			return Optional.empty();
		}
		Path shortest = null;
		for (Path path : paths) {
			if (path != null) {
				shortest = path;
				break;
			}
		}
		for (Path p : paths) {
			if (p != null && shortest != null && p.getLength() < shortest.getLength()) {
				shortest = p;
			}
		}
		return shortest != null ? Optional.of(shortest) : Optional.empty();
	}

	public static Optional<Path> findPathAround(int sx, int sy, int tx, int ty, Level level) {
		List<Path> checked = new ArrayList<>();
		findPath(sx, sy, tx - 1, ty, level).ifPresent(checked::add);
		findPath(sx, sy, tx + 1, ty, level).ifPresent(checked::add);
		findPath(sx, sy, tx, ty - 1, level).ifPresent(checked::add);
		findPath(sx, sy, tx, ty + 1, level).ifPresent(checked::add);
		findPath(sx, sy, tx - 1, ty - 1, level).ifPresent(checked::add);
		findPath(sx, sy, tx + 1, ty + 1, level).ifPresent(checked::add);
		findPath(sx, sy, tx + 1, ty - 1, level).ifPresent(checked::add);
		findPath(sx, sy, tx - 1, ty + 1, level).ifPresent(checked::add);
		return getShortest(checked.toArray(new Path[0]));
	}

	public static Optional<Path> findPath(int sx, int sy, int tx, int ty, Level level) {
		if (sx < 0 || sx > level.width || sy < 0 || sy > level.height || tx < 0 || tx > level.width || ty < 0 || ty > level.height) {
			return Optional.empty();
		}
		if (!level.isWalkAbleTile(tx, ty)) {
			return Optional.empty();
		}
		nodes[tx][ty].setParent(null);
		closed.clear();
		open.clear();
		open.add(nodes[sx][sy]);
		nodes[tx][ty].setParent(null);
		while (!open.isEmpty()) {
			Point current = open.get(0);
			if (current.equals(nodes[tx][ty])) {
				break;
			}
			open.remove(current);
			closed.add(current);
			for (int x = -1; x < 2; x++) {
				for (int y = -1; y < 2; y++) {
					if ((x == 0) && (y == 0)) {
						continue;
					}
					if ((x != 0 && y != 0)) { // DIAGONAL
						continue;
					}
					int xp = x + current.x;
					int yp = y + current.y;

					if (isValidLocation(xp, yp, level)) {
						float nextStepCost = getCost(current.x, current.y, xp, yp);
						Point neighbour = nodes[xp][yp];
						if (nextStepCost < neighbour.cost) {
							open.remove(neighbour);
							closed.remove(neighbour);
						}
						if (!open.contains(neighbour) && !closed.contains(neighbour)) {
							neighbour.cost = nextStepCost;
							open.add(neighbour);
							neighbour.setParent(current);
						}
					}
				}
			}
		}
		if (nodes[tx][ty].getParent() == null) {
			return Optional.empty();
		}
		Path path = new Path(tx, ty);
		Point target = nodes[tx][ty];
		while (!target.equals(nodes[sx][sy])) {
			path.prependStep(target.x, target.y);
			target = target.getParent();
		}
		path.prependStep(sx, sy);
		return Optional.of(path);
	}

	private static boolean isValidLocation(int xp, int yp, Level level) {
		return xp > 0 && xp < level.width && yp > 0 && yp < level.height && level.isWalkAbleTile(xp, yp);
	}

	private static float getCost(int x, int y, int tx, int ty) {
		int dx = tx - x;
		int dy = ty - y;
		return (float) (Math.sqrt((dx * dx) + (dy * dy)));
	}

}
