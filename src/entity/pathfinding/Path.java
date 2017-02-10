package entity.pathfinding;

import java.util.ArrayList;

//path for the villager to take to a destination
public class Path {
	private ArrayList<Point> steps = new ArrayList<Point>(); // list of steps to
																// the
																// destination
	private boolean arrived = false; // has the villager arrived
	private int xdest, ydest; // x and y coord of the destination

	// getters
	public int getXdest() {
		return xdest;
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

	public void setDest(int x, int y) {
		xdest = x;
		ydest = y;
	}

	public int getLength() {
		return steps.size();
	}

	public boolean isArrived() {
		return arrived;
	}

	// adds a step to the end of the list
	public void appendStep(int x, int y) {
		steps.add(new Point(x, y));
	}

	// adds a step to the front of the list
	public void prependStep(int x, int y) {
		steps.add(0, new Point(x, y));
	}

	//does the list already contain a step
	public boolean contains(int x, int y) {
		return steps.contains(new Point(x, y));
	}

}
