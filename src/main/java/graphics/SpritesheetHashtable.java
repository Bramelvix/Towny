package graphics;

import java.util.Hashtable;

public class SpritesheetHashtable {
    private static final Hashtable<Integer, Spritesheet> table = new Hashtable<>();

    public static void registerSpritesheet(int key, Spritesheet sheet) throws Exception {
        if (table.put(key, sheet) != null) {
            throw new Exception("Duplicate key while registering spritesheet: " + key);
        }
    }

    public static Spritesheet get(int key) {
        return table.get(key);
    }

    public static void registerSpritesheets() throws Exception { //registers all spritesheets by id
        registerSpritesheet(1, new Spritesheet("/tiles.png", 3));
        registerSpritesheet(2, new Spritesheet("/characters.png", 3));
        registerSpritesheet(3, new Spritesheet("/indoor.png", 3));
    }

}
