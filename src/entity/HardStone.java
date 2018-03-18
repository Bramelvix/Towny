package entity;

import entity.item.ItemHashtable;
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
        decideSprite(leftHasWall, rightHasWall, topHasWall, bottomHasWall);

    }

    private void decideSprite(boolean leftHasWall, boolean rightHasWall, boolean topHasWall, boolean bottomHasWall) {
        sprite = SpriteHashtable.get(161);
        if (topHasWall) {
            sprite = SpriteHashtable.get(176);
            if (bottomHasWall) {
                sprite = SpriteHashtable.get(170);
                if (leftHasWall) {
                    sprite = SpriteHashtable.get(163);
                    if (rightHasWall) {
                        sprite = SpriteHashtable.get(171);
                    }
                } else {
                    if (rightHasWall) {
                        sprite = SpriteHashtable.get(162);

                    }
                }
            } else {
                if (leftHasWall) {
                    sprite = SpriteHashtable.get(167);

                    if (rightHasWall) {
                        sprite = SpriteHashtable.get(169);
                    }
                } else {
                    if (rightHasWall) {
                        sprite = SpriteHashtable.get(166);
                    }
                }
            }
        } else {
            if (bottomHasWall) {
                sprite = SpriteHashtable.get(175);
                if (leftHasWall) {
                    SpriteHashtable.get(165);
                    if (rightHasWall) {
                        SpriteHashtable.get(168);
                    }

                } else {
                    if (rightHasWall) {
                        sprite = SpriteHashtable.get(164);
                    }
                }
            } else {
                if (leftHasWall) {
                    sprite = SpriteHashtable.get(173);
                    if (rightHasWall) {
                        sprite = SpriteHashtable.get(174);
                    }
                } else {
                    if (rightHasWall) {
                        sprite = SpriteHashtable.get(172);
                    }
                }
            }
        }
    }
}
