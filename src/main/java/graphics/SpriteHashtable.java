package graphics;

import entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;

public final class SpriteHashtable {
	private SpriteHashtable() {}
	private static final Logger logger = LoggerFactory.getLogger(SpriteHashtable.class);

	private static final Hashtable<Integer, Sprite> table = new Hashtable<>();

	// return random grass sprite
	public static Sprite getGrass() {
		return Entity.RANDOM.nextBoolean() ? get(2) : get(5);
	}

	// return random stone sprite
	public static Sprite getStone() {
		return Entity.RANDOM.nextBoolean() ? get(8) : get(9);
	}

	// return random dirt sprite
	public static Sprite getDirt() {
		return Entity.RANDOM.nextBoolean() ? get(6) : get(7);
	}

	// returns random skin color sprite
	public static Sprite getPerson() {
		byte n = (byte) Entity.RANDOM.nextInt(3);
		return n == 0 ? get(48) : n == 1 ? get(49) : get(50);
	}

	public static Sprite getHair(boolean male) {
		return get(male ? maleHairNrs[Entity.RANDOM.nextInt(maleHairNrs.length)]
			: femaleHairNrs[Entity.RANDOM.nextInt(femaleHairNrs.length)]
		);
	}

	// return random sand sprite
	public static Sprite getSand() {
		return Entity.RANDOM.nextBoolean() ? get(10) : get(11);
	}

	public static void registerSprite(int key, Sprite sprite) throws Exception {
		if (table.put(key, sprite) != null) {
			throw new Exception("Duplicate key while registering sprite: " + key);
		}
	}

	public static int[] maleHairNrs = { 211, 213, 214, 215, 217, 218, 219, 223, 227, 228, 229, 230, 231, 232, 233, 234,
		235, 237, 238, 239, 241, 242, 243, 247, 251, 252, 253, 254, 255, 256, 257, 258, 259, 261, 262, 263, 267, 268,
		269, 270
	};

	public static int[] femaleHairNrs = { 212, 216, 220, 221, 222, 224, 225, 226, 236, 240, 244, 245, 246, 248, 249,
		250, 260, 264, 265, 266
	};

	public static Sprite get(int key) {
		if (!table.containsKey(key)) {
			logger.warn("NullPointer imminent! Trying to get Sprite with id: " + key + ", but no sprite with this key is present!");
		}
		return table.get(key);
	}

	public static void registerSprites() throws Exception { //registers all sprites by id
		registerSprite(1, new StaticSprite(0, 0, 1)); //water
		registerSprite(2, new StaticSprite(5, 0, 1)); //grass
		registerSprite(3, new StaticSprite(0, 8, 1)); //void tile
		registerSprite(4, new StaticSprite(0, 5, 1)); //darkGrass
		registerSprite(5, new StaticSprite(5, 1, 1)); //grass2
		registerSprite(6, new StaticSprite(6, 0, 1)); //dirt
		registerSprite(7, new StaticSprite(6, 1, 1)); //dirt2
		registerSprite(8, new StaticSprite(7, 0, 1)); //stone
		registerSprite(9, new StaticSprite(7, 1, 1)); //stone2
		registerSprite(10, new StaticSprite(8, 0, 1)); //sand
		registerSprite(11, new StaticSprite(8, 1, 1)); //sand2
		registerSprite(12, new StaticSprite(13, 11, 1)); //treebottom
		registerSprite(13, new StaticSprite(13, 10, 1)); //treetop
		registerSprite(14, new StaticSprite(27, 15, 1)); //StoneNoSides
		registerSprite(15, new StaticSprite(32, 13, 1));
		registerSprite(16, new StaticSprite(33, 13, 1));
		registerSprite(17, new StaticSprite(32, 14, 1));
		registerSprite(18, new StaticSprite(33, 14, 1));
		registerSprite(19, new StaticSprite(32, 15, 1));
		registerSprite(20, new StaticSprite(33, 15, 1));
		registerSprite(21, new StaticSprite(32, 16, 1));
		registerSprite(22, new StaticSprite(33, 16, 1));
		registerSprite(23, new StaticSprite(32, 17, 1));
		registerSprite(24, new StaticSprite(33, 17, 1));
		registerSprite(25, new StaticSprite(32, 18, 1));
		registerSprite(26, new StaticSprite(33, 18, 1));
		registerSprite(27, new StaticSprite(32, 19, 1));
		registerSprite(28, new StaticSprite(33, 19, 1));
		registerSprite(33, new StaticSprite(32, 22, 1));
		registerSprite(34, new StaticSprite(33, 22, 1));
		registerSprite(35, new StaticSprite(27, 18, 1));
		registerSprite(36, new StaticSprite(28, 18, 1));
		registerSprite(37, new StaticSprite(29, 18, 1));
		registerSprite(38, new StaticSprite(30, 18, 1));
		registerSprite(39, new StaticSprite(38, 10, 1));//chest
		registerSprite(40, new StaticSprite(36, 18, 1)); //stairs Top
		registerSprite(41, new StaticSprite(34, 18, 1)); //stairs bottom
		registerSprite(42,new StaticSprite(3,6,2)); //turquoise trousers long
		registerSprite(43,new StaticSprite(13,0,2)); //blacksmith apron turquoise
		registerSprite(44, new StaticSprite(32, 1, 1)); //woodenDoorHorizontal
		registerSprite(45, new StaticSprite(37, 1, 1)); //woodenDoorVertical
		registerSprite(46, new StaticSprite(32, 2, 1));//stoneDoorHorizontal
		registerSprite(47, new StaticSprite(32, 4, 1));//stoneDoorVertical
		registerSprite(48, new StaticSprite(1, 0, 2));//villager1
		registerSprite(49, new StaticSprite(1, 1, 2));//villager2
		registerSprite(50, new StaticSprite(1, 2, 2));//villager3
		registerSprite(51, new StaticSprite(0, 3, 2));//zombie
		registerSprite(52, new StaticSprite(1, 3, 2));//zombiehit
		registerSprite(53, new StaticSprite(0, 23, 1));//coalOre
		registerSprite(54, new StaticSprite(0, 24, 1));//ironOre
		registerSprite(55, new StaticSprite(0, 26, 1));//copperOre
		registerSprite(56, new StaticSprite(1, 25, 1));//goldOre
		registerSprite(57, new StaticSprite(0, 25, 1));//crystalOre
		registerSprite(58, new StaticSprite(53, 22, 1));//logs
		registerSprite(59, new StaticSprite(42, 10, 1));//ironBar
		registerSprite(60, new StaticSprite(43, 10, 1));//goldBar
		registerSprite(61, new StaticSprite(41, 10, 1));//copperBar
		registerSprite(62, new StaticSprite(44, 10, 1));//ironChunk
		registerSprite(63, new StaticSprite(45, 10, 1));//goldChunk
		registerSprite(64, new StaticSprite(46, 10, 1));//stoneChunk
		registerSprite(65, new StaticSprite(47, 10, 1));//coalChunk
		registerSprite(66, new StaticSprite(48, 10, 1));//copperChunk
		registerSprite(67, new StaticSprite(41, 24, 1));//crystalChunkUncut
		registerSprite(68, new StaticSprite(41, 23, 1));//crystalChunkCut
		registerSprite(69, new StaticSprite(3, 0, 2));//blacktrousers
		registerSprite(70, new StaticSprite(6, 0, 2));//brownShirt1
		registerSprite(71, new StaticSprite(7, 0, 2));//brownShirt2
		registerSprite(72, new StaticSprite(8, 0, 2));//brownShirt3
		registerSprite(73, new StaticSprite(9, 0, 2));//brownShirt4
		registerSprite(74, new StaticSprite(10, 0, 2));//greenShirt1
		registerSprite(75, new StaticSprite(11, 0, 2));//greenShirt2
		registerSprite(76, new StaticSprite(12, 0, 2));//greenShirt3
		registerSprite(77, new StaticSprite(13, 0, 2));//greenShirt4
		registerSprite(78, new StaticSprite(14, 0, 2));//greyShirt1
		registerSprite(79, new StaticSprite(15, 0, 2));//greyShirt2
		registerSprite(80, new StaticSprite(16, 0, 2));//greyShirt3
		registerSprite(81, new StaticSprite(17, 0, 2));//greyShirt4
		registerSprite(82, new StaticSprite(12, 0, 1));//furnaceOff
		registerSprite(83, new StaticSprite(13, 0, 1));//furnaceOn1
		registerSprite(84, new StaticSprite(14, 0, 1));//furnaceOn2
		registerSprite(85, new StaticSprite(15, 0, 1));//anvil
		registerSprite(86, new StaticSprite(44, 4, 2));//woodspear
		registerSprite(87, new StaticSprite(42, 0, 2));//copperaxe
		registerSprite(88, new StaticSprite(3, 1, 2));//browntrousers
		registerSprite(89, new StaticSprite(3, 5, 2));//orangetrousers
		registerSprite(90, new StaticSprite(46, 0, 2));//copperhalbert
		registerSprite(91, new StaticSprite(42, 5, 2));//woodaxe
		//GAP
		registerSprite(94, new StaticSprite(46, 5, 2));//woodhalbert
		registerSprite(95, new StaticSprite(52, 0, 2));//woodbow
		registerSprite(96, new StaticSprite(49, 4, 2));//ironspear
		registerSprite(97, new StaticSprite(47, 5, 2));//ironaxe
		registerSprite(98, new StaticSprite(53, 1, 2));//ironbow
		registerSprite(99, new StaticSprite(52, 2, 2));//goldbow
		registerSprite(100, new StaticSprite(52, 4, 2));//crystalbow
		//GAP
		registerSprite(103, new StaticSprite(51, 5, 2));//ironhalbert
		registerSprite(104, new StaticSprite(49, 6, 2));//goldspear
		registerSprite(105, new StaticSprite(47, 7, 2));//goldaxe
		//GAP
		registerSprite(108, new StaticSprite(51, 7, 2));//goldhalbert
		registerSprite(109, new StaticSprite(49, 8, 2));//crystalspear
		registerSprite(110, new StaticSprite(47, 9, 2));//crystalaxe
		//GAP
		registerSprite(113, new StaticSprite(51, 9, 2));//crystalhalbert
		registerSprite(114, new StaticSprite(42, 6, 2));//woodsword
		registerSprite(115, new StaticSprite(43, 6, 2));//coppersword
		registerSprite(116, new StaticSprite(44, 6, 2));//ironsword
		registerSprite(117, new StaticSprite(45, 6, 2));//goldsword
		registerSprite(118, new StaticSprite(46, 6, 2));//crystalsword
		registerSprite(119, new StaticSprite(42, 7, 2));//wooddagger
		registerSprite(120, new StaticSprite(43, 7, 2));//copperdagger
		registerSprite(121, new StaticSprite(44, 7, 2));//irondagger
		registerSprite(122, new StaticSprite(45, 7, 2));//golddagger
		registerSprite(123, new StaticSprite(46, 7, 2));//crystaldagger
		registerSprite(124, new StaticSprite(42, 9, 2));//woodscimitar
		registerSprite(125, new StaticSprite(43, 9, 2));//copperscimitar
		registerSprite(126, new StaticSprite(44, 9, 2));//ironscimitar
		registerSprite(127, new StaticSprite(45, 9, 2));//goldscimitar
		registerSprite(128, new StaticSprite(46, 9, 2));//crystalscimitar
		registerSprite(129, new StaticSprite(33, 1, 2));//woodbuckler
		registerSprite(130, new StaticSprite(37, 1, 2));//ironbuckler
		registerSprite(131, new StaticSprite(39, 0, 2));//ironheater
		registerSprite(132, new StaticSprite(33, 4, 2));//goldbuckler
		registerSprite(133, new StaticSprite(35, 3, 2));//goldheater
		registerSprite(134, new StaticSprite(37, 4, 2));//copperbuckler
		registerSprite(135, new StaticSprite(39, 3, 2));//copperheater
		registerSprite(136, new StaticSprite(37, 7, 2));//crystalbuckler
		registerSprite(137, new StaticSprite(39, 6, 2));//crystalheater
		registerSprite(138, new StaticSprite(35, 0, 2));//woodheater
		registerSprite(139, new StaticSprite(44, 1, 2));//copperspear
		registerSprite(140, new StaticSprite(1, 5, 1)); //darkStone
		registerSprite(141, new StaticSprite(5, 6, 1));
		registerSprite(142, new StaticSprite(6, 6, 1));
		//GAP

		registerSprite(161, new StaticSprite(9, 0, 1)); //HardStoneNoSides
		registerSprite(162, new StaticSprite(10, 0, 1));
		registerSprite(163, new StaticSprite(11, 0, 1));
		registerSprite(164, new StaticSprite(10, 1, 1));
		registerSprite(165, new StaticSprite(11, 1, 1));
		registerSprite(166, new StaticSprite(10, 2, 1));
		registerSprite(167, new StaticSprite(11, 2, 1));
		registerSprite(168, new StaticSprite(10, 3, 1));
		registerSprite(169, new StaticSprite(11, 3, 1));
		registerSprite(170, new StaticSprite(10, 4, 1));
		registerSprite(171, new StaticSprite(11, 4, 1));
		registerSprite(172, new StaticSprite(10, 5, 1));
		registerSprite(173, new StaticSprite(11, 5, 1));
		registerSprite(174, new StaticSprite(10, 6, 1));
		registerSprite(175, new StaticSprite(11, 6, 1));
		registerSprite(176, new StaticSprite(10, 7, 1));
		registerSprite(177, new StaticSprite(11, 7, 1));
		registerSprite(178, new StaticSprite(10, 8, 1));
		registerSprite(179, new StaticSprite(11, 8, 1));
		registerSprite(180, new StaticSprite(10, 9, 1));
		registerSprite(181, new StaticSprite(11, 9, 1));

		registerSprite(186, new StaticSprite(35, 14, 1)); //WoodNoSides
		registerSprite(187, new StaticSprite(39, 12, 1));
		registerSprite(188, new StaticSprite(40, 12, 1));
		registerSprite(189, new StaticSprite(39, 13, 1));
		registerSprite(190, new StaticSprite(40, 13, 1));
		registerSprite(191, new StaticSprite(39, 14, 1));
		registerSprite(192, new StaticSprite(40, 14, 1));
		registerSprite(193, new StaticSprite(39, 15, 1));
		registerSprite(194, new StaticSprite(40, 15, 1));
		registerSprite(195, new StaticSprite(39, 16, 1));
		registerSprite(196, new StaticSprite(40, 16, 1));
		registerSprite(197, new StaticSprite(39, 17, 1));
		registerSprite(198, new StaticSprite(40, 17, 1));
		registerSprite(199, new StaticSprite(39, 18, 1));
		registerSprite(200, new StaticSprite(40, 18, 1));
		//GAP
		registerSprite(205, new StaticSprite(39, 21, 1));
		registerSprite(206, new StaticSprite(40, 21, 1));
		registerSprite(207, new StaticSprite(35, 17, 1));
		registerSprite(208, new StaticSprite(36, 17, 1));
		registerSprite(209, new StaticSprite(37, 17, 1));
		registerSprite(210, new StaticSprite(38, 17, 1));

		registerSprite(211, new StaticSprite(19, 0, 2)); // brownHair1
		registerSprite(212, new StaticSprite(20, 0, 2)); // brownHair2
		registerSprite(213, new StaticSprite(21, 0, 2)); // brownHairBeard1
		registerSprite(214, new StaticSprite(22, 0, 2)); // brownHairBeard2
		registerSprite(215, new StaticSprite(23, 0, 2)); // lightBrownHair1
		registerSprite(216, new StaticSprite(24, 0, 2)); // lightBrownHair2
		registerSprite(217, new StaticSprite(25, 0, 2)); // lightBrownHairBeard1
		registerSprite(218, new StaticSprite(26, 0, 2)); // lightBrownHairBeard2
		registerSprite(219, new StaticSprite(19, 1, 2)); // brownHair3
		registerSprite(220, new StaticSprite(20, 1, 2)); // brownHair4
		registerSprite(221, new StaticSprite(21, 1, 2)); // brownHair5
		registerSprite(222, new StaticSprite(22, 1, 2)); // brownHair6
		registerSprite(223, new StaticSprite(23, 1, 2)); // lightBrownHair3
		registerSprite(224, new StaticSprite(24, 1, 2)); // lightBrownHair4
		registerSprite(225, new StaticSprite(25, 1, 2)); // lightBrownHair5
		registerSprite(226, new StaticSprite(26, 1, 2)); // lightBrownHair6
		registerSprite(227, new StaticSprite(19, 2, 2)); // brownHairBeard3
		registerSprite(228, new StaticSprite(20, 2, 2)); // brownHair7
		registerSprite(229, new StaticSprite(21, 2, 2)); // brownHair8
		registerSprite(230, new StaticSprite(22, 2, 2)); // brownHair9
		registerSprite(231, new StaticSprite(23, 2, 2)); // lightBrownHairBeard3
		registerSprite(232, new StaticSprite(24, 2, 2)); // lightBrownHair7
		registerSprite(233, new StaticSprite(25, 2, 2)); // lightBrownHair8
		registerSprite(234, new StaticSprite(26, 2, 2)); // lightBrownHair9
		registerSprite(235, new StaticSprite(19, 4, 2)); // blondeHair1
		registerSprite(236, new StaticSprite(20, 4, 2)); // blondeHair2
		registerSprite(237, new StaticSprite(21, 4, 2)); // blondeHairBeard1
		registerSprite(238, new StaticSprite(22, 4, 2)); // blondeHairBeard2
		registerSprite(239, new StaticSprite(23, 4, 2)); // blackHair1
		registerSprite(240, new StaticSprite(24, 4, 2)); // blackHair2
		registerSprite(241, new StaticSprite(25, 4, 2)); // blackHairBeard1
		registerSprite(242, new StaticSprite(26, 4, 2)); // blackHairBeard2
		registerSprite(243, new StaticSprite(19, 5, 2)); // blondeHair3
		registerSprite(244, new StaticSprite(20, 5, 2)); // blondeHair4
		registerSprite(245, new StaticSprite(21, 5, 2)); // blondeHair5
		registerSprite(246, new StaticSprite(22, 5, 2)); // blondeHair6
		registerSprite(247, new StaticSprite(23, 5, 2)); // blackHair3
		registerSprite(248, new StaticSprite(24, 5, 2)); // blackHair4
		registerSprite(249, new StaticSprite(25, 5, 2)); // blackHair5
		registerSprite(250, new StaticSprite(26, 5, 2)); // blackHair6
		registerSprite(251, new StaticSprite(19, 6, 2)); // blondeHairBeard3
		registerSprite(252, new StaticSprite(20, 6, 2)); // blondeHair7
		registerSprite(253, new StaticSprite(21, 6, 2)); // blondeHair8
		registerSprite(254, new StaticSprite(22, 6, 2)); // blondeHair9
		registerSprite(255, new StaticSprite(13, 6, 2)); // blackHairBeard3
		registerSprite(256, new StaticSprite(24, 6, 2)); // blackHair7
		registerSprite(257, new StaticSprite(25, 6, 2)); // blackHair8
		registerSprite(258, new StaticSprite(26, 6, 2)); // blackHair9
		registerSprite(259, new StaticSprite(19, 8, 2)); // whiteHair1
		registerSprite(260, new StaticSprite(20, 8, 2)); // whiteHair2
		registerSprite(261, new StaticSprite(21, 8, 2)); // whiteHairBeard1
		registerSprite(262, new StaticSprite(22, 8, 2)); // whiteHairBeard2
		registerSprite(263, new StaticSprite(19, 9, 2)); // whiteHair3
		registerSprite(264, new StaticSprite(20, 9, 2)); // whiteHair4
		registerSprite(265, new StaticSprite(21, 9, 2)); // whiteHair5
		registerSprite(266, new StaticSprite(22, 9, 2)); // whiteHair6
		registerSprite(267, new StaticSprite(19, 10, 2)); // whiteHairBeard3
		registerSprite(268, new StaticSprite(20, 10, 2)); // whiteHair7
		registerSprite(269, new StaticSprite(21, 10, 2)); // whiteHair8
		registerSprite(270, new StaticSprite(22, 10, 2)); // whiteHair9
		registerSprite(271, new AnimationSprite(new Sprite[] { get(83), get(84) }, new int[] { 30, 30 }));
	}

}
