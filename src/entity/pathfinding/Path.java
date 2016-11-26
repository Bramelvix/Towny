package entity.pathfinding;

import java.util.ArrayList;

public class Path {
	private ArrayList<Point> steps = new ArrayList<Point>();
	private boolean arrived = false;

	public int getX(int index) {
		return steps.get(index).x;
	}

	public int getY(int index) {
		return steps.get(index).y;
	}

	public void appendStep(int x, int y) {
		steps.add(new Point(x, y));
	}

	public void prependStep(int x, int y) {
		steps.add(0, new Point(x, y));
	}

	public Point getStep(int index) {
		if (index == steps.size()) {
			arrived = true;
		} else {
			arrived = false;
		}
		if (index < steps.size()) {
			return steps.get(index);
		} else {
			return null;
		}
	}

	public int getLength() {
		return steps.size();
	}

	public boolean isArrived() {
		return arrived;
	}

	public boolean contains(int x, int y) {
		return steps.contains(new Point(x, y));
	}

}
