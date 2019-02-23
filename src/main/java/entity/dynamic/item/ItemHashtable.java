package entity.dynamic.item;


import entity.Entity;
import entity.dynamic.item.weapon.Weapon;
import entity.dynamic.item.weapon.WeaponMaterial;
import entity.dynamic.item.weapon.WeaponType;
import graphics.SpriteHashtable;

import java.util.Hashtable;

public abstract class ItemHashtable {
    private static final Hashtable<Integer, Item> table = new Hashtable<Integer, Item>();


    public static <T extends Item> void registerItem(T item) throws Exception {
        if (table.put(item.getId(), item) != null) {
            throw new Exception("Duplicate key while registering item: " + item.getId());
        }
    }

    public static Weapon getRandomWeapon() {
        return get(Entity.RANDOM.nextInt(41) + 20);
    }


    public static <T extends Item> T get(int key) {
        return (T) table.get(key).copy();
    }

    public static <T extends Item> T get(int key, int x, int y, int z) {
        return (T) table.get(key).copy(x, y, z);
    }

    public static void registerItems() throws Exception {
        registerItem(new Item("logs", SpriteHashtable.get(58), "Wooden logs", 1)); //logs
        registerItem(new Item("iron bar", SpriteHashtable.get(59), "a bar of solid iron", 2));//iron bar
        registerItem(new Item("gold bar", SpriteHashtable.get(60), "a bar of solid gold", 3));//gold bar
        registerItem(new Item("copper bar", SpriteHashtable.get(61), "a bar of solid copper", 4));//copper bar
        registerItem(new Item("iron ore", SpriteHashtable.get(62), "iron ore", 5));// iron ore
        registerItem(new Item("copper ore", SpriteHashtable.get(66), "copper ore", 6)); // copper ore
        registerItem(new Item("gold ore", SpriteHashtable.get(63), "gold ore", 7)); // gold ore
        registerItem(new Item("coal ore", SpriteHashtable.get(65), "coal ore", 8)); // coal ore
        registerItem(new Item("uncut crystal", SpriteHashtable.get(67), "an uncut crystal", 9)); // uncut crystal
        registerItem(new Item("stone", SpriteHashtable.get(64), "some stones", 10)); // stone
        registerItem(new Item("cut crystal", SpriteHashtable.get(68), "a cut crystal", 11)); // cut crystal

        registerItem(new Weapon("wooden axe", SpriteHashtable.get(91), "a wooden axe", WeaponType.AXE, WeaponMaterial.WOOD, 20));
        registerItem(new Weapon("wooden bow", SpriteHashtable.get(95), "a wooden bow", WeaponType.BOW, WeaponMaterial.WOOD, 21));
        registerItem(new Weapon("wooden buckler", SpriteHashtable.get(129), "a wooden buckler", WeaponType.BUCKLER, WeaponMaterial.WOOD, 22));
        registerItem(new Weapon("wooden dagger", SpriteHashtable.get(119), "a wooden dagger", WeaponType.DAGGER, WeaponMaterial.WOOD, 23));
        registerItem(new Weapon("wooden halbert", SpriteHashtable.get(94), "a wooden halbert", WeaponType.HALBERD, WeaponMaterial.WOOD, 24));
        registerItem(new Weapon("wooden heater", SpriteHashtable.get(138), "a wooden heater", WeaponType.HEATER, WeaponMaterial.WOOD, 25));
        registerItem(new Weapon("wooden scimitar", SpriteHashtable.get(124), "a wooden scimitar", WeaponType.SCIMITAR, WeaponMaterial.WOOD, 26));
        registerItem(new Weapon("wooden spear", SpriteHashtable.get(86), "a wooden spear", WeaponType.SPEAR, WeaponMaterial.WOOD, 27));
        registerItem(new Weapon("wooden sword", SpriteHashtable.get(114), "a wooden sword", WeaponType.HALBERD, WeaponMaterial.WOOD, 28));

        registerItem(new Weapon("copper axe", SpriteHashtable.get(87), "a copper axe", WeaponType.AXE, WeaponMaterial.COPPER, 29));
        registerItem(new Weapon("copper buckler", SpriteHashtable.get(134), "a copper buckler", WeaponType.BUCKLER, WeaponMaterial.COPPER, 30));
        registerItem(new Weapon("copper dagger", SpriteHashtable.get(120), "a copper dagger", WeaponType.DAGGER, WeaponMaterial.COPPER, 31));
        registerItem(new Weapon("copper halbert", SpriteHashtable.get(90), "a copper halbert", WeaponType.HALBERD, WeaponMaterial.COPPER, 32));
        registerItem(new Weapon("copper heater", SpriteHashtable.get(135), "a copper heater", WeaponType.HEATER, WeaponMaterial.COPPER, 33));
        registerItem(new Weapon("copper scimitar", SpriteHashtable.get(125), "a copper scimitar", WeaponType.SCIMITAR, WeaponMaterial.COPPER, 34));
        registerItem(new Weapon("copper spear", SpriteHashtable.get(139), "a copper spear", WeaponType.SPEAR, WeaponMaterial.COPPER, 35));
        registerItem(new Weapon("copper sword", SpriteHashtable.get(115), "a copper sword", WeaponType.SWORD, WeaponMaterial.COPPER, 36));

        registerItem(new Weapon("iron axe", SpriteHashtable.get(97), "an iron axe", WeaponType.AXE, WeaponMaterial.IRON, 37));
        registerItem(new Weapon("iron buckler", SpriteHashtable.get(130), "an iron buckler", WeaponType.BUCKLER, WeaponMaterial.IRON, 38));
        registerItem(new Weapon("iron dagger", SpriteHashtable.get(121), "an iron dagger", WeaponType.DAGGER, WeaponMaterial.IRON, 39));
        registerItem(new Weapon("iron halbert", SpriteHashtable.get(103), "an iron halbert", WeaponType.HALBERD, WeaponMaterial.IRON, 40));
        registerItem(new Weapon("iron heater", SpriteHashtable.get(131), "an iron heater", WeaponType.HEATER, WeaponMaterial.IRON, 41));
        registerItem(new Weapon("iron scimitar", SpriteHashtable.get(126), "an iron scimitar", WeaponType.SCIMITAR, WeaponMaterial.IRON, 42));
        registerItem(new Weapon("iron spear", SpriteHashtable.get(96), "an iron spear", WeaponType.SPEAR, WeaponMaterial.IRON, 43));
        registerItem(new Weapon("iron sword", SpriteHashtable.get(116), "an iron sword", WeaponType.SWORD, WeaponMaterial.IRON, 44));

        registerItem(new Weapon("gold axe", SpriteHashtable.get(105), "a golden axe", WeaponType.AXE, WeaponMaterial.GOLD, 45));
        registerItem(new Weapon("gold buckler", SpriteHashtable.get(132), "a golden buckler", WeaponType.BUCKLER, WeaponMaterial.GOLD, 46));
        registerItem(new Weapon("gold dagger", SpriteHashtable.get(122), "a golden dagger", WeaponType.DAGGER, WeaponMaterial.GOLD, 47));
        registerItem(new Weapon("gold halbert", SpriteHashtable.get(108), "a golden halbert", WeaponType.HALBERD, WeaponMaterial.GOLD, 48));
        registerItem(new Weapon("gold heater", SpriteHashtable.get(133), "a golden heater", WeaponType.HEATER, WeaponMaterial.GOLD, 49));
        registerItem(new Weapon("gold scimitar", SpriteHashtable.get(127), "an golden scimitar", WeaponType.SCIMITAR, WeaponMaterial.GOLD, 50));
        registerItem(new Weapon("gold spear", SpriteHashtable.get(104), "a golden spear", WeaponType.SPEAR, WeaponMaterial.GOLD, 51));
        registerItem(new Weapon("gold sword", SpriteHashtable.get(117), "a golden sword", WeaponType.SWORD, WeaponMaterial.GOLD, 52));

        registerItem(new Weapon("crystal axe", SpriteHashtable.get(110), "a crystal axe", WeaponType.AXE, WeaponMaterial.CRYSTAL, 53));
        registerItem(new Weapon("crystal buckler", SpriteHashtable.get(136), "a crystal buckler", WeaponType.BUCKLER, WeaponMaterial.CRYSTAL, 54));
        registerItem(new Weapon("crystal dagger", SpriteHashtable.get(123), "a crystal dagger", WeaponType.DAGGER, WeaponMaterial.CRYSTAL, 55));
        registerItem(new Weapon("crystal halbert", SpriteHashtable.get(113), "a crystal halbert", WeaponType.HALBERD, WeaponMaterial.CRYSTAL, 56));
        registerItem(new Weapon("crystal heater", SpriteHashtable.get(137), "a crystal heater", WeaponType.HEATER, WeaponMaterial.CRYSTAL, 57));
        registerItem(new Weapon("crystal scimitar", SpriteHashtable.get(128), "an crystal scimitar", WeaponType.SCIMITAR, WeaponMaterial.CRYSTAL, 58));
        registerItem(new Weapon("crystal spear", SpriteHashtable.get(109), "a crystal spear", WeaponType.SPEAR, WeaponMaterial.CRYSTAL, 59));
        registerItem(new Weapon("crystal sword", SpriteHashtable.get(118), "a crystal sword", WeaponType.SWORD, WeaponMaterial.CRYSTAL, 60));

        registerItem(new Clothing("Orange shirt",SpriteHashtable.get(70),"a brown shirt",ClothingType.SHIRT,0.1f,61));
        registerItem(new Clothing("Orange shirt",SpriteHashtable.get(71),"a brown shirt",ClothingType.SHIRT,0.1f,62));
        registerItem(new Clothing("Orange shirt",SpriteHashtable.get(72),"a brown shirt",ClothingType.SHIRT,0.1f,63));
        registerItem(new Clothing("Orange shirt",SpriteHashtable.get(73),"a brown shirt",ClothingType.SHIRT,0.1f,64));
        registerItem(new Clothing("Green shirt",SpriteHashtable.get(74),"a green shirt",ClothingType.SHIRT,0.1f,65));
        registerItem(new Clothing("Green shirt",SpriteHashtable.get(75),"a green shirt",ClothingType.SHIRT,0.1f,66));
        registerItem(new Clothing("Green shirt",SpriteHashtable.get(76),"a green shirt",ClothingType.SHIRT,0.1f,67));
        registerItem(new Clothing("Green shirt",SpriteHashtable.get(77),"a green shirt",ClothingType.SHIRT,0.1f,68));
        registerItem(new Clothing("Grey shirt",SpriteHashtable.get(78),"a grey shirt",ClothingType.SHIRT,0.1f,69));
        registerItem(new Clothing("Grey shirt",SpriteHashtable.get(79),"a grey shirt",ClothingType.SHIRT,0.1f,70));
        registerItem(new Clothing("Grey shirt",SpriteHashtable.get(80),"a grey shirt",ClothingType.SHIRT,0.1f,71));
        registerItem(new Clothing("Grey shirt",SpriteHashtable.get(81),"a grey shirt",ClothingType.SHIRT,0.1f,72));
        registerItem(new Clothing("Black trousers",SpriteHashtable.get(69),"a pair of black trousers",ClothingType.TROUSERS,0.1f,73));
        registerItem(new Clothing("Brown trousers",SpriteHashtable.get(88),"a pair of brown trousers",ClothingType.TROUSERS,0.1f,74));
        registerItem(new Clothing("Orange trousers",SpriteHashtable.get(89),"a pair of orange trousers",ClothingType.TROUSERS,0.1f,75));

    }
}


