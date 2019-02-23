package entity.pathfinding;

import java.util.ArrayList;

import map.Level;

public class PathFinder {
    private static ArrayList<Point> closed = new ArrayList<>();
    private static ArrayList<Point> open = new ArrayList<>();
    private static Point[][] nodes;

    public static void init(int levelWidth, int levelHeight) {
        nodes = new Point[levelWidth][levelHeight];
        for (int x = 0; x < levelWidth; x++) {
            for (int y = 0; y < levelHeight; y++) {
                nodes[x][y] = new Point(x, y);
            }
        }
    }

    private static Path getShortest(Path[] paths) {
        if (paths != null) {
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
            return shortest;
        }
        return null;
    }

    public static Path findPathAround(int sx, int sy, int tx, int ty, Level level) {
        return getShortest(new Path[]{findPath(sx, sy, tx - 1, ty, level), findPath(sx, sy, tx + 1, ty, level),
                findPath(sx, sy, tx, ty - 1, level), findPath(sx, sy, tx, ty + 1, level), findPath(sx, sy, tx - 1, ty - 1, level),
                findPath(sx, sy, tx + 1, ty + 1, level), findPath(sx, sy, tx + 1, ty - 1, level), findPath(sx, sy, tx - 1, ty + 1, level)});
    }

    public static Path findPath(int sx, int sy, int tx, int ty, Level level) {
        if (sx < 0 || sx > level.width || sy < 0 || sy > level.height || tx < 0 || tx > level.width || ty < 0 || ty > level.height) {
            return null;
        }
        if (!level.isWalkAbleTile(tx, ty)) {
            return null;

        }
        nodes[tx][ty].setParent(null);
        closed.clear();
        open.clear();
        open.add(nodes[sx][sy]);
        nodes[tx][ty].setParent(null);
        while (open.size() != 0) {
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
            return null;
        }
        Path path = new Path(tx, ty);
        Point target = nodes[tx][ty];
        while (!target.equals(nodes[sx][sy])) {
            path.prependStep(target.x, target.y);
            target = target.getParent();

        }
        path.prependStep(sx, sy);
        return path;

    }

    private static boolean isValidLocation(int xp, int yp, Level level) {
        return xp > 0 && xp < level.width && yp > 0 && yp < level.height && level.isWalkAbleTile(xp, yp);
    }

    private static float getCost(int x, int y, int tx, int ty) {
        float dx = tx - x;
        float dy = ty - y;
        return (float) (Math.sqrt((dx * dx) + (dy * dy)));
    }

}
