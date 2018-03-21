package entity.dynamic.mob;

import entity.Entity;
import entity.pathfinding.Direction;
import entity.pathfinding.Path;
import entity.pathfinding.PathFinder;
import entity.pathfinding.Point;
import graphics.Screen;
import map.Level;
import map.Tile;

//abstract mob class for villagers and monsters/animals to extend
public abstract class Mob extends Entity {
    public Level[] levels; // level in which the entity is placed
    private int idletimer = getIdleTimer();// timer for the mob to idle
    private Direction dir;
    private int health = 100;
    private int armour = 0;
    private Path movement; // path for the Mob to follow
    private int counter; // counter of steps along the path
    private boolean arrived = false; // has the mob arrived at the path's destination

    // move a mob with a combination of x direction and y direction (both between -1 and 1).
    public void move(int xa, int ya) {
        if (xa > 0) {
            if (ya > 0) {
                dir = Direction.RECHTS_OMLAAG;
            } else {
                dir = (ya < 0) ? Direction.RECHTS_OMHOOG : Direction.RECHTS;
            }
        } else {
            if (xa < 0) {
                if (ya > 0) {
                    dir = Direction.LINKS_OMLAAG;
                } else {
                    dir = (ya < 0) ? Direction.LINKS_OMHOOG : Direction.LINKS;
                }

            } else {
                dir = (ya > 0) ? Direction.OMLAAG : Direction.OMHOOG;
            }
        }
        if (!collision()) {
            x += xa;
            y += ya;
        }

    }

    void idle() {
        while (movement == null) {
            movement = getPath((x / 16) + Entity.RANDOM.nextInt(4) - 2, (y / 16) + Entity.RANDOM.nextInt(4) - 2);
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

    // resets the mob's path
    public void resetMove() {
        counter = 0;
        arrived = false;
        movement = null;
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
                        movement = getShortest(destx, desty);
                        return;
                    }
                    moveTo(step.x * 16, step.y * 16);
                    if (x == step.x * 16 && y == step.y * 16) {
                        arrived = true;
                    }

                }
            }
        }

    }

    // is the mob around a tile (x and y in pixels)
    public boolean aroundTile(int endx, int endy, int endz) {
        return (this.z == endz && (this.x <= ((endx + 16))) && (this.x >= ((endx - 16)))
                && ((this.y >= ((endy - 16))) && (this.y <= ((endy + 16)))));

    }


    public boolean onSpot(int x, int y, int z) {
        return (this.z == z && this.x / 16 == x / 16 && this.y / 16 == y / 16);
    }

    // pathfinder

    public Path getPath(Tile tile) {
        return getPath(tile.x, tile.y);
    }

    public Path getShortest(int x, int y) {
        return PathFinder
                .getShortest(new Path[]{getPath(x - 1, y), getPath(x + 1, y), getPath(x, y - 1), getPath(x, y + 1)});
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
        return PathFinder.findPath(x / 16, y / 16, tx, ty, levels[z]);

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
    private boolean collision() {
        return ((dir == Direction.OMLAAG || dir == Direction.LINKS_OMLAAG || dir == Direction.RECHTS_OMLAAG)
                ||
                levels[z].getTile((x / 16), ((y + 1) / 16)).solid())
                && ((dir == Direction.OMHOOG || dir == Direction.LINKS_OMHOOG || dir == Direction.RECHTS_OMHOOG)
                || levels[z].getTile((x / 16), ((y - 1) / 16)).solid())
                && ((dir == Direction.LINKS || dir == Direction.LINKS_OMHOOG || dir == Direction.LINKS_OMLAAG)
                || levels[z].getTile(((x - 1) / 16), (y / 16)).solid())
                && ((dir == Direction.RECHTS || dir == Direction.RECHTS_OMHOOG || dir == Direction.RECHTS_OMLAAG)
                || levels[z].getTile(((x + 1) / 16), (y / 16)).solid()) && (levels[z].getTile((x / 16), (y / 16)).solid() || levels[z].getTile(((x + 1) / 16), ((y + 1) / 16)).solid());

    }

    // DO NOT TOUCH THIS. SET THE MOVEMENT TO THE PATH OBJ USE move()!! DO NOT
    // USE!!!
    protected void moveTo(int x, int y) {
        int xmov, ymov;
        xmov = Integer.compare(x, this.x);
        ymov = Integer.compare(y, this.y);
        move(xmov, ymov);

    }

    // method to render onto the screen
    public void render(Screen screen) {
        screen.renderMultiSprite(x, y, this.sprites); // renders the body
    }
}
