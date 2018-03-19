package entity.nonDynamic.building.wall;

import entity.nonDynamic.building.BuildAbleObject;
import entity.dynamic.item.Item;
import graphics.Sprite;
import graphics.SpriteHashtable;
import map.Level;
import map.Tile;

import java.util.ArrayList;

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
    private void decideSprite(boolean leftHasWall, boolean rightHasWall, boolean topHasWall, boolean bottomHasWall, boolean topRightHasWall, boolean bottomRightHasWall, boolean topLeftHasWall, boolean bottomLeftHasWall) {
        //initial sprite
        spriteList = new ArrayList<>();
        if (door) {
            if (topHasWall || bottomHasWall) {
                spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 47 : 45));
            } else {
                spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 46 : 44));
            }
        }
        spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 14 : 186));

        //check if places are empty
        if (!topHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 17 : 189));
        if (!rightHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 16 : 188));
        if (!bottomHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 18 : 190));
        if (!leftHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 15 : 187));

        // top right corner
        if (!topHasWall && !rightHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 20 : 192));
        else if (!rightHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 25 : 197));
        else if (!topHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 27 : 199));
        else if (!topRightHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 36 : 208));

        // bottom right corner

        if (!bottomHasWall && !rightHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 22 : 194));
        else if (!rightHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 26 : 198));
        else if (!bottomHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 34 : 206));
        else if (!bottomRightHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 38 : 210));


        // bottom left corner
        if (!bottomHasWall && !leftHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 21 : 193));
        else if (!leftHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 23 : 195));
        else if (!bottomHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 33 : 205));
        else if (!bottomLeftHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 37 : 209));

        // top left corner
        if (!topHasWall && !leftHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 19 : 191));
        else if (!leftHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 24 : 196));
        else if (!topHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 28 : 200));
        else if (!topLeftHasWall) spriteList.add(SpriteHashtable.get(type == WallType.STONE ? 35 : 207));

    }

    @Override
    public BuildAbleObject clone() {
        return new Wall(this.type, this.door);
    }


}
