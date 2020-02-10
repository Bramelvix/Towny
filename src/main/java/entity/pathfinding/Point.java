package entity.pathfinding;

public class Point {

	public int x, y;
	float cost;
	private Point parent;

	// a point on the map (combination of x and y of a tile)
	Point(int x, int y) {
		this.x = x;
		this.y = y;
		cost = 1;
	}

	// rewritten the equals method to return true if the X and Y are the same,
	// because points are not unique
	public boolean equals(Object o) {
		return o instanceof Point && this.x == ((Point) o).x && this.y == ((Point) o).y;
	}

	public int hashCode() {
		return x * y;
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
