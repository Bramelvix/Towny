package entity.pathfinding;

import util.Vector2I;

public class Point {
    public Vector2I position;
    float cost;
    private Point parent;

    // a point on the map (combination of x and y of a tile)
    Point(int x, int y) {
        this.position = new Vector2I(x, y);
        cost = 1;
    }

    // rewritten the equals method to return true if the X and Y are the same,
    // because points are not unique
    public boolean equals(Object o) {
        return o instanceof Point && this.position.x == ((Point) o).position.x && this.position.y == ((Point) o).position.y;
    }

    public int hashCode() {
        return position.x * position.y;
    }

    //getter
    Point getParent() {
        return parent;
    }

    //setter

    void setParent(Point p) {
        parent = p;
    }

}
