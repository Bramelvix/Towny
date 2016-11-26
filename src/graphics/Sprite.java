package graphics;

import java.util.Random;

public class Sprite {
	public final int SIZE;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	private static final Random rand = new Random();

	
	
	//TERRAIN
	public static Sprite water = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite grass = new Sprite(16, 5, 0, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16);
	public static Sprite darkGrass = new Sprite(16, 0, 5, SpriteSheet.tiles);
	public static Sprite villager1 = new Sprite(16, 1, 0, SpriteSheet.entities);
	public static Sprite grass2 = new Sprite(16, 5, 1, SpriteSheet.tiles);
	public static Sprite dirt = new Sprite(16, 6, 0, SpriteSheet.tiles);
	public static Sprite dirt2 = new Sprite(16, 6, 1, SpriteSheet.tiles);
	public static Sprite stone = new Sprite(16, 7, 0, SpriteSheet.tiles);
	public static Sprite stone2 = new Sprite(16, 7, 1, SpriteSheet.tiles);
	public static Sprite sand = new Sprite(16, 8, 0, SpriteSheet.tiles);
	public static Sprite sand2 = new Sprite(16, 8, 1, SpriteSheet.tiles);
	public static Sprite beigeWallVertical = new Sprite(16, 15, 13, SpriteSheet.tiles);
	public static Sprite treebottom = new Sprite(16, 13, 11, SpriteSheet.tiles);
	public static Sprite treetop = new Sprite(16, 13, 10, SpriteSheet.tiles);
	
	
	//ORE
	public static Sprite coalOre = new Sprite(16, 0, 23, SpriteSheet.tiles);
	public static Sprite ironOre = new Sprite(16, 0, 24, SpriteSheet.tiles);
	public static Sprite copperOre = new Sprite(16, 0, 25, SpriteSheet.tiles);
	public static Sprite goldOre = new Sprite(16, 1, 25, SpriteSheet.tiles);
	
	//ITEMS
	public static Sprite logs = new Sprite(16, 41, 10, SpriteSheet.tiles);
	public static Sprite ironBar = new Sprite(16, 42, 10, SpriteSheet.tiles);
	public static Sprite goldBar = new Sprite(16, 43, 10, SpriteSheet.tiles);
	public static Sprite ironChunk = new Sprite(16, 44, 10, SpriteSheet.tiles);
	public static Sprite goldChunk = new Sprite(16, 45, 10, SpriteSheet.tiles);
	
	
	
	
	//CLOTHES
	public static Sprite blacktrousers = new Sprite(16, 4, 0, SpriteSheet.entities);
	public static Sprite brownShirt1 = new Sprite(16, 6, 0, SpriteSheet.entities);
	public static Sprite brownShirt2 = new Sprite(16, 7, 0, SpriteSheet.entities);
	public static Sprite brownShirt3 = new Sprite(16, 8, 0, SpriteSheet.entities);
	public static Sprite brownShirt4 = new Sprite(16, 9, 0, SpriteSheet.entities);
	public static Sprite greenShirt1 = new Sprite(16, 10, 0, SpriteSheet.entities);
	public static Sprite greenShirt2 = new Sprite(16, 11, 0, SpriteSheet.entities);
	public static Sprite greenShirt3 = new Sprite(16, 12, 0, SpriteSheet.entities);
	public static Sprite greenShirt4 = new Sprite(16, 13, 0, SpriteSheet.entities);
	public static Sprite greyShirt1 = new Sprite(16, 14, 0, SpriteSheet.entities);
	public static Sprite greyShirt2 = new Sprite(16, 15, 0, SpriteSheet.entities);
	public static Sprite greyShirt3 = new Sprite(16, 16, 0, SpriteSheet.entities);
	public static Sprite greyShirt4 = new Sprite(16, 17, 0, SpriteSheet.entities);

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.x = x * size + (x * sheet.margin);
		this.y = y * size + (y * sheet.margin);
		this.sheet = sheet;
		pixels = new int[SIZE * SIZE];
		load();
	}

	public Sprite(int size) {
		SIZE = size;
		pixels = new int[size * size];
	}

	public static Sprite getGrass() {
		return rand.nextBoolean() ? grass : grass2;
	}

	public static Sprite getStone() {
		return rand.nextBoolean() ? stone : stone2;
	}

	public static Sprite getDirt() {
		return rand.nextBoolean() ? dirt : dirt2;
	}

	public static Sprite getSand() {
		return rand.nextBoolean() ? sand : sand2;
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];
			}
		}
	}
}
