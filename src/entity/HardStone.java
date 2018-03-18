package entity;

import entity.item.ItemHashtable;
import graphics.Sprite;
import graphics.SpriteHashtable;
import map.Level;
import map.Tile;

public class HardStone extends Ore {

    public HardStone(int x, int y) {
        super(x, y, OreType.STONE);
        this.minedItem = ItemHashtable.get(10, this.x, this.y);
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
        //initial sprite
        spriteList.add(SpriteHashtable.get(161));
        sprite = SpriteHashtable.get(161); //sprite needs to be set, idk

        //check if places are empty
        if (!topHasWall) spriteList.add(SpriteHashtable.get(164));
        if (!rightHasWall) spriteList.add(SpriteHashtable.get(163));
        if (!bottomHasWall) spriteList.add(SpriteHashtable.get(165));
        if (!leftHasWall) spriteList.add(SpriteHashtable.get(162));

        // top right corner
        if(!topHasWall && !rightHasWall) spriteList.add(SpriteHashtable.get(167));
        else if(!rightHasWall) spriteList.add(SpriteHashtable.get(172));
        else if(!topHasWall) spriteList.add(SpriteHashtable.get(174));
        else if(!topRightHasWall) spriteList.add(SpriteHashtable.get(183));

        // bottom right corner

        if(!bottomHasWall && !rightHasWall) spriteList.add(SpriteHashtable.get(169));
        else if (!rightHasWall) spriteList.add(SpriteHashtable.get(173));
        else if (!bottomHasWall) spriteList.add(SpriteHashtable.get(181));
        else if(!bottomRightHasWall) spriteList.add(SpriteHashtable.get(185));


        // bottom left corner
        if(!bottomHasWall && !leftHasWall) spriteList.add(SpriteHashtable.get(168));
        else if(!leftHasWall) spriteList.add(SpriteHashtable.get(170));
        else if(!bottomHasWall) spriteList.add(SpriteHashtable.get(180));
        else if(!bottomLeftHasWall) spriteList.add(SpriteHashtable.get(184));

        // top left corner
        if(!topHasWall && !leftHasWall) spriteList.add(SpriteHashtable.get(166));
        else if(!leftHasWall) spriteList.add(SpriteHashtable.get(171));
        else if(!topHasWall) spriteList.add(SpriteHashtable.get(175));
        else if(!topLeftHasWall) spriteList.add(SpriteHashtable.get(182));
    }
}
