package entity.building.wall;

import entity.building.BuildAbleObject;
import entity.item.Item;
import graphics.Sprite;
import graphics.SpriteHashtable;
import map.Level;
import map.Tile;

public class Wall extends BuildAbleObject {
    private Sprite[] sprites;
    private boolean door;
    private WallType type;

    // basic constructor
    public Wall(WallType type) {
        this(type, false);
    }

    private void decideSprites(WallType type, boolean door) {
        this.type = type;
        String name = "";
        switch (type) {
            case WOOD:
                name = name.concat("wooden ");
                if (door) {
                    sprites = SpriteHashtable.WOODDOORSPRITES;
                } else {
                    sprites = SpriteHashtable.WOODWALLSPRITES;
                }
                break;
            case STONE:
                name = name.concat("stone ");
                if (door) {
                    sprites = SpriteHashtable.STONEDOORSPRITES;
                } else {
                    sprites = SpriteHashtable.STONEWALLSPRITES;
                }
                break;
        }
        name = name.concat(door ? "door" : "wall");
        setName(name);
    }

    public Wall(WallType type, boolean door) {
        super();
        this.door = door;
        decideSprites(type, door);
    }

    // checks the 4 sides of this wall to see if there are walls next to it. The sprite is decided based on this.
    // this method has a boolean that stops the walls next to this wall to retrigger checking the sides of this wall, which would create an infinite
    // loop of walls checking eachother again and again
    private void checkSides(boolean eerstekeer) {
        Wall left = level.getWallOn((x - Tile.SIZE), y);
        Wall right = level.getWallOn((x + Tile.SIZE), y);
        Wall up = level.getWallOn(x, (y - Tile.SIZE));
        Wall down = level.getWallOn(x, (y + Tile.SIZE));
        Wall upLeft = level.getWallOn(x - Tile.SIZE, (y - Tile.SIZE));
        Wall downLeft = level.getWallOn(x - Tile.SIZE, (y + Tile.SIZE));
        Wall upRight = level.getWallOn((x + Tile.SIZE), y - Tile.SIZE);
        Wall downRight = level.getWallOn((x + Tile.SIZE), y + Tile.SIZE);
        boolean leftHasWall = decideToCheckSides(left, eerstekeer);
        boolean rightHasWall = decideToCheckSides(right, eerstekeer);
        boolean topHasWall = decideToCheckSides(up, eerstekeer);
        boolean bottomHasWall = decideToCheckSides(down, eerstekeer);
        boolean upRightHasWall = decideToCheckSides(upRight, eerstekeer);
        boolean downRightHasWall = decideToCheckSides(downRight, eerstekeer);
        boolean upLeftHasWall = decideToCheckSides(upLeft, eerstekeer);
        boolean downLeftHasWall = decideToCheckSides(downLeft, eerstekeer);
        decideSprite(leftHasWall, rightHasWall, topHasWall, bottomHasWall, upRightHasWall, downRightHasWall, upLeftHasWall, downLeftHasWall);

    }

    private boolean decideToCheckSides(Wall wall, boolean eerstekeer) {
        if (wall != null) {
            if (eerstekeer) {
                wall.checkSides(false);
            }
            return true;
        }
        return false;
    }

    // Checksides method for the walls around this wall
    private void checkSides() {
        checkSides(true);
    }

    // called by villagers when they start building the wall.
    public boolean initialiseFails(int x, int y, Item material, Level level) {
        boolean initGelukt = !super.initialiseFails(x, y, material, level);
        if (initGelukt) {
            checkSides();
        }
        if (door) {
            setOpened(true);
        }
        return !initGelukt;

    }

    // decide the sprite for the wall, depending on the other 4 sides next to
    // the wall
    private void decideSprite(boolean leftHasWall, boolean rightHasWall, boolean topHasWall, boolean bottomHasWall, boolean upRightHasWall, boolean downRightHasWall, boolean upLeftHasWall, boolean downLeftHasWall) {
        sprite = sprites[15];
        if (topHasWall) {
            if (bottomHasWall) {
                sprite = sprites[1];
                if (leftHasWall) {
                    sprite = sprites[2];
                    if (!door && upLeftHasWall && downLeftHasWall) {
                        sprite = sprites[19];
                    }
                    if (rightHasWall) {
                        sprite = sprites[3];
                        if (!door && upLeftHasWall && upRightHasWall && downLeftHasWall && downRightHasWall) {
                            sprite = sprites[16];
                        }
                    }
                } else {
                    if (rightHasWall) {
                        sprite = sprites[4];
                        if (!door && upRightHasWall && downRightHasWall) {
                            sprite = sprites[18];
                        }
                    }
                }
            } else {
                if (leftHasWall) {
                    sprite = sprites[5];
                    if (!door && upLeftHasWall) {
                        sprite = sprites[23];
                    }
                    if (rightHasWall) {
                        sprite = sprites[6];
                        if (!door && upLeftHasWall && upRightHasWall) {
                            sprite = sprites[17];
                        }
                    }
                } else {
                    if (rightHasWall) {
                        sprite = sprites[7];
                        if (!door && upRightHasWall) {
                            sprite = sprites[21];
                        }
                    } else {
                        sprite = sprites[0];
                    }
                }
            }
        } else {
            if (bottomHasWall) {
                sprite = sprites[8];
                if (leftHasWall) {
                    sprite = sprites[9];
                    if (!door && downLeftHasWall) {
                        sprite = sprites[24];
                    }
                    if (rightHasWall) {
                        sprite = sprites[10];
                        if (!door && downLeftHasWall && downRightHasWall) {
                            sprite = sprites[20];
                        }
                    }

                } else {
                    if (rightHasWall) {
                        sprite = sprites[11];
                        if (!door && downRightHasWall) {
                            sprite = sprites[22];
                        }
                    }
                }
            } else {
                if (leftHasWall) {
                    sprite = sprites[12];
                    if (rightHasWall) {
                        sprite = sprites[13];
                    }
                } else {
                    if (rightHasWall) {
                        sprite = sprites[14];
                    }
                }
            }
        }
    }

    @Override
    public BuildAbleObject clone() {
        return new Wall(this.type, this.door);
    }


}
