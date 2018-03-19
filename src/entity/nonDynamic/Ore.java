package entity.nonDynamic;

import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.dynamic.mob.Villager;
import entity.dynamic.mob.work.Job;
import entity.dynamic.mob.work.MoveItemJob;
import graphics.Sprite;
import graphics.SpriteHashtable;
import map.Level;
import map.Tile;
import sound.Sound;

public class Ore extends Resource {
    private byte mined = 100; // mined percentage (100 = unfinished / 0 =
    // finished)
    private Item minedItem; // Item dropped when the ore is mined


    // basic constructor
    public Ore(int x, int y, OreType type) {
        super(x, y);
        decideSprite(type);
        setVisible(true);
    }

    // decide the sprite for the ore
    private void decideSprite(OreType type) {
        switch (type) {
            case IRON:
                setOre(type.toString().toLowerCase() + " ore", SpriteHashtable.get(54), ItemHashtable.get(5, this.x, this.y));
                break;
            case GOLD:
                setOre(type.toString().toLowerCase() + " ore", SpriteHashtable.get(56), ItemHashtable.get(7, this.x, this.y));
                break;
            case COAL:
                setOre(type.toString().toLowerCase() + " ore", SpriteHashtable.get(53), ItemHashtable.get(8, this.x, this.y));
                break;
            case CRYSTAL:
                setOre(type.toString().toLowerCase() + " ore", SpriteHashtable.get(57), ItemHashtable.get(9, this.x, this.y));
                break;
            case COPPER:
                setOre(type.toString().toLowerCase() + " ore", SpriteHashtable.get(55), ItemHashtable.get(6, this.x, this.y));
                break;
            case STONE:
                setOre(type.toString().toLowerCase(), SpriteHashtable.get(161), ItemHashtable.get(10, this.x, this.y));
                break;
        }
    }

    private void setOre(String name, Sprite oreSprite, Item item) {
        setName(name);
        this.sprite = oreSprite;
        this.minedItem = item;
    }


    // work method executed by the villager when mining
    public boolean work(Villager worker) {
        if (!worker.level.isClearTile(this.x / 16, this.y / 16) && worker.level.getItemOn(this.x, this.y) != null
                && worker.level.getNearestEmptySpot(this.x, this.y) != null) {
            worker.addJob(new MoveItemJob(worker.level.getItemOn(this.x, this.y), worker));
            Tile tile = worker.level.getNearestEmptySpot(this.x, this.y);
            worker.addJob(new MoveItemJob(tile.x * 16, tile.y * 16, worker));
            worker.addJob(new Job(this, worker));
            return true;
        }
        if (mined > 0) {
            if (mined % 20 == 0)
                Sound.speelGeluid(Sound.stoneMining);
            mined--;
            return false;
        } else {
            worker.level.removeEntity(this);
            worker.level.addItem(minedItem.copy());
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
        //initial sprite
        spriteList.add(sprite);

        //check if places are empty
        if (!topHasWall) spriteList.add(SpriteHashtable.get(164));
        if (!rightHasWall) spriteList.add(SpriteHashtable.get(163));
        if (!bottomHasWall) spriteList.add(SpriteHashtable.get(165));
        if (!leftHasWall) spriteList.add(SpriteHashtable.get(162));

        // top right corner
        if (!topHasWall && !rightHasWall) spriteList.add(SpriteHashtable.get(167));
        else if (!rightHasWall) spriteList.add(SpriteHashtable.get(172));
        else if (!topHasWall) spriteList.add(SpriteHashtable.get(174));
        else if (!topRightHasWall) spriteList.add(SpriteHashtable.get(183));

        // bottom right corner

        if (!bottomHasWall && !rightHasWall) spriteList.add(SpriteHashtable.get(169));
        else if (!rightHasWall) spriteList.add(SpriteHashtable.get(173));
        else if (!bottomHasWall) spriteList.add(SpriteHashtable.get(181));
        else if (!bottomRightHasWall) spriteList.add(SpriteHashtable.get(185));


        // bottom left corner
        if (!bottomHasWall && !leftHasWall) spriteList.add(SpriteHashtable.get(168));
        else if (!leftHasWall) spriteList.add(SpriteHashtable.get(170));
        else if (!bottomHasWall) spriteList.add(SpriteHashtable.get(180));
        else if (!bottomLeftHasWall) spriteList.add(SpriteHashtable.get(184));

        // top left corner
        if (!topHasWall && !leftHasWall) spriteList.add(SpriteHashtable.get(166));
        else if (!leftHasWall) spriteList.add(SpriteHashtable.get(171));
        else if (!topHasWall) spriteList.add(SpriteHashtable.get(175));
        else if (!topLeftHasWall) spriteList.add(SpriteHashtable.get(182));
    }
}
