package entity.pathfinding;

import java.util.ArrayList;

//path for the villager to take to a destination
public class Path {

	private final ArrayList<Point> steps = new ArrayList<>(); // list of steps to the destination
	private final int xdest;
	private final int ydest; // x and y coord of the destination

	// getters
	public int getXdest() {
		return xdest;
	}

	public int getStepsSize() {
		return steps.size();
	}

	public int getYdest() {
		return ydest;
	}

	public int getX(int index) {
		return steps.get(index).x;
	}

	public int getY(int index) {
		return steps.get(index).y;
	}

	public Point getStep(int index) {
		if (index < steps.size()) {
			return steps.get(index);
		}
		return null;
	}

	Path(int x, int y) {
		xdest = x;
		ydest = y;
	}

	public int getLength() {
		return steps.size();
	}

	// adds a step to the front of the list
	void prependStep(int x, int y) {
		steps.addFirst(new Point(x, y));
	}
}
