package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public final int WIDTH;
	public final int HEIGHT;
	public int[] pixels;
	public int margin;
	
	public static SpriteSheet tiles = new SpriteSheet("/res/tiles.png",968,526,1);
	public static SpriteSheet entities = new SpriteSheet("/res/characters.png",918,203,1);
	
	public SpriteSheet(String path, int width, int height, int margin) {
		WIDTH = width;
		HEIGHT = height;
		this.margin = margin;
		pixels = new int[WIDTH*HEIGHT];
		load(path);
	}
	private void load(String path) {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w= image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0,w,h,pixels,0,w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
