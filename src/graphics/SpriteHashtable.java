package graphics;

import entity.item.weapon.WeaponMaterial;
import entity.item.weapon.WeaponType;

import java.util.Hashtable;
import java.util.Random;

public class SpriteHashtable {
    private static final Hashtable<Integer, Sprite> table = new Hashtable<>();
    private static final Random rand = new Random(); // random object used for random distributionof sprites

    public static Sprite[] STONEWALLSPRITES;
    public static Sprite[] WOODWALLSPRITES;
    public static Sprite[] WOODDOORSPRITES;
    public static Sprite[] STONEDOORSPRITES;


    // return random grass sprite
    public static Sprite getGrass() {
        return rand.nextBoolean() ? get(2) : get(5);
    }

    // return random stone sprite
    public static Sprite getStone() {
        return rand.nextBoolean() ? get(8) : get(9);
    }

    // return random dirt sprite
    public static Sprite getDirt() {
        return rand.nextBoolean() ? get(6) : get(7);
    }

    // returns random skin color sprite
    public static Sprite getPerson() {
        byte n = (byte) rand.nextInt(3);
        return n == 0 ? get(48) : n == 1 ? get(49) : get(50);
    }

    // return random sand sprite
    public static Sprite getSand() {
        return rand.nextBoolean() ? get(10) : get(11);
    }

    public static Sprite getWeaponSprite(WeaponType type, WeaponMaterial mat) {
        switch (mat) {
            case COPPER: {
                switch (type) {
                    case AXE:
                        return get(87);
                    case BUCKLER:
                        return get(134);
                    case DAGGER:
                        return get(120);
                    case HALBERT:
                        return get(90);
                    case HEATER:
                        return get(135);
                    case PICK:
                        return get(89);
                    case SCIMITAR:
                        return get(125);
                    case SPEAR:
                        return get(139);
                    case SWORD:
                        return get(115);
                    case WARHAMMER:
                        return get(88);
                    default:
                        return null;
                }
            }
            case CRYSTAL: {
                switch (type) {
                    case AXE:
                        return get(110);
                    case BUCKLER:
                        return get(136);
                    case DAGGER:
                        return get(123);
                    case HALBERT:
                        return get(113);
                    case HEATER:
                        return get(137);
                    case PICK:
                        return get(112);
                    case SCIMITAR:
                        return get(128);
                    case SPEAR:
                        return get(109);
                    case SWORD:
                        return get(118);
                    case WARHAMMER:
                        return get(111);
                    case BOW:
                        return get(100);
                }
            }
            case GOLD: {
                switch (type) {
                    case AXE:
                        return get(105);
                    case BUCKLER:
                        return get(132);
                    case DAGGER:
                        return get(122);
                    case HALBERT:
                        return get(108);
                    case HEATER:
                        return get(133);
                    case PICK:
                        return get(107);
                    case SCIMITAR:
                        return get(127);
                    case SPEAR:
                        return get(104);
                    case SWORD:
                        return get(117);
                    case WARHAMMER:
                        return get(106);
                    case BOW:
                        return get(99);
                }
            }
            case IRON: {
                switch (type) {
                    case AXE:
                        return get(97);
                    case BUCKLER:
                        return get(130);
                    case DAGGER:
                        return get(121);
                    case HALBERT:
                        return get(103);
                    case HEATER:
                        return get(131);
                    case PICK:
                        return get(102);
                    case SCIMITAR:
                        return get(126);
                    case SPEAR:
                        return get(96);
                    case SWORD:
                        return get(116);
                    case WARHAMMER:
                        return get(101);
                    case BOW:
                        return get(98);
                }
            }
            case WOOD: {
                switch (type) {
                    case AXE:
                        return get(91);
                    case BOW:
                        return get(95);
                    case BUCKLER:
                        return get(129);
                    case DAGGER:
                        return get(119);
                    case HALBERT:
                        return get(94);
                    case HEATER:
                        return get(138);
                    case PICK:
                        return get(93);
                    case SCIMITAR:
                        return get(124);
                    case SPEAR:
                        return get(86);
                    case SWORD:
                        return get(114);
                    case WARHAMMER:
                        return get(92);
                }
            }
        }
        return null;
    }

    public static void registerSprite(int key, Sprite sprite) throws Exception {
        if (table.put(key, sprite) != null) {
            throw new Exception("Duplicate key while registering sprite: " + key);
        }
    }

    public static Sprite get(int key) {
        return table.get(key);
    }

    public static void registerSprites() throws Exception { //registers all sprites by id
        registerSprite(1, new Sprite(0, 0, SpritesheetHashtable.get(1))); //water
        registerSprite(2, new Sprite(5, 0, SpritesheetHashtable.get(1))); //grass
        registerSprite(3, new Sprite()); //void
        registerSprite(4, new Sprite(0, 5, SpritesheetHashtable.get(1))); //darkGrass
        registerSprite(5, new Sprite(5, 1, SpritesheetHashtable.get(1))); //grass2 
        registerSprite(6, new Sprite(6, 0, SpritesheetHashtable.get(1))); //dirt
        registerSprite(7, new Sprite(6, 1, SpritesheetHashtable.get(1))); //dirt2
        registerSprite(8, new Sprite(7, 0, SpritesheetHashtable.get(1))); //stone
        registerSprite(9, new Sprite(7, 1, SpritesheetHashtable.get(1))); //stone2
        registerSprite(10, new Sprite(8, 0, SpritesheetHashtable.get(1))); //sand
        registerSprite(11, new Sprite(8, 1, SpritesheetHashtable.get(1))); //sand2
        registerSprite(12, new Sprite(13, 11, SpritesheetHashtable.get(1))); //treebottom
        registerSprite(13, new Sprite(13, 10, SpritesheetHashtable.get(1))); //treetop
        registerSprite(14, new Sprite(27, 12, SpritesheetHashtable.get(1))); //stoneWallHorizontalRight
        registerSprite(15, new Sprite(28, 12, SpritesheetHashtable.get(1))); //stoneWallHorizontalBoth
        registerSprite(16, new Sprite(29, 12, SpritesheetHashtable.get(1))); //stoneWallHorizontalLeft
        registerSprite(17, new Sprite(27, 13, SpritesheetHashtable.get(1))); //stoneWallVerticalBottom
        registerSprite(18, new Sprite(28, 13, SpritesheetHashtable.get(1))); //stoneWall4sides
        registerSprite(19, new Sprite(29, 13, SpritesheetHashtable.get(1))); //stoneWallVerticalBoth
        registerSprite(20, new Sprite(30, 13, SpritesheetHashtable.get(1))); //stoneWallCornerTopRight
        registerSprite(21, new Sprite(31, 13, SpritesheetHashtable.get(1))); //stoneWallCornerTopLeft
        registerSprite(22, new Sprite(27, 14, SpritesheetHashtable.get(1))); //stoneWallVerticalTop
        registerSprite(23, new Sprite(30, 12, SpritesheetHashtable.get(1))); //stoneWallCornerBottomRight
        registerSprite(24, new Sprite(31, 12, SpritesheetHashtable.get(1))); //stoneWallCornerBottomLeft
        registerSprite(25, new Sprite(32, 12, SpritesheetHashtable.get(1))); //stoneWallTRight
        registerSprite(26, new Sprite(33, 12, SpritesheetHashtable.get(1))); //stoneWallTTop
        registerSprite(27, new Sprite(31, 13, SpritesheetHashtable.get(1))); //stoneWallTLeft
        registerSprite(28, new Sprite(32, 13, SpritesheetHashtable.get(1))); //stoneWallTBottom
        registerSprite(29, new Sprite(34, 12, SpritesheetHashtable.get(1))); //woodenWallHorizontalRight
        registerSprite(30, new Sprite(35, 12, SpritesheetHashtable.get(1))); //woodenWallHorizontalBoth
        registerSprite(31, new Sprite(36, 12, SpritesheetHashtable.get(1))); //woodenWallHorizontalLeft
        registerSprite(32, new Sprite(34, 13, SpritesheetHashtable.get(1))); //woodenWallVerticalBottom
        registerSprite(33, new Sprite(35, 13, SpritesheetHashtable.get(1))); //woodenWall4sides
        registerSprite(34, new Sprite(36, 13, SpritesheetHashtable.get(1))); //woodenWallVerticalBoth
        registerSprite(35, new Sprite(37, 13, SpritesheetHashtable.get(1))); //woodenWallCornerTopRight
        registerSprite(36, new Sprite(38, 13, SpritesheetHashtable.get(1))); //woodenWallCornerTopLeft
        registerSprite(37, new Sprite(34, 14, SpritesheetHashtable.get(1))); //woodenWallVerticalTop
        registerSprite(38, new Sprite(37, 12, SpritesheetHashtable.get(1))); //woodenWallCornerBottomRight
        registerSprite(39, new Sprite(38, 12, SpritesheetHashtable.get(1))); //woodenWallCornerBottomLeft
        registerSprite(40, new Sprite(39, 12, SpritesheetHashtable.get(1))); //woodenWallTRight
        registerSprite(41, new Sprite(40, 12, SpritesheetHashtable.get(1))); //woodenWallTTop
        registerSprite(42, new Sprite(39, 13, SpritesheetHashtable.get(1))); //woodenWallTLeft
        registerSprite(43, new Sprite(40, 13, SpritesheetHashtable.get(1))); //woodenWallTBottom
        registerSprite(44, new Sprite(32, 1, SpritesheetHashtable.get(1))); //woodenDoorHorizontal
        registerSprite(45, new Sprite(37, 1, SpritesheetHashtable.get(1))); //woodenDoorVertical
        registerSprite(46, new Sprite(32, 2, SpritesheetHashtable.get(1)));//stoneDoorHorizontal
        registerSprite(47, new Sprite(32, 4, SpritesheetHashtable.get(1)));//stoneDoorVertical
        registerSprite(48, new Sprite(1, 0, SpritesheetHashtable.get(2)));//villager1
        registerSprite(49, new Sprite(1, 1, SpritesheetHashtable.get(2)));//villager2
        registerSprite(50, new Sprite(1, 2, SpritesheetHashtable.get(2)));//villager3
        registerSprite(51, new Sprite(0, 3, SpritesheetHashtable.get(2)));//zombie
        registerSprite(52, new Sprite(1, 3, SpritesheetHashtable.get(2)));//zombiehit
        registerSprite(53, new Sprite(0, 23, SpritesheetHashtable.get(1)));//coalOre
        registerSprite(54, new Sprite(0, 24, SpritesheetHashtable.get(1)));//ironOre
        registerSprite(55, new Sprite(0, 26, SpritesheetHashtable.get(1)));//copperOre
        registerSprite(56, new Sprite(1, 25, SpritesheetHashtable.get(1)));//goldOre
        registerSprite(57, new Sprite(0, 25, SpritesheetHashtable.get(1)));//crystalOre
        registerSprite(58, new Sprite(53, 22, SpritesheetHashtable.get(1)));//logs
        registerSprite(59, new Sprite(42, 10, SpritesheetHashtable.get(1)));//ironBar
        registerSprite(60, new Sprite(43, 10, SpritesheetHashtable.get(1)));//goldBar
        registerSprite(61, new Sprite(41, 10, SpritesheetHashtable.get(1)));//copperBar
        registerSprite(62, new Sprite(44, 10, SpritesheetHashtable.get(1)));//ironChunk
        registerSprite(63, new Sprite(45, 10, SpritesheetHashtable.get(1)));//goldChunk
        registerSprite(64, new Sprite(46, 10, SpritesheetHashtable.get(1)));//stoneChunk
        registerSprite(65, new Sprite(47, 10, SpritesheetHashtable.get(1)));//coalChunk
        registerSprite(66, new Sprite(48, 10, SpritesheetHashtable.get(1)));//copperChunk
        registerSprite(67, new Sprite(41, 24, SpritesheetHashtable.get(1)));//crystalChunkUncut
        registerSprite(68, new Sprite(41, 23, SpritesheetHashtable.get(1)));//crystalChunkCut
        registerSprite(69, new Sprite(4, 0, SpritesheetHashtable.get(2)));//blacktrousers
        registerSprite(70, new Sprite(6, 0, SpritesheetHashtable.get(2)));//brownShirt1
        registerSprite(71, new Sprite(7, 0, SpritesheetHashtable.get(2)));//brownShirt2
        registerSprite(72, new Sprite(8, 0, SpritesheetHashtable.get(2)));//brownShirt3
        registerSprite(73, new Sprite(9, 0, SpritesheetHashtable.get(2)));//brownShirt4
        registerSprite(74, new Sprite(10, 0, SpritesheetHashtable.get(2)));//greenShirt1
        registerSprite(75, new Sprite(11, 0, SpritesheetHashtable.get(2)));//greenShirt2
        registerSprite(76, new Sprite(12, 0, SpritesheetHashtable.get(2)));//greenShirt3
        registerSprite(77, new Sprite(13, 0, SpritesheetHashtable.get(2)));//greenShirt4
        registerSprite(78, new Sprite(14, 0, SpritesheetHashtable.get(2)));//greyShirt1
        registerSprite(79, new Sprite(15, 0, SpritesheetHashtable.get(2)));//greyShirt2
        registerSprite(80, new Sprite(16, 0, SpritesheetHashtable.get(2)));//greyShirt3
        registerSprite(81, new Sprite(17, 0, SpritesheetHashtable.get(2)));//greyShirt4
        registerSprite(82, new Sprite(12, 0, SpritesheetHashtable.get(1)));//furnaceOff
        registerSprite(83, new Sprite(13, 0, SpritesheetHashtable.get(1)));//furnaceOn1
        registerSprite(84, new Sprite(14, 0, SpritesheetHashtable.get(1)));//furnaceOn2
        registerSprite(85, new Sprite(15, 0, SpritesheetHashtable.get(1)));//anvil
        registerSprite(86, new Sprite(44, 4, SpritesheetHashtable.get(2)));//woodspear
        registerSprite(87, new Sprite(42, 0, SpritesheetHashtable.get(2)));//copperaxe
        registerSprite(88, new Sprite(43, 0, SpritesheetHashtable.get(2)));//copperwarhammer
        registerSprite(89, new Sprite(45, 0, SpritesheetHashtable.get(2)));//copperpick
        registerSprite(90, new Sprite(46, 0, SpritesheetHashtable.get(2)));//copperhalbert
        registerSprite(91, new Sprite(42, 5, SpritesheetHashtable.get(2)));//woodaxe
        registerSprite(92, new Sprite(43, 5, SpritesheetHashtable.get(2)));//woodwarhammer
        registerSprite(93, new Sprite(45, 5, SpritesheetHashtable.get(2)));//woodpick
        registerSprite(94, new Sprite(46, 5, SpritesheetHashtable.get(2)));//woodhalbert
        registerSprite(95, new Sprite(52, 0, SpritesheetHashtable.get(2)));//woodbow
        registerSprite(96, new Sprite(49, 4, SpritesheetHashtable.get(2)));//ironspear
        registerSprite(97, new Sprite(47, 5, SpritesheetHashtable.get(2)));//ironaxe
        registerSprite(98, new Sprite(53, 1, SpritesheetHashtable.get(2)));//ironbow
        registerSprite(99, new Sprite(52, 2, SpritesheetHashtable.get(2)));//goldbow
        registerSprite(100, new Sprite(52, 4, SpritesheetHashtable.get(2)));//crystalbow
        registerSprite(101, new Sprite(48, 5, SpritesheetHashtable.get(2)));//ironwarhammer
        registerSprite(102, new Sprite(50, 5, SpritesheetHashtable.get(2)));//ironpick
        registerSprite(103, new Sprite(51, 5, SpritesheetHashtable.get(2)));//ironhalbert
        registerSprite(104, new Sprite(49, 6, SpritesheetHashtable.get(2)));//goldspear
        registerSprite(105, new Sprite(47, 7, SpritesheetHashtable.get(2)));//goldaxe
        registerSprite(106, new Sprite(48, 7, SpritesheetHashtable.get(2)));//goldwarhammer
        registerSprite(107, new Sprite(50, 7, SpritesheetHashtable.get(2)));//goldpick
        registerSprite(108, new Sprite(51, 7, SpritesheetHashtable.get(2)));//goldhalbert
        registerSprite(109, new Sprite(49, 8, SpritesheetHashtable.get(2)));//crystalspear
        registerSprite(110, new Sprite(47, 9, SpritesheetHashtable.get(2)));//crystalaxe
        registerSprite(111, new Sprite(48, 9, SpritesheetHashtable.get(2)));//crystalwarhammer
        registerSprite(112, new Sprite(50, 9, SpritesheetHashtable.get(2)));//crystalpick
        registerSprite(113, new Sprite(51, 9, SpritesheetHashtable.get(2)));//crystalhalbert
        registerSprite(114, new Sprite(42, 6, SpritesheetHashtable.get(2)));//woodsword
        registerSprite(115, new Sprite(43, 6, SpritesheetHashtable.get(2)));//coppersword
        registerSprite(116, new Sprite(44, 6, SpritesheetHashtable.get(2)));//ironsword
        registerSprite(117, new Sprite(45, 6, SpritesheetHashtable.get(2)));//goldsword
        registerSprite(118, new Sprite(46, 6, SpritesheetHashtable.get(2)));//crystalsword
        registerSprite(119, new Sprite(42, 7, SpritesheetHashtable.get(2)));//wooddagger
        registerSprite(120, new Sprite(43, 7, SpritesheetHashtable.get(2)));//copperdagger
        registerSprite(121, new Sprite(44, 7, SpritesheetHashtable.get(2)));//irondagger
        registerSprite(122, new Sprite(45, 7, SpritesheetHashtable.get(2)));//golddagger
        registerSprite(123, new Sprite(46, 7, SpritesheetHashtable.get(2)));//crystaldagger
        registerSprite(124, new Sprite(42, 9, SpritesheetHashtable.get(2)));//woodscimitar
        registerSprite(125, new Sprite(43, 9, SpritesheetHashtable.get(2)));//copperscimitar
        registerSprite(126, new Sprite(44, 9, SpritesheetHashtable.get(2)));//ironscimitar
        registerSprite(127, new Sprite(45, 9, SpritesheetHashtable.get(2)));//goldscimitar
        registerSprite(128, new Sprite(46, 9, SpritesheetHashtable.get(2)));//crystalscimitar
        registerSprite(129, new Sprite(33, 1, SpritesheetHashtable.get(2)));//woodbuckler
        registerSprite(130, new Sprite(37, 1, SpritesheetHashtable.get(2)));//ironbuckler
        registerSprite(131, new Sprite(39, 0, SpritesheetHashtable.get(2)));//ironheater
        registerSprite(132, new Sprite(33, 4, SpritesheetHashtable.get(2)));//goldbuckler
        registerSprite(133, new Sprite(35, 3, SpritesheetHashtable.get(2)));//goldheater
        registerSprite(134, new Sprite(37, 4, SpritesheetHashtable.get(2)));//copperbuckler
        registerSprite(135, new Sprite(39, 3, SpritesheetHashtable.get(2)));//copperheater
        registerSprite(136, new Sprite(37, 7, SpritesheetHashtable.get(2)));//crystalbuckler
        registerSprite(137, new Sprite(39, 6, SpritesheetHashtable.get(2)));//crystalheater
        registerSprite(138, new Sprite(35, 0, SpritesheetHashtable.get(2)));//woodheater
        registerSprite(139, new Sprite(44, 1, SpritesheetHashtable.get(2)));//copperspear
        registerSprite(140, new Sprite(38, 10, SpritesheetHashtable.get(1)));//chest

        //These sprites have their inside sides coloured in, so multiple walls placed togheter look like one giant block
        registerSprite(141, new Sprite(28, 14, SpritesheetHashtable.get(1))); //stoneWall4sidesEXTRA
        registerSprite(142, new Sprite(32, 14, SpritesheetHashtable.get(1))); //stoneWallTRightEXTRA
        registerSprite(143, new Sprite(33, 14, SpritesheetHashtable.get(1))); //stoneWallTTopEXTRA
        registerSprite(144, new Sprite(31, 15, SpritesheetHashtable.get(1))); //stoneWallTLeftEXTRA
        registerSprite(145, new Sprite(32, 15, SpritesheetHashtable.get(1))); //stoneWallTBottomEXTRA
        registerSprite(146, new Sprite(30, 15, SpritesheetHashtable.get(1))); //stoneWallCornerTopRightEXTRA
        registerSprite(147, new Sprite(31, 15, SpritesheetHashtable.get(1))); //stoneWallCornerTopLeftEXTRA
        registerSprite(148, new Sprite(30, 14, SpritesheetHashtable.get(1))); //stoneWallCornerBottomRightEXTRA
        registerSprite(149, new Sprite(31, 14, SpritesheetHashtable.get(1))); //stoneWallCornerBottomLeftEXTRA

        registerSprite(150, new Sprite(35, 14, SpritesheetHashtable.get(1))); //woodenWall4sidesEXTRA
        registerSprite(151, new Sprite(37, 15, SpritesheetHashtable.get(1))); //woodenWallCornerTopRightEXTRA
        registerSprite(152, new Sprite(38, 15, SpritesheetHashtable.get(1))); //woodenWallCornerTopLeftEXTRA
        registerSprite(154, new Sprite(37, 14, SpritesheetHashtable.get(1))); //woodenWallCornerBottomRightEXTRA
        registerSprite(155, new Sprite(38, 14, SpritesheetHashtable.get(1))); //woodenWallCornerBottomLeftEXTRA
        registerSprite(156, new Sprite(39, 14, SpritesheetHashtable.get(1))); //woodenWallTRightEXTRA
        registerSprite(157, new Sprite(40, 14, SpritesheetHashtable.get(1))); //woodenWallTTopEXTRA
        registerSprite(158, new Sprite(39, 15, SpritesheetHashtable.get(1))); //woodenWallTLeftEXTRA
        registerSprite(153, new Sprite(40, 15, SpritesheetHashtable.get(1))); //woodenWallTBottomEXTRA

        registerSprite(159, new Sprite(34, 15, SpritesheetHashtable.get(1))); //woodenWallNoSides
        registerSprite(160, new Sprite(27, 15, SpritesheetHashtable.get(1))); //StoneWallNoSides

        registerSprite(161, new Sprite(9, 0, SpritesheetHashtable.get(1))); //HardStoneNoSides
        registerSprite(162, new Sprite(10, 0, SpritesheetHashtable.get(1))); //HardStoneBorderLeftS
        registerSprite(163, new Sprite(11, 0, SpritesheetHashtable.get(1))); //HardStoneBorderRight
        registerSprite(164, new Sprite(10, 1, SpritesheetHashtable.get(1))); //HardStoneCornerBottomRight
        registerSprite(165, new Sprite(11, 1, SpritesheetHashtable.get(1))); //HardStoneCornerBottomLeft
        registerSprite(166, new Sprite(10, 2, SpritesheetHashtable.get(1))); //HardStoneCornerTopRight
        registerSprite(167, new Sprite(11, 2, SpritesheetHashtable.get(1))); //HardStoneCornerTopLeft
        registerSprite(168, new Sprite(10, 3, SpritesheetHashtable.get(1))); //HardStoneBorderTop
        registerSprite(169, new Sprite(11, 3, SpritesheetHashtable.get(1))); //HardStoneBorderBottom
        registerSprite(170, new Sprite(10, 5, SpritesheetHashtable.get(1))); //HardStoneVertical
        registerSprite(171, new Sprite(11, 5, SpritesheetHashtable.get(1))); //HardStoneEverySide
        registerSprite(172, new Sprite(10, 6, SpritesheetHashtable.get(1))); //HardStoneURight
        registerSprite(173, new Sprite(11, 6, SpritesheetHashtable.get(1))); //HardStoneULeft
        registerSprite(174, new Sprite(11, 7, SpritesheetHashtable.get(1))); //HardStoneHoriztontal
        registerSprite(175, new Sprite(10, 4, SpritesheetHashtable.get(1))); //HardStoneUBottom
        registerSprite(176, new Sprite(11, 4, SpritesheetHashtable.get(1))); //HardStoneUTop


        STONEDOORSPRITES = new Sprite[]{
                SpriteHashtable.get(47), SpriteHashtable.get(47),
                SpriteHashtable.get(47), SpriteHashtable.get(46), SpriteHashtable.get(47), SpriteHashtable.get(46),
                SpriteHashtable.get(46), SpriteHashtable.get(46), SpriteHashtable.get(47),
                SpriteHashtable.get(46), SpriteHashtable.get(46), SpriteHashtable.get(46),
                SpriteHashtable.get(46), SpriteHashtable.get(46), SpriteHashtable.get(46),
                SpriteHashtable.get(44), SpriteHashtable.get(47)};

        WOODDOORSPRITES = new Sprite[]{
                SpriteHashtable.get(45), SpriteHashtable.get(45),
                SpriteHashtable.get(45), SpriteHashtable.get(44), SpriteHashtable.get(45),
                SpriteHashtable.get(44), SpriteHashtable.get(44), SpriteHashtable.get(44),
                SpriteHashtable.get(45), SpriteHashtable.get(44), SpriteHashtable.get(44),
                SpriteHashtable.get(44), SpriteHashtable.get(44), SpriteHashtable.get(44), SpriteHashtable.get(45)};
        WOODWALLSPRITES = new Sprite[]{
                SpriteHashtable.get(37), SpriteHashtable.get(34),
                SpriteHashtable.get(42), SpriteHashtable.get(33), SpriteHashtable.get(40), SpriteHashtable.get(36),
                SpriteHashtable.get(41), SpriteHashtable.get(35), SpriteHashtable.get(32),
                SpriteHashtable.get(39), SpriteHashtable.get(43), SpriteHashtable.get(38),
                SpriteHashtable.get(31), SpriteHashtable.get(30), SpriteHashtable.get(29), SpriteHashtable.get(159), SpriteHashtable.get(150),
                SpriteHashtable.get(157), SpriteHashtable.get(156), SpriteHashtable.get(158), SpriteHashtable.get(153),
                SpriteHashtable.get(151), SpriteHashtable.get(154), SpriteHashtable.get(152), SpriteHashtable.get(155)
        };
        STONEWALLSPRITES = new Sprite[]{
                SpriteHashtable.get(22), SpriteHashtable.get(19),
                SpriteHashtable.get(27), SpriteHashtable.get(18), SpriteHashtable.get(25), SpriteHashtable.get(21),
                SpriteHashtable.get(26), SpriteHashtable.get(20), SpriteHashtable.get(17),
                SpriteHashtable.get(24), SpriteHashtable.get(28), SpriteHashtable.get(23),
                SpriteHashtable.get(16), SpriteHashtable.get(15), SpriteHashtable.get(14), SpriteHashtable.get(160), SpriteHashtable.get(141),
                SpriteHashtable.get(143), SpriteHashtable.get(142), SpriteHashtable.get(144), SpriteHashtable.get(145),
                SpriteHashtable.get(146), SpriteHashtable.get(148), SpriteHashtable.get(147), SpriteHashtable.get(149)
        };

    }
}
