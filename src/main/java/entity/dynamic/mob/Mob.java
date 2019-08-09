package entity.dynamic.mob;

import entity.Entity;
import entity.pathfinding.Direction;
import entity.pathfinding.Path;
import entity.pathfinding.PathFinder;
import entity.pathfinding.Point;
import map.Level;
import map.Tile;

//abstract mob class for villagers and monsters/animals to extend
public abstract class Mob extends Entity {
    public Level[] levels; // level in which the entity is placed
    private int idletimer = getIdleTimer();// timer for the mob to idle
    private int health = 100;
    private int armour = 0;
    private Path movement; // path for the Mob to follow
    private int counter; // counter of steps along the path
    private boolean arrived = false; // has the mob arrived at the path's destination

    // move a mob with a combination of x direction and y direction (both between -1 and 1).
    public void move(int xa, int ya) {
        Direction dir;
        if (xa > 0) {
            if (ya > 0) {
                dir = Direction.DOWN_RIGHT;
            } else {
                dir = (ya < 0) ? Direction.UP_RIGHT : Direction.RIGHT;
            }
        } else {
            if (xa < 0) {
                if (ya > 0) {
                    dir = Direction.DOWN_LEFT;
                } else {
                    dir = (ya < 0) ? Direction.UP_LEFT : Direction.LEFT;
                }

            } else {
                dir = (ya > 0) ? Direction.DOWN : Direction.UP;
            }
        }
        if (!collision(dir)) {
            x += xa;
            y += ya;
        }

    }

    void idle() {
        while (movement == null) {
            movement = getPath((x / Tile.SIZE) + Entity.RANDOM.nextInt(4) - 2, (y / Tile.SIZE) + Entity.RANDOM.nextInt(4) - 2);
        }

    }

    boolean idleTime() {
        if (idletimer <= 0) {
            idletimer = getIdleTimer();
            return true;
        } else {
            idletimer--;
            return false;
        }
    }

    public boolean isMovementNull() {
        return movement == null;
    }

    public void setPath(Path path) {
        movement = path;
        counter = 0;
        arrived = false;
    }
    // method to move the villager
    public void move() {
        if (movement == null) {
            counter = 0;
            return;
        }
        if (arrived) {
            counter++;
            arrived = false;
        }
        if (movement.getLength() == counter) {
            counter = 0;
            movement = null;
            arrived = false;
        } else {
            if (!arrived) {
                Point step = movement.getStep(counter);
                if (step != null) {
                    if (!levels[z].isWalkAbleTile(step.x, step.y)) {
                        int destx = movement.getXdest();
                        int desty = movement.getYdest();
                        movement = getPathAround(destx, desty);
                        return;
                    }
                    moveTo(step.x, step.y);
                    if (onSpot(step.x*Tile.SIZE,step.y*Tile.SIZE,this.z)) {
                        arrived = true;
                    }

                }
            }
        }

    }

    // is the mob around a tile (x and y in pixels)
    public boolean aroundTile(int x, int y, int z) {
        return (this.z == z
                && ((this.x/Tile.SIZE) <= ((x/Tile.SIZE) + 1)) && ((this.x/Tile.SIZE) >= ((x/Tile.SIZE) - 1))
                && ((this.y/Tile.SIZE >= ((y/Tile.SIZE) - 1)) && (this.y/Tile.SIZE <= ((y/Tile.SIZE) + 1))));

    }


    public boolean onSpot(int x, int y, int z) {
        return (this.z == z && this.x == x && this.y == y);
    }

    public Path getPathAround(int x, int y) {
        return PathFinder.findPathAround(this.x / Tile.SIZE, this.y / Tile.SIZE, x, y, levels[z]);
    }

    // getter
    private int getIdleTimer() {
        return Entity.RANDOM.nextInt(5) * 60;
    }

    // constructor
    Mob(Level[] levels) {
        super();
        this.levels = levels;
    }

    public abstract void die();

    // update method needed for game logic
    public abstract void update();

    // pathfinder method
    public Path getPath(int tx, int ty) {
        return PathFinder.findPath(x / Tile.SIZE, y / Tile.SIZE, tx, ty, levels[z]);

    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return 1;
    }

    public abstract float getDamage();

    public void hit(float damage) {
        health -= (damage - armour);
    }

    // calculates collision
    private boolean collision(Direction dir) {
        return ((dir == Direction.DOWN || dir == Direction.DOWN_LEFT || dir == Direction.DOWN_RIGHT)
                || levels[z].getTile((x / Tile.SIZE), ((y + 1) / Tile.SIZE)).isSolid())
                && ((dir == Direction.UP || dir == Direction.UP_LEFT || dir == Direction.UP_RIGHT)
                || levels[z].getTile((x / Tile.SIZE), ((y - 1) / Tile.SIZE)).isSolid())
                && ((dir == Direction.LEFT || dir == Direction.UP_LEFT || dir == Direction.DOWN_LEFT)
                || levels[z].getTile(((x - 1) / Tile.SIZE), (y / Tile.SIZE)).isSolid())
                && ((dir == Direction.RIGHT || dir == Direction.UP_RIGHT || dir == Direction.DOWN_RIGHT)
                || levels[z].getTile(((x + 1) / Tile.SIZE), (y / Tile.SIZE)).isSolid())
                && (levels[z].getTile((x / Tile.SIZE), (y / Tile.SIZE)).isSolid()
                || levels[z].getTile(((x + 1) / Tile.SIZE), ((y + 1) / Tile.SIZE)).isSolid());

    }

    // DO NOT TOUCH THIS. SET THE MOVEMENT TO THE PATH OBJ USE move()!! DO NOT USE!!!
    protected void moveTo(int x, int y) {
        int xmov = Integer.compare(x*Tile.SIZE, this.x);
        int ymov = Integer.compare(y*Tile.SIZE, this.y);
        move(xmov*3, ymov*3);

    }


}
