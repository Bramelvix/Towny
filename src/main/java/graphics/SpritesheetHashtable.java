package graphics;

import map.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

public final class SpritesheetHashtable  {
	private SpritesheetHashtable() {}

	private static Spritesheet combined;
	private static final Hashtable<Integer, BufferedImage> table = new Hashtable<>();

	public static void registerSpritesheet(int key, BufferedImage sheet) throws Exception {
		if (table.put(key, sheet) != null) {
			throw new Exception("Duplicate key while registering spritesheet: " + key);
		}
	}

	public static void registerSpritesheet(int key, String path) throws Exception {
		registerSpritesheet(key, ImageIO.read(Spritesheet.class.getResource(path)));
	}

	private static BufferedImage get(int key) {
		return table.get(key);
	}

	public static Spritesheet getCombined() {
		if (combined == null) {
			System.err.println("Combined spritesheet has not yet been loaded!");
		}
		return combined;
	}

	public static void registerSpritesheets() throws Exception { //registers all spritesheets by id
		registerSpritesheet(1, "/tiles.png");
		registerSpritesheet(2, "/characters.png");
		registerSpritesheet(3, "/indoor.png");
		combineSpriteSheets();
	}

	private static void combineSpriteSheets() {
		int heightTotal = 0;
		int biggestWidth = 0;
		for (int i = 1; i < table.size()+1; i++) {
			heightTotal+=get(i).getHeight();
			if (get(i).getWidth() > biggestWidth) {
				biggestWidth = get(i).getWidth();
			}
		}
		BufferedImage image = new BufferedImage(biggestWidth, heightTotal, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		int height = 0;
		for (int i = 1; i< table.size()+1; i++) {
			g2d.drawImage(get(i), 0, height, null);
			height += get(i).getHeight();
		}
		g2d.dispose();
		combined = new Spritesheet(image);
	}

	public static int getBaselineY (int originalY, int spritesheetIndex) {
		int y = originalY;
		for (int i = 1; i < spritesheetIndex; i++) {
			y+= get(i).getHeight()/ Tile.SIZE;
		}
		return y;
	}

}
