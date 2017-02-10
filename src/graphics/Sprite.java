package graphics;

import java.util.Random;

import map.Tile;

//sprites in the game
public class Sprite {
	private int x, y; // x and y on the spritesheet
	public static final int SIZE = Tile.SIZE; // 16
	public int[] pixels; // pixels array
	private SpriteSheet sheet; // spritesheet
	private static final Random rand = new Random(); //random object used for random distribution of sprites

	// TERRAIN
	public static Sprite water = new Sprite(0, 0, SpriteSheet.tiles);
	public static Sprite grass = new Sprite(5, 0, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite();
	public static Sprite darkGrass = new Sprite(0, 5, SpriteSheet.tiles);
	public static Sprite grass2 = new Sprite(5, 1, SpriteSheet.tiles);
	public static Sprite dirt = new Sprite(6, 0, SpriteSheet.tiles);
	public static Sprite dirt2 = new Sprite(6, 1, SpriteSheet.tiles);
	public static Sprite stone = new Sprite(7, 0, SpriteSheet.tiles);
	public static Sprite stone2 = new Sprite(7, 1, SpriteSheet.tiles);
	public static Sprite sand = new Sprite(8, 0, SpriteSheet.tiles);
	public static Sprite sand2 = new Sprite(8, 1, SpriteSheet.tiles);
	
	//TREES
	public static Sprite treebottom = new Sprite(13, 11, SpriteSheet.tiles);
	public static Sprite treetop = new Sprite(13, 10, SpriteSheet.tiles);

	
	//WALLS
	public static Sprite beigeWallVertical = new Sprite(15, 13, SpriteSheet.tiles);
	public static Sprite woodenWallHorizontalRight = new Sprite(34, 12, SpriteSheet.tiles);
	public static Sprite woodenWallHorizontalBoth = new Sprite(35, 12, SpriteSheet.tiles);
	public static Sprite woodenWallHorizontalLeft = new Sprite(36, 12, SpriteSheet.tiles);
	public static Sprite woodenWallVerticalBottom = new Sprite(34, 13, SpriteSheet.tiles);
	public static Sprite woodenWall4sides = new Sprite(35, 13, SpriteSheet.tiles);
	public static Sprite woodenWallVerticalBoth = new Sprite(36, 13, SpriteSheet.tiles);
	public static Sprite woodenWallCornerTopRight = new Sprite(37, 13, SpriteSheet.tiles);
	public static Sprite woodenWallCornerTopLeft = new Sprite(38, 13, SpriteSheet.tiles);
	public static Sprite woodenWallVerticalTop = new Sprite(34, 14, SpriteSheet.tiles);
	public static Sprite woodenWallCornerBottomRight = new Sprite(37, 12, SpriteSheet.tiles);
	public static Sprite woodenWallCornerBottomLeft = new Sprite(38, 12, SpriteSheet.tiles);
	public static Sprite woodenWallTRight = new Sprite(39, 12, SpriteSheet.tiles);
	public static Sprite woodenWallTTop = new Sprite(40, 12, SpriteSheet.tiles);
	public static Sprite woodenWallTLeft = new Sprite(39, 13, SpriteSheet.tiles);
	public static Sprite woodenWallTBottom = new Sprite(40, 13, SpriteSheet.tiles);
	
	
	// MOBS
	public static Sprite villager1 = new Sprite(1, 0, SpriteSheet.entities);
	public static Sprite villager2 = new Sprite( 1, 1, SpriteSheet.entities);
	public static Sprite villager3 = new Sprite(1, 2, SpriteSheet.entities);

	// ORE
	public static Sprite coalOre = new Sprite(0, 23, SpriteSheet.tiles);
	public static Sprite ironOre = new Sprite(0, 24, SpriteSheet.tiles);
	public static Sprite copperOre = new Sprite(0, 25, SpriteSheet.tiles);
	public static Sprite goldOre = new Sprite(1, 25, SpriteSheet.tiles);
	public static Sprite copperBar = new Sprite(41, 10, SpriteSheet.tiles);

	// ITEMS
	public static Sprite logs  = new Sprite(53, 22, SpriteSheet.tiles);
	public static Sprite ironBar = new Sprite(42, 10, SpriteSheet.tiles);
	public static Sprite goldBar = new Sprite(43, 10, SpriteSheet.tiles);
	public static Sprite ironChunk = new Sprite(44, 10, SpriteSheet.tiles);
	public static Sprite goldChunk = new Sprite(45, 10, SpriteSheet.tiles);

	// CLOTHES
	public static Sprite blacktrousers = new Sprite(4, 0, SpriteSheet.entities);
	public static Sprite brownShirt1 = new Sprite(6, 0, SpriteSheet.entities);
	public static Sprite brownShirt2 = new Sprite(7, 0, SpriteSheet.entities);
	public static Sprite brownShirt3 = new Sprite(8, 0, SpriteSheet.entities);
	public static Sprite brownShirt4 = new Sprite(9, 0, SpriteSheet.entities);
	public static Sprite greenShirt1 = new Sprite(10, 0, SpriteSheet.entities);
	public static Sprite greenShirt2 = new Sprite(11, 0, SpriteSheet.entities);
	public static Sprite greenShirt3 = new Sprite(12, 0, SpriteSheet.entities);
	public static Sprite greenShirt4 = new Sprite(13, 0, SpriteSheet.entities);
	public static Sprite greyShirt1 = new Sprite(14, 0, SpriteSheet.entities);
	public static Sprite greyShirt2 = new Sprite(15, 0, SpriteSheet.entities);
	public static Sprite greyShirt3 = new Sprite(16, 0, SpriteSheet.entities);
	public static Sprite greyShirt4 = new Sprite(17, 0, SpriteSheet.entities);

	protected Sprite(int x, int y, SpriteSheet sheet)  {
		this.x = x * SIZE + (x * sheet.margin);
		this.y = y * SIZE + (y * sheet.margin);
		this.sheet = sheet;
		pixels = new int[SIZE * SIZE];
		load();
	}

	private Sprite() {
		pixels = new int[SIZE * SIZE];
	}

	//return random grass sprite
	public static Sprite getGrass() {
		return rand.nextBoolean() ? grass : grass2;
	}

	//return random stone sprite
	public static Sprite getStone() {
		return rand.nextBoolean() ? stone : stone2;
	}

	//return random dirt sprite
	public static Sprite getDirt() {
		return rand.nextBoolean() ? dirt : dirt2;
	}

	//returns random skin color sprite
	public static Sprite getPerson() {
		byte n = (byte) rand.nextInt(3);
		if (n == 0)
			return Sprite.villager1;
		if (n == 1)
			return Sprite.villager2;
		if (n == 2)
			return Sprite.villager3;
		return Sprite.voidSprite;
	}

	//return random sand sprite
	public static Sprite getSand() {
		return rand.nextBoolean() ? sand : sand2;
	}

	//load a sprites pixels into the pixel array
	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];
			}
		}
	}
}
