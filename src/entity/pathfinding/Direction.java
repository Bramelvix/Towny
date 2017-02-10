package entity.pathfinding;

//different directions for 
public enum Direction {

	OMHOOG(1), RECHTS_OMHOOG(2), RECHTS(3), RECHTS_OMLAAG(4), OMLAAG(5), LINKS_OMLAAG(6), LINKS(7), LINKS_OMHOOG(8);
	
	private final int n;

	private Direction(int n) {
		this.n = n;
	}

	public int getNum() {
		return n;
	}

}
