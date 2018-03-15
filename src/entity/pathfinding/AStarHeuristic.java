package entity.pathfinding;

//method to calculate the cost of moving over a tile (can be higher for mud for instance)
public interface AStarHeuristic {
    float getCost(int x, int y, int tx, int ty);

}
