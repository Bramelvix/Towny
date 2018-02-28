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
	public static final Sprite water = new Sprite(0, 0, SpriteSheet.tiles);
	public static final Sprite grass = new Sprite(5, 0, SpriteSheet.tiles);
	public static final Sprite voidSprite = new Sprite();
	public static final Sprite darkGrass = new Sprite(0, 5, SpriteSheet.tiles);
	public static final Sprite grass2 = new Sprite(5, 1, SpriteSheet.tiles);
	public static final Sprite dirt = new Sprite(6, 0, SpriteSheet.tiles);
	public static final Sprite dirt2 = new Sprite(6, 1, SpriteSheet.tiles);
	public static final Sprite stone = new Sprite(7, 0, SpriteSheet.tiles);
	public static final Sprite stone2 = new Sprite(7, 1, SpriteSheet.tiles);
	public static final Sprite sand = new Sprite(8, 0, SpriteSheet.tiles);
	public static final Sprite sand2 = new Sprite(8, 1, SpriteSheet.tiles);

	// TREES
	public static final Sprite treebottom = new Sprite(13, 11, SpriteSheet.tiles);
	public static final Sprite treetop = new Sprite(13, 10, SpriteSheet.tiles);

	// WALLS
	private static final Sprite stoneWallHorizontalRight = new Sprite(27, 12, SpriteSheet.tiles);
	private static final Sprite stoneWallHorizontalBoth = new Sprite(28, 12, SpriteSheet.tiles);
	private static final Sprite stoneWallHorizontalLeft = new Sprite(29, 12, SpriteSheet.tiles);
	private static final Sprite stoneWallVerticalBottom = new Sprite(27, 13, SpriteSheet.tiles);
	private static final Sprite stoneWall4sides = new Sprite(28, 13, SpriteSheet.tiles);
	private static final Sprite stoneWallVerticalBoth = new Sprite(29, 13, SpriteSheet.tiles);
	private static final Sprite stoneWallCornerTopRight = new Sprite(30, 13, SpriteSheet.tiles);
	private static final Sprite stoneWallCornerTopLeft = new Sprite(31, 13, SpriteSheet.tiles);
	private static final Sprite stoneWallVerticalTop = new Sprite(27, 14, SpriteSheet.tiles);
	private static final Sprite stoneWallCornerBottomRight = new Sprite(30, 12, SpriteSheet.tiles);
	private static final Sprite stoneWallCornerBottomLeft = new Sprite(31, 12, SpriteSheet.tiles);
	private static final Sprite stoneWallTRight = new Sprite(32, 12, SpriteSheet.tiles);
	private static final Sprite stoneWallTTop = new Sprite(32, 12, SpriteSheet.tiles);
	private static final Sprite stoneWallTLeft = new Sprite(31, 13, SpriteSheet.tiles);
	private static final Sprite stoneWallTBottom = new Sprite(32, 13, SpriteSheet.tiles);

	public static final Sprite[] STONEWALLSPRITES = { Sprite.stoneWallVerticalTop, Sprite.stoneWallVerticalBoth,
			Sprite.stoneWallTLeft, Sprite.stoneWall4sides, Sprite.stoneWallTRight, Sprite.stoneWallCornerTopLeft,
			Sprite.stoneWallTTop, Sprite.stoneWallCornerTopRight, Sprite.stoneWallVerticalBottom,
			Sprite.stoneWallCornerBottomLeft, Sprite.stoneWallTBottom, Sprite.stoneWallCornerBottomRight,
			Sprite.stoneWallHorizontalLeft, Sprite.stoneWallHorizontalBoth, Sprite.stoneWallHorizontalRight };

	private static final Sprite woodenWallHorizontalRight = new Sprite(34, 12, SpriteSheet.tiles);
	private static final Sprite woodenWallHorizontalBoth = new Sprite(35, 12, SpriteSheet.tiles);
	private static final Sprite woodenWallHorizontalLeft = new Sprite(36, 12, SpriteSheet.tiles);
	private static final Sprite woodenWallVerticalBottom = new Sprite(34, 13, SpriteSheet.tiles);
	private static final Sprite woodenWall4sides = new Sprite(35, 13, SpriteSheet.tiles);
	private static final Sprite woodenWallVerticalBoth = new Sprite(36, 13, SpriteSheet.tiles);
	private static final Sprite woodenWallCornerTopRight = new Sprite(37, 13, SpriteSheet.tiles);
	private static final Sprite woodenWallCornerTopLeft = new Sprite(38, 13, SpriteSheet.tiles);
	private static final Sprite woodenWallVerticalTop = new Sprite(34, 14, SpriteSheet.tiles);
	private static final Sprite woodenWallCornerBottomRight = new Sprite(37, 12, SpriteSheet.tiles);
	private static final Sprite woodenWallCornerBottomLeft = new Sprite(38, 12, SpriteSheet.tiles);
	private static final Sprite woodenWallTRight = new Sprite(39, 12, SpriteSheet.tiles);
	private static final Sprite woodenWallTTop = new Sprite(40, 12, SpriteSheet.tiles);
	private static final Sprite woodenWallTLeft = new Sprite(39, 13, SpriteSheet.tiles);
	private static final Sprite woodenWallTBottom = new Sprite(40, 13, SpriteSheet.tiles);

	public static final Sprite[] WOODWALLSPRITES = { Sprite.woodenWallVerticalTop, Sprite.woodenWallVerticalBoth,
			Sprite.woodenWallTLeft, Sprite.woodenWall4sides, Sprite.woodenWallTRight, Sprite.woodenWallCornerTopLeft,
			Sprite.woodenWallTTop, Sprite.woodenWallCornerTopRight, Sprite.woodenWallVerticalBottom,
			Sprite.woodenWallCornerBottomLeft, Sprite.woodenWallTBottom, Sprite.woodenWallCornerBottomRight,
			Sprite.woodenWallHorizontalLeft, Sprite.woodenWallHorizontalBoth, Sprite.woodenWallHorizontalRight };

	private static final Sprite woodenDoorHorizontal = new Sprite(32, 1, SpriteSheet.tiles);
	private static final Sprite woodenDoorVertical = new Sprite(37, 1, SpriteSheet.tiles);

	public static final Sprite[] WOODDOORSPRITES = { Sprite.woodenDoorVertical, Sprite.woodenDoorVertical,
			Sprite.woodenDoorVertical, Sprite.woodenDoorHorizontal, Sprite.woodenDoorVertical,
			Sprite.woodenDoorHorizontal, Sprite.woodenDoorHorizontal, Sprite.woodenDoorHorizontal,
			Sprite.woodenDoorVertical, Sprite.woodenDoorHorizontal, Sprite.woodenDoorHorizontal,
			Sprite.woodenDoorHorizontal, Sprite.woodenDoorHorizontal, Sprite.woodenDoorHorizontal,
			Sprite.woodenDoorHorizontal };

	private static final Sprite stoneDoorHorizontal = new Sprite(32, 2, SpriteSheet.tiles);
	private static final Sprite stoneDoorVertical = new Sprite(32, 4, SpriteSheet.tiles);

	public static final Sprite[] STONEDOORSPRITES = { Sprite.stoneDoorVertical, Sprite.stoneDoorVertical,
			Sprite.stoneDoorVertical, Sprite.stoneDoorHorizontal, Sprite.stoneDoorVertical, Sprite.stoneDoorHorizontal,
			Sprite.stoneDoorHorizontal, Sprite.stoneDoorHorizontal, Sprite.stoneDoorVertical,
			Sprite.stoneDoorHorizontal, Sprite.stoneDoorHorizontal, Sprite.stoneDoorHorizontal,
			Sprite.stoneDoorHorizontal, Sprite.stoneDoorHorizontal, Sprite.stoneDoorHorizontal };

	// MOBS
	public static final Sprite villager1 = new Sprite(1, 0, SpriteSheet.entities);
	public static final Sprite villager2 = new Sprite(1, 1, SpriteSheet.entities);
	public static final Sprite villager3 = new Sprite(1, 2, SpriteSheet.entities);
	public static final Sprite zombie = new Sprite(0, 3, SpriteSheet.entities);
	public static final Sprite zombiehit = new Sprite(1, 3, SpriteSheet.entities);

	// ORE
	public static final Sprite coalOre = new Sprite(0, 23, SpriteSheet.tiles);
	public static final Sprite ironOre = new Sprite(0, 24, SpriteSheet.tiles);
	public static final Sprite copperOre = new Sprite(0, 26, SpriteSheet.tiles);
	public static final Sprite goldOre = new Sprite(1, 25, SpriteSheet.tiles);
	public static final Sprite crystalOre = new Sprite(0, 25, SpriteSheet.tiles);

	// ITEMS
	public static final Sprite logs = new Sprite(53, 22, SpriteSheet.tiles);
	public static final Sprite ironBar = new Sprite(42, 10, SpriteSheet.tiles);
	public static final Sprite goldBar = new Sprite(43, 10, SpriteSheet.tiles);
	public static final Sprite copperBar = new Sprite(41, 10, SpriteSheet.tiles);
	public static final Sprite ironChunk = new Sprite(44, 10, SpriteSheet.tiles);
	public static final Sprite goldChunk = new Sprite(45, 10, SpriteSheet.tiles);
	public static final Sprite stoneChunk = new Sprite(46, 10, SpriteSheet.tiles);
	public static final Sprite coalChunk = new Sprite(47, 10, SpriteSheet.tiles);
	public static final Sprite copperChunk = new Sprite(48, 10, SpriteSheet.tiles);
	public static final Sprite crystalChunkUncut = new Sprite(41, 24, SpriteSheet.tiles);
	public static final Sprite crystalChunkCut = new Sprite(41, 23, SpriteSheet.tiles);

	// CLOTHES
	public static final Sprite blacktrousers = new Sprite(4, 0, SpriteSheet.entities);
	public static final Sprite brownShirt1 = new Sprite(6, 0, SpriteSheet.entities);
	public static final Sprite brownShirt2 = new Sprite(7, 0, SpriteSheet.entities);
	public static final Sprite brownShirt3 = new Sprite(8, 0, SpriteSheet.entities);
	public static final Sprite brownShirt4 = new Sprite(9, 0, SpriteSheet.entities);
	public static final Sprite greenShirt1 = new Sprite(10, 0, SpriteSheet.entities);
	public static final Sprite greenShirt2 = new Sprite(11, 0, SpriteSheet.entities);
	public static final Sprite greenShirt3 = new Sprite(12, 0, SpriteSheet.entities);
	public static final Sprite greenShirt4 = new Sprite(13, 0, SpriteSheet.entities);
	public static final Sprite greyShirt1 = new Sprite(14, 0, SpriteSheet.entities);
	public static final Sprite greyShirt2 = new Sprite(15, 0, SpriteSheet.entities);
	public static final Sprite greyShirt3 = new Sprite(16, 0, SpriteSheet.entities);
	public static final Sprite greyShirt4 = new Sprite(17, 0, SpriteSheet.entities);

	// WEAPONS
	public static final Sprite woodspear = new Sprite(44, 4, SpriteSheet.entities);
	public static final Sprite copperaxe = new Sprite(42, 0, SpriteSheet.entities);
	public static final Sprite copperwarhammer = new Sprite(43, 0, SpriteSheet.entities);
	public static final Sprite copperpick = new Sprite(45, 0, SpriteSheet.entities);
	public static final Sprite copperhalbert = new Sprite(46, 0, SpriteSheet.entities);
	public static final Sprite woodaxe = new Sprite(42, 5, SpriteSheet.entities);
	public static final Sprite woodwarhammer = new Sprite(43, 5, SpriteSheet.entities);
	public static final Sprite woodpick = new Sprite(45, 5, SpriteSheet.entities);
	public static final Sprite woodhalbert = new Sprite(46, 5, SpriteSheet.entities);
	public static final Sprite woodbow = new Sprite(52, 0, SpriteSheet.entities);
	public static final Sprite ironspear = new Sprite(49, 4, SpriteSheet.entities);
	public static final Sprite ironaxe = new Sprite(47, 5, SpriteSheet.entities);
	public static final Sprite ironbow = new Sprite(53, 1, SpriteSheet.entities);
	public static final Sprite goldbow = new Sprite(52, 2, SpriteSheet.entities);
	public static final Sprite crystalbow = new Sprite(52, 4, SpriteSheet.entities);
	public static final Sprite ironwarhammer = new Sprite(48, 5, SpriteSheet.entities);
	public static final Sprite ironpick = new Sprite(50, 5, SpriteSheet.entities);
	public static final Sprite ironhalbert = new Sprite(51, 5, SpriteSheet.entities);
	public static final Sprite goldspear = new Sprite(49, 6, SpriteSheet.entities);
	public static final Sprite goldaxe = new Sprite(47, 7, SpriteSheet.entities);
	public static final Sprite goldwarhammer = new Sprite(48, 7, SpriteSheet.entities);
	public static final Sprite goldpick = new Sprite(50, 7, SpriteSheet.entities);
	public static final Sprite goldhalbert = new Sprite(51, 7, SpriteSheet.entities);
	public static final Sprite crystalspear = new Sprite(49, 8, SpriteSheet.entities);
	public static final Sprite crystalaxe = new Sprite(47, 9, SpriteSheet.entities);
	public static final Sprite crystalwarhammer = new Sprite(48, 9, SpriteSheet.entities);
	public static final Sprite crystalpick = new Sprite(50, 9, SpriteSheet.entities);
	public static final Sprite crystalhalbert = new Sprite(51, 9, SpriteSheet.entities);
	public static final Sprite woodsword = new Sprite(42, 6, SpriteSheet.entities);
	public static final Sprite coppersword = new Sprite(43, 6, SpriteSheet.entities);
	public static final Sprite ironsword = new Sprite(44, 6, SpriteSheet.entities);
	public static final Sprite goldsword = new Sprite(45, 6, SpriteSheet.entities);
	public static final Sprite crystalsword = new Sprite(46, 6, SpriteSheet.entities);
	public static final Sprite wooddagger = new Sprite(42, 7, SpriteSheet.entities);
	public static final Sprite copperdagger = new Sprite(43, 7, SpriteSheet.entities);
	public static final Sprite irondagger = new Sprite(44, 7, SpriteSheet.entities);
	public static final Sprite golddagger = new Sprite(45, 7, SpriteSheet.entities);
	public static final Sprite crystaldagger = new Sprite(46, 7, SpriteSheet.entities);
	public static final Sprite woodscimitar = new Sprite(42, 9, SpriteSheet.entities);
	public static final Sprite bronzescimitar = new Sprite(43, 9, SpriteSheet.entities);
	public static final Sprite ironscimitar = new Sprite(44, 9, SpriteSheet.entities);
	public static final Sprite goldscimitar = new Sprite(45, 9, SpriteSheet.entities);
	public static final Sprite crystalscimitar = new Sprite(46, 9, SpriteSheet.entities);
	public static final Sprite woodbuckler = new Sprite(33, 1, SpriteSheet.entities);
	public static final Sprite ironbuckler = new Sprite(37, 1, SpriteSheet.entities);
	public static final Sprite ironheater = new Sprite(39, 0, SpriteSheet.entities);
	public static final Sprite goldbuckler = new Sprite(33, 4, SpriteSheet.entities);
	public static final Sprite goldheater = new Sprite(35, 3, SpriteSheet.entities);
	public static final Sprite copperbuckler = new Sprite(37, 4, SpriteSheet.entities);
	public static final Sprite copperheater = new Sprite(39, 3, SpriteSheet.entities);
	public static final Sprite crystalbuckler = new Sprite(37, 7, SpriteSheet.entities);
	public static final Sprite crystalheater = new Sprite(39, 6, SpriteSheet.entities);
	public static final Sprite woodheater = new Sprite(35, 0, SpriteSheet.entities);
	public static final Sprite copperspear = new Sprite(44, 1, SpriteSheet.entities);

	// furnace
	public static final Sprite furnaceOff = new Sprite(12, 0, SpriteSheet.tiles);
	public static final Sprite furnaceOn1 = new Sprite(13, 0, SpriteSheet.tiles);
	public static final Sprite furnaceOn2 = new Sprite(14, 0, SpriteSheet.tiles);

	// anvil
	public static final Sprite anvil = new Sprite(15, 0, SpriteSheet.tiles);

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
		case COPPER: {
			switch (type) {
			case AXE:
				return copperaxe;
			case BUCKLER:
				return copperbuckler;
			case DAGGER:
				return copperdagger;
			case HALBERT:
				return copperhalbert;
			case HEATER:
				return copperheater;
			case PICK:
				return copperpick;
			case SCIMITAR:
				return bronzescimitar;
			case SPEAR:
				return copperspear;
			case SWORD:
				return coppersword;
			case WARHAMMER:
				return copperwarhammer;
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
