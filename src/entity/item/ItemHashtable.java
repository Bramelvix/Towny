package entity.item;


import graphics.Sprite;
import graphics.SpriteHashtable;

import java.util.Hashtable;

public abstract class ItemHashtable {
    private static final Hashtable<Integer, Item> table = new Hashtable<>();


    public static void registerItem(int key, Item item) throws Exception {
        if (table.put(key, item) != null) {
            throw new Exception("Duplicate key while registering items!");
        }
    }

    public static Item get(int key) {
        return table.get(key).copy();
    }

    public static Item get(int key, int x, int y) {
        return table.get(key).copy(x, y);
    }

    public static void registerItems() throws Exception {
        registerItem(1, new Item("logs", SpriteHashtable.get(58), "Wooden logs")); //logs
        registerItem(2, new Item("iron bar", SpriteHashtable.get(59), "a bar of solid iron"));//iron bar
        registerItem(3, new Item("gold bar", SpriteHashtable.get(60), "a bar of solid gold"));//gold bar
        registerItem(4, new Item("copper bar", SpriteHashtable.get(61), "a bar of solid copper"));//copper bar
        registerItem(5, new Item("iron ore", SpriteHashtable.get(62), "iron ore"));// iron ore
        registerItem(6, new Item("copper ore", SpriteHashtable.get(66), "copper ore")); // copper ore
        registerItem(7, new Item("gold ore", SpriteHashtable.get(63), "gold ore")); // gold ore
        registerItem(8, new Item("coal ore", SpriteHashtable.get(65), "coal ore")); // coal ore
        registerItem(9, new Item("uncut crystal", SpriteHashtable.get(67), "an uncut crystal")); // uncut crystal
        registerItem(10, new Item("stone", SpriteHashtable.get(64), "some stones")); // stone
        registerItem(11, new Item("cut crystal", SpriteHashtable.get(68), "a cut crystal")); // cut crystal

    }
}


