package entity.nonDynamic.resources;

import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.dynamic.mob.Villager;
import graphics.Sprite;
import graphics.SpriteHashtable;
import map.Level;
import map.Tile;
import sound.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ore extends Resource {
    private static HashMap<List<Sprite>, Sprite> dynamicSpriteList = new HashMap<>();
    private byte mined = 100; // mined percentage (100 = unfinished / 0 = finished)
    private Item minedItem; // Item dropped when the ore is mined


    // basic constructor
    public Ore(int x, int y, int z, Level level, OreType type) {
        super(x, y, z, level);
        decideSprite(type);
        setVisible(true);
    }

    // decide the sprite for the ore
    private void decideSprite(OreType type) {
        String name = type == OreType.STONE ? type.toString().toLowerCase() : type.toString().toLowerCase() + " ore";
        setOre(name, SpriteHashtable.get(type.getSpriteId()), ItemHashtable.get(type.getItemSpriteId(), this.x, this.y, this.z));


    }

    private void setOre(String name, Sprite oreSprite, Item item) {
        setName(name);
        this.sprite = oreSprite;
        this.minedItem = item;
    }


    // work method executed by the villager when mining
    public boolean work(Villager worker) {
        if (mined > 0) {
            if (mined % 20 == 0)
                Sound.playSound(Sound.stoneMining);
            mined--;
            return false;
        } else {
            level.removeEntity(this);
            level.addItem(minedItem.copy());
            return true;
        }
    }

    public void checkSides(Level level) {
        boolean leftHasWall = null != level.selectOre(x - Tile.SIZE, y);
        boolean rightHasWall = null != level.selectOre(x + Tile.SIZE, y);
        boolean topHasWall = null != level.selectOre(x, y - Tile.SIZE);
        boolean bottomHasWall = null != level.selectOre(x, y + Tile.SIZE);

        boolean topRightHasWall = null != level.selectOre(x + Tile.SIZE, y - Tile.SIZE);
        boolean bottomRightHasWall = null != level.selectOre(x + Tile.SIZE, y + Tile.SIZE);
        boolean bottomLeftHasWall = null != level.selectOre(x - Tile.SIZE, y + Tile.SIZE);
        boolean topLeftHasWall = null != level.selectOre(x - Tile.SIZE, y - Tile.SIZE);
        decideSprite(leftHasWall, rightHasWall, topHasWall, bottomHasWall, topRightHasWall, bottomRightHasWall, bottomLeftHasWall, topLeftHasWall);

    }

    private void decideSprite(boolean leftHasWall, boolean rightHasWall, boolean topHasWall, boolean bottomHasWall, boolean topRightHasWall, boolean bottomRightHasWall, boolean bottomLeftHasWall, boolean topLeftHasWall) {
        List<Sprite> sprites = new ArrayList<>();
        sprites.add(sprite);
        //check if places are empty
        if (!topHasWall) sprites.add(SpriteHashtable.get(164)); //3
        if (!rightHasWall) sprites.add(SpriteHashtable.get(163)); //2
        if (!bottomHasWall) sprites.add(SpriteHashtable.get(165)); //4
        if (!leftHasWall) sprites.add(SpriteHashtable.get(162)); //1

        // top right corner
        if (!topHasWall && !rightHasWall) sprites.add(SpriteHashtable.get(167)); //6
        else if (!rightHasWall) sprites.add(SpriteHashtable.get(172)); //11
        else if (!topHasWall) sprites.add(SpriteHashtable.get(174)); //13
        else if (!topRightHasWall) sprites.add(SpriteHashtable.get(181)); //20

        // bottom right corner
        if (!bottomHasWall && !rightHasWall) sprites.add(SpriteHashtable.get(169)); //8
        else if (!rightHasWall) sprites.add(SpriteHashtable.get(173)); //12
        else if (!bottomHasWall) sprites.add(SpriteHashtable.get(177)); //16
        else if (!bottomRightHasWall) sprites.add(SpriteHashtable.get(179)); //18

        // bottom left corner
        if (!bottomHasWall && !leftHasWall) sprites.add(SpriteHashtable.get(168)); //7
        else if (!leftHasWall) sprites.add(SpriteHashtable.get(170)); //9
        else if (!bottomHasWall) sprites.add(SpriteHashtable.get(176)); //15
        else if (!bottomLeftHasWall) sprites.add(SpriteHashtable.get(178)); //17

        // top left corner
        if (!topHasWall && !leftHasWall) sprites.add(SpriteHashtable.get(166)); //5
        else if (!leftHasWall) sprites.add(SpriteHashtable.get(171)); //10
        else if (!topHasWall) sprites.add(SpriteHashtable.get(175)); //14
        else if (!topLeftHasWall) sprites.add(SpriteHashtable.get(180)); //19


        if(dynamicSpriteList.containsKey(sprites)) { //if a dynamic sprite exists, use it
            sprite = dynamicSpriteList.get(sprites);
        } else { //otherwise make it
            final int SIZE = Tile.SIZE;
            int[] pixels = new int[SIZE*SIZE];
            for (Sprite sprite : sprites) {
                for (int y = 0; y < SIZE; y++) {
                    for (int x = 0; x < SIZE; x++) {
                        int pixel = sprite.pixels[x+y*SIZE];
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
}
