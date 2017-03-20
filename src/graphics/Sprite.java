package graphics;

import java.util.Random;

import entity.item.weapon.WeaponMaterial;
import entity.item.weapon.WeaponType;
import map.Tile;

//sprites in the game
public class Sprite {
	private int x, y; // x and y on the spritesheet
	public static final int SIZE = Tile.SIZE; // 16
	public int[] pixels; // pixels array
	private SpriteSheet sheet; // spritesheet
	private static final Random rand = new Random(); // random object used for
														// random distribution
														// of sprites

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

	// TREES
	public static Sprite treebottom = new Sprite(13, 11, SpriteSheet.tiles);
	public static Sprite treetop = new Sprite(13, 10, SpriteSheet.tiles);

	// WALLS
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
	public static Sprite villager2 = new Sprite(1, 1, SpriteSheet.entities);
	public static Sprite villager3 = new Sprite(1, 2, SpriteSheet.entities);

	// ORE
	public static Sprite coalOre = new Sprite(0, 23, SpriteSheet.tiles);
	public static Sprite ironOre = new Sprite(0, 24, SpriteSheet.tiles);
	public static Sprite copperOre = new Sprite(0, 25, SpriteSheet.tiles);
	public static Sprite goldOre = new Sprite(1, 25, SpriteSheet.tiles);
	public static Sprite copperBar = new Sprite(41, 10, SpriteSheet.tiles);

	// ITEMS
	public static Sprite logs = new Sprite(53, 22, SpriteSheet.tiles);
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

	// WEAPONS
	public static Sprite woodspear = new Sprite(44, 4, SpriteSheet.entities);
	public static Sprite bronzeaxe = new Sprite(42, 0, SpriteSheet.entities);
	public static Sprite bronzewarhammer = new Sprite(43, 0, SpriteSheet.entities);
	public static Sprite bronzepick = new Sprite(45, 0, SpriteSheet.entities);
	public static Sprite bronzehalbert = new Sprite(46, 0, SpriteSheet.entities);
	public static Sprite woodaxe = new Sprite(42, 5, SpriteSheet.entities);
	public static Sprite woodwarhammer = new Sprite(43, 5, SpriteSheet.entities);
	public static Sprite woodpick = new Sprite(45, 5, SpriteSheet.entities);
	public static Sprite woodhalbert = new Sprite(46, 5, SpriteSheet.entities);
	public static Sprite woodbow = new Sprite(52, 0, SpriteSheet.entities);
	public static Sprite ironspear = new Sprite(49, 4, SpriteSheet.entities);
	public static Sprite ironaxe = new Sprite(47, 5, SpriteSheet.entities);
	public static Sprite ironbow = new Sprite(53, 1, SpriteSheet.entities);
	public static Sprite goldbow = new Sprite(52, 2, SpriteSheet.entities);
	public static Sprite crystalbow = new Sprite(52, 4, SpriteSheet.entities);
	public static Sprite ironwarhammer = new Sprite(48, 5, SpriteSheet.entities);
	public static Sprite ironpick = new Sprite(50, 5, SpriteSheet.entities);
	public static Sprite ironhalbert = new Sprite(51, 5, SpriteSheet.entities);
	public static Sprite goldspear = new Sprite(49, 6, SpriteSheet.entities);
	public static Sprite goldaxe = new Sprite(47, 7, SpriteSheet.entities);
	public static Sprite goldwarhammer = new Sprite(48, 7, SpriteSheet.entities);
	public static Sprite goldpick = new Sprite(50, 7, SpriteSheet.entities);
	public static Sprite goldhalbert = new Sprite(51, 7, SpriteSheet.entities);
	public static Sprite crystalspear = new Sprite(49, 8, SpriteSheet.entities);
	public static Sprite crystalaxe = new Sprite(47, 9, SpriteSheet.entities);
	public static Sprite crystalwarhammer = new Sprite(48, 9, SpriteSheet.entities);
	public static Sprite crystalpick = new Sprite(50, 9, SpriteSheet.entities);
	public static Sprite crystalhalbert = new Sprite(51, 9, SpriteSheet.entities);
	public static Sprite woodsword = new Sprite(42, 6, SpriteSheet.entities);
	public static Sprite bronzesword = new Sprite(43, 6, SpriteSheet.entities);
	public static Sprite ironsword = new Sprite(44, 6, SpriteSheet.entities);
	public static Sprite goldsword = new Sprite(45, 6, SpriteSheet.entities);
	public static Sprite crystalsword = new Sprite(46, 6, SpriteSheet.entities);
	public static Sprite wooddagger = new Sprite(42, 7, SpriteSheet.entities);
	public static Sprite bronzedagger = new Sprite(43, 7, SpriteSheet.entities);
	public static Sprite irondagger = new Sprite(44, 7, SpriteSheet.entities);
	public static Sprite golddagger = new Sprite(45, 7, SpriteSheet.entities);
	public static Sprite crystaldagger = new Sprite(46, 7, SpriteSheet.entities);
	public static Sprite woodscimitar = new Sprite(42, 9, SpriteSheet.entities);
	public static Sprite bronzescimitar = new Sprite(43, 9, SpriteSheet.entities);
	public static Sprite ironscimitar = new Sprite(44, 9, SpriteSheet.entities);
	public static Sprite goldscimitar = new Sprite(45, 9, SpriteSheet.entities);
	public static Sprite crystalscimitar = new Sprite(46, 9, SpriteSheet.entities);
	public static Sprite woodbuckler = new Sprite(33, 1, SpriteSheet.entities);
	public static Sprite ironbuckler = new Sprite(37, 1, SpriteSheet.entities);
	public static Sprite ironheater = new Sprite(39, 0, SpriteSheet.entities);
	public static Sprite goldbuckler = new Sprite(33, 4, SpriteSheet.entities);
	public static Sprite goldheater = new Sprite(35, 3, SpriteSheet.entities);
	public static Sprite bronzebuckler = new Sprite(37, 4, SpriteSheet.entities);
	public static Sprite bronzeheater = new Sprite(39, 3, SpriteSheet.entities);
	public static Sprite crystalbuckler = new Sprite(37, 7, SpriteSheet.entities);
	public static Sprite crystalheater = new Sprite(39, 6, SpriteSheet.entities);
	public static Sprite woodheater = new Sprite(35, 0, SpriteSheet.entities);
	public static Sprite bronzespear = new Sprite(44, 1, SpriteSheet.entities);

	protected Sprite(int x, int y, SpriteSheet sheet) {
		this.x = x * SIZE + (x * sheet.margin);
		this.y = y * SIZE + (y * sheet.margin);
		this.sheet = sheet;
		pixels = new int[SIZE * SIZE];
		load();
	}

	private Sprite() {
		pixels = new int[SIZE * SIZE];
	}

	// return random grass sprite
	public static Sprite getGrass() {
		return rand.nextBoolean() ? grass : grass2;
	}

	// return random stone sprite
	public static Sprite getStone() {
		return rand.nextBoolean() ? stone : stone2;
	}

	// return random dirt sprite
	public static Sprite getDirt() {
		return rand.nextBoolean() ? dirt : dirt2;
	}

	// returns random skin color sprite
	public static Sprite getPerson() {
		byte n = (byte) rand.nextInt(3);
		return n == 0 ? Sprite.villager1 : n == 1 ? Sprite.villager2 : Sprite.villager3;
	}

	// return random sand sprite
	public static Sprite getSand() {
		return rand.nextBoolean() ? sand : sand2;
	}

	// load a sprites pixels into the pixel array
	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];
			}
		}
	}

	public static Sprite getWeaponSprite(WeaponType type, WeaponMaterial mat) {
		switch (mat) {
		case BRONZE: {
			switch (type) {
			case AXE:
				return bronzeaxe;
			case BUCKLER:
				return bronzebuckler;
			case DAGGER:
				return bronzedagger;
			case HALBERT:
				return bronzehalbert;
			case HEATER:
				return bronzeheater;
			case PICK:
				return bronzepick;
			case SCIMITAR:
				return bronzescimitar;
			case SPEAR:
				return bronzespear;
			case SWORD:
				return bronzesword;
			case WARHAMMER:
				return bronzewarhammer;
			default:
				return null;
			}
		}
		case CRYSTAL: {
			switch (type) {
			case AXE:
				return crystalaxe;
			case BUCKLER:
				return crystalbuckler;
			case DAGGER:
				return crystaldagger;
			case HALBERT:
				return crystalhalbert;
			case HEATER:
				return crystalheater;
			case PICK:
				return crystalpick;
			case SCIMITAR:
				return crystalscimitar;
			case SPEAR:
				return crystalspear;
			case SWORD:
				return crystalsword;
			case WARHAMMER:
				return crystalwarhammer;
			case BOW:
				return crystalbow;
			}
		}
		case GOLD: {
			switch (type) {
			case AXE:
				return goldaxe;
			case BUCKLER:
				return goldbuckler;
			case DAGGER:
				return golddagger;
			case HALBERT:
				return goldhalbert;
			case HEATER:
				return goldheater;
			case PICK:
				return goldpick;
			case SCIMITAR:
				return goldscimitar;
			case SPEAR:
				return goldspear;
			case SWORD:
				return goldsword;
			case WARHAMMER:
				return goldwarhammer;
			case BOW:
				return goldbow;
			}
		}
		case IRON: {
			switch (type) {
			case AXE:
				return ironaxe;
			case BUCKLER:
				return ironbuckler;
			case DAGGER:
				return irondagger;
			case HALBERT:
				return ironhalbert;
			case HEATER:
				return ironheater;
			case PICK:
				return ironpick;
			case SCIMITAR:
				return ironscimitar;
			case SPEAR:
				return ironspear;
			case SWORD:
				return ironsword;
			case WARHAMMER:
				return ironwarhammer;
			case BOW:
				return ironbow;
			}
		}
		case WOOD: {
			switch (type) {
			case AXE:
				return woodaxe;
			case BOW:
				return woodbow;
			case BUCKLER:
				return woodbuckler;
			case DAGGER:
				return wooddagger;
			case HALBERT:
				return woodhalbert;
			case HEATER:
				return woodheater;
			case PICK:
				return woodpick;
			case SCIMITAR:
				return woodscimitar;
			case SPEAR:
				return woodspear;
			case SWORD:
				return woodsword;
			case WARHAMMER:
				return woodwarhammer;
			}
		}
		}
		return null;
	}
}
