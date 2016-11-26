package entity.pathfinding;

import entity.mob.Mob;
import map.Map;

public interface AStarHeuristic {
	public float getCost(int x, int y, int tx, int ty);

}
