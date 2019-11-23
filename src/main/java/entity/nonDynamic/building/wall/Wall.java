package entity.nonDynamic.building.wall;

import entity.nonDynamic.building.BuildAbleObject;
import graphics.Sprite;
import graphics.SpriteHashtable;
import map.Level;
import map.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Wall extends BuildAbleObject {
    private boolean door;
    private WallType type;
    private static HashMap<List<Sprite>, Sprite> dynamicSpriteList = new HashMap<>();

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
                break;
            case STONE:
                name = name.concat("stone ");
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
    private void checkSides(boolean firstTime) {
        Optional<Wall> left = level.getWallOn(x/Tile.SIZE - 1, y/Tile.SIZE);
        Optional<Wall> right = level.getWallOn(x/Tile.SIZE + 1, y/Tile.SIZE);
        Optional<Wall> up = level.getWallOn(x/Tile.SIZE, (y/Tile.SIZE - 1));
        Optional<Wall> down = level.getWallOn(x/Tile.SIZE, (y/Tile.SIZE + 1));
        Optional<Wall> upLeft = level.getWallOn(x/Tile.SIZE - 1, (y/Tile.SIZE - 1));
        Optional<Wall> downLeft = level.getWallOn(x/Tile.SIZE - 1, (y/Tile.SIZE + 1));
        Optional<Wall> upRight = level.getWallOn((x/Tile.SIZE + 1), y/Tile.SIZE - 1);
        Optional<Wall> downRight = level.getWallOn((x/Tile.SIZE + 1), y/Tile.SIZE + 1);
        if (firstTime) {
            left.ifPresent(Wall::checkSides);
            right.ifPresent(Wall::checkSides);
            up.ifPresent(Wall::checkSides);
            down.ifPresent(Wall::checkSides);
            upRight.ifPresent(Wall::checkSides);
            downRight.ifPresent(Wall::checkSides);
            downLeft.ifPresent(Wall::checkSides);
        }

        decideSprite(
                left.isPresent(), right.isPresent(), up.isPresent(), down.isPresent(), upRight.isPresent(),
                downRight.isPresent(), upLeft.isPresent(), downLeft.isPresent()
        );

    }

    // Checksides method for the walls around this wall
    private void checkSides() {
        checkSides(false);
    }

    // called by villagers when they start building the wall.
    public void initialise(int x, int y, Level[] levels, int depth) {
        super.initialise(x, y, levels, depth);
        checkSides(true);
        if (door) {
            setOpened(true);
        }
    }

    // decide the sprite for the wall, depending on the other 4 sides next to
    // the wall
    private void decideSprite(boolean leftHasWall, boolean rightHasWall, boolean topHasWall, boolean bottomHasWall, boolean topRightHasWall, boolean bottomRightHasWall, boolean topLeftHasWall, boolean bottomLeftHasWall) {
        List<Sprite> sprites = new ArrayList<>();

        if (door) {
            if (topHasWall || bottomHasWall) {
                sprites.add(SpriteHashtable.get(type == WallType.STONE ? 47 : 45));
            } else {
                sprites.add(SpriteHashtable.get(type == WallType.STONE ? 46 : 44));
            }
        } else {
            sprites.add(SpriteHashtable.get(type == WallType.STONE ? 14 : 186));

            //check if places are empty
            if (!topHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 17 : 189));
            if (!rightHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 16 : 188));
            if (!bottomHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 18 : 190));
            if (!leftHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 15 : 187));

            // top right corner
            if (!topHasWall && !rightHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 20 : 192));
            else if (!rightHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 25 : 197));
            else if (!topHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 27 : 199));
            else if (!topRightHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 36 : 208));

            // bottom right corner

            if (!bottomHasWall && !rightHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 22 : 194));
            else if (!rightHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 26 : 198));
            else if (!bottomHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 34 : 206));
            else if (!bottomRightHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 38 : 210));


            // bottom left corner
            if (!bottomHasWall && !leftHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 21 : 193));
            else if (!leftHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 23 : 195));
            else if (!bottomHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 33 : 205));
            else if (!bottomLeftHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 37 : 209));

            // top left corner
            if (!topHasWall && !leftHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 19 : 191));
            else if (!leftHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 24 : 196));
            else if (!topHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 28 : 200));
            else if (!topLeftHasWall) sprites.add(SpriteHashtable.get(type == WallType.STONE ? 35 : 207));
        }
        if (dynamicSpriteList.containsKey(sprites)) { //if a dynamic sprite exists, use it
            sprite = dynamicSpriteList.get(sprites);
        } else { //otherwise make it
            final int SIZE = Tile.SIZE;
            int[] pixels = new int[SIZE * SIZE];
            for (Sprite sprite : sprites) {
                for (int y = 0; y < SIZE; y++) {
                    for (int x = 0; x < SIZE; x++) {
                        int pixel = sprite.pixels[x + y * SIZE];
                        if (!(pixel == 0x00FFFFFF)) {
                            pixels[x + y * SIZE] = pixel;
                        }
                    }
                }
            }
            sprite = new Sprite(pixels);
            dynamicSpriteList.put(sprites, new Sprite(pixels));
        }
    }

    @Override
    public BuildAbleObject instance() {
        return new Wall(this.type, this.door);
    }


}
