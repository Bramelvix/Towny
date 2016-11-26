package entity.pathfinding;

public class Point {
	public int x;
	public int y;
	public float cost;
	private Point parent;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		cost = 1;
	}


	public boolean equals(Object o) {
		if (o==null) return false;
		return this.x == ((Point)o).x && this.y == ((Point)o).y;
	}
	public int hashCode() {
		return x*y;
	}
	public Point getParent() {
		return parent;
	}
	public void setParent(int x, int y) {
		parent = new Point(x, y);
	}
	public void setParent(Point p) {
		parent = p;
	}

}
