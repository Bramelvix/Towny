package entity.pathfinding;

import java.util.ArrayList;

//path for the villager to take to a destination
public class Path {
    private ArrayList<Point> steps = new ArrayList<>(); // spritesheets of steps to
																// the
																// destination
	private boolean arrived = false; // has the villager arrived
	private int xdest, ydest; // x and y coord of the destination

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
        arrived = index == steps.size();
		if (index < steps.size()) {
			return steps.get(index);
		} else {
			return null;
		}
	}

    public Path(int x, int y) {
		xdest = x;
		ydest = y;
	}

	public int getLength() {
		return steps.size();
	}

	public boolean isArrived() {
		return arrived;
	}

    // adds a step to the end of the spritesheets
	public void appendStep(int x, int y) {
		steps.add(new Point(x, y));
	}

    // adds a step to the front of the spritesheets
	public void prependStep(int x, int y) {
		steps.add(0, new Point(x, y));
	}

    //does the spritesheets already contain a step
	public boolean contains(int x, int y) {
		return steps.contains(new Point(x, y));
	}

}
