package entity.dynamic.item;


import entity.Entity;
import entity.dynamic.item.weapon.Weapon;
import entity.dynamic.item.weapon.WeaponMaterial;
import entity.dynamic.item.weapon.WeaponType;
import entity.nondynamic.resources.OreType;
import graphics.SpriteHashtable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public abstract class ItemHashtable {
	private ItemHashtable() {

	}

	private static final HashMap<Integer, ItemInfo<?>> table = new HashMap<>();
	private static final Logger logger = LoggerFactory.getLogger(ItemHashtable.class);

	public static <T extends Item> void registerItem(ItemInfo<T> itemInfo) throws IllegalArgumentException {
		if (table.put(itemInfo.getId(), itemInfo) != null) {
			throw new IllegalArgumentException("Duplicate key while registering item: " + itemInfo.getId());
		}
	}

	public static Weapon getRandomWeapon() {
		return (Weapon) get(Entity.RANDOM.nextInt(41) + 20).createInstance();
	}

	public static ItemInfo<?> get(int key) {
		if (!table.containsKey(key)) {
			logger.warn("NullPointer imminent! Trying to get Item with id: {}, but no item with this key is present!", key);
			return getTestItemInfo();
		}
		return table.get(key);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Item> ItemInfo<T> get(int key, Class<T> clazz) {
		ItemInfo<?> info = get(key);
		if (clazz.isInstance(info.createInstance())) {
			return (ItemInfo<T>) info;
		}

		throw new IllegalArgumentException("Item with key " + key + " is not an instance of " + clazz);
	}

	//testing purposes only
	public static ItemInfo<Item> getTestItemInfo() {
		return new ItemInfo<>(Item.class, new Item("null item", null, "If you find this item in the game, please tell a developer.", 0));
	}

	public static void registerItems() throws IllegalArgumentException {
		registerItem(new ItemInfo<>(Item.class, new Item("logs", SpriteHashtable.get(58), "Wooden logs", 1))); //logs
		registerItem(new ItemInfo<>(Item.class, new Item("iron bar", SpriteHashtable.get(59), "a bar of solid iron", 2)));//iron bar
		registerItem(new ItemInfo<>(Item.class, new Item("gold bar", SpriteHashtable.get(60), "a bar of solid gold", 3)));//gold bar
		registerItem(new ItemInfo<>(Item.class, new Item("copper bar", SpriteHashtable.get(61), "a bar of solid copper", 4)));//copper bar
		registerItem(new ItemInfo<>(Item.class, new Item("iron ore", SpriteHashtable.get(62), "iron ore", OreType.IRON.getItemId())));// iron ore
		registerItem(new ItemInfo<>(Item.class, new Item("copper ore", SpriteHashtable.get(66), "copper ore", OreType.COPPER.getItemId()))); // copper ore
		registerItem(new ItemInfo<>(Item.class, new Item("gold ore", SpriteHashtable.get(63), "gold ore", OreType.GOLD.getItemId()))); // gold ore
		registerItem(new ItemInfo<>(Item.class, new Item("coal ore", SpriteHashtable.get(65), "coal ore", OreType.COAL.getItemId()))); // coal ore
		registerItem(new ItemInfo<>(Item.class, new Item("uncut crystal", SpriteHashtable.get(67), "an uncut crystal", OreType.CRYSTAL.getItemId()))); // uncut crystal
		registerItem(new ItemInfo<>(Item.class, new Item("stone", SpriteHashtable.get(64), "some stones", OreType.STONE.getItemId()))); // stone
		registerItem(new ItemInfo<>(Item.class, new Item("cut crystal", SpriteHashtable.get(68), "a cut crystal", 11))); // cut crystal

		registerItem(new ItemInfo<>(Weapon.class, new Weapon("wooden axe", SpriteHashtable.get(91), "a wooden axe", WeaponType.AXE, WeaponMaterial.WOOD, 20)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("wooden bow", SpriteHashtable.get(95), "a wooden bow", WeaponType.BOW, WeaponMaterial.WOOD, 21)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("wooden buckler", SpriteHashtable.get(129), "a wooden buckler", WeaponType.BUCKLER, WeaponMaterial.WOOD, 22)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("wooden dagger", SpriteHashtable.get(119), "a wooden dagger", WeaponType.DAGGER, WeaponMaterial.WOOD, 23)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("wooden halbert", SpriteHashtable.get(94), "a wooden halbert", WeaponType.HALBERD, WeaponMaterial.WOOD, 24)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("wooden heater", SpriteHashtable.get(138), "a wooden heater", WeaponType.HEATER, WeaponMaterial.WOOD, 25)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("wooden scimitar", SpriteHashtable.get(124), "a wooden scimitar", WeaponType.SCIMITAR, WeaponMaterial.WOOD, 26)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("wooden spear", SpriteHashtable.get(86), "a wooden spear", WeaponType.SPEAR, WeaponMaterial.WOOD, 27)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("wooden sword", SpriteHashtable.get(114), "a wooden sword", WeaponType.HALBERD, WeaponMaterial.WOOD, 28)));

		registerItem(new ItemInfo<>(Weapon.class, new Weapon("copper axe", SpriteHashtable.get(87), "a copper axe", WeaponType.AXE, WeaponMaterial.COPPER, 29)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("copper buckler", SpriteHashtable.get(134), "a copper buckler", WeaponType.BUCKLER, WeaponMaterial.COPPER, 30)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("copper dagger", SpriteHashtable.get(120), "a copper dagger", WeaponType.DAGGER, WeaponMaterial.COPPER, 31)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("copper halbert", SpriteHashtable.get(90), "a copper halbert", WeaponType.HALBERD, WeaponMaterial.COPPER, 32)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("copper heater", SpriteHashtable.get(135), "a copper heater", WeaponType.HEATER, WeaponMaterial.COPPER, 33)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("copper scimitar", SpriteHashtable.get(125), "a copper scimitar", WeaponType.SCIMITAR, WeaponMaterial.COPPER, 34)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("copper spear", SpriteHashtable.get(139), "a copper spear", WeaponType.SPEAR, WeaponMaterial.COPPER, 35)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("copper sword", SpriteHashtable.get(115), "a copper sword", WeaponType.SWORD, WeaponMaterial.COPPER, 36)));

		registerItem(new ItemInfo<>(Weapon.class, new Weapon("iron axe", SpriteHashtable.get(97), "an iron axe", WeaponType.AXE, WeaponMaterial.IRON, 37)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("iron buckler", SpriteHashtable.get(130), "an iron buckler", WeaponType.BUCKLER, WeaponMaterial.IRON, 38)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("iron dagger", SpriteHashtable.get(121), "an iron dagger", WeaponType.DAGGER, WeaponMaterial.IRON, 39)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("iron halbert", SpriteHashtable.get(103), "an iron halbert", WeaponType.HALBERD, WeaponMaterial.IRON, 40)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("iron heater", SpriteHashtable.get(131), "an iron heater", WeaponType.HEATER, WeaponMaterial.IRON, 41)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("iron scimitar", SpriteHashtable.get(126), "an iron scimitar", WeaponType.SCIMITAR, WeaponMaterial.IRON, 42)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("iron spear", SpriteHashtable.get(96), "an iron spear", WeaponType.SPEAR, WeaponMaterial.IRON, 43)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("iron sword", SpriteHashtable.get(116), "an iron sword", WeaponType.SWORD, WeaponMaterial.IRON, 44)));

		registerItem(new ItemInfo<>(Weapon.class, new Weapon("gold axe", SpriteHashtable.get(105), "a golden axe", WeaponType.AXE, WeaponMaterial.GOLD, 45)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("gold buckler", SpriteHashtable.get(132), "a golden buckler", WeaponType.BUCKLER, WeaponMaterial.GOLD, 46)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("gold dagger", SpriteHashtable.get(122), "a golden dagger", WeaponType.DAGGER, WeaponMaterial.GOLD, 47)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("gold halbert", SpriteHashtable.get(108), "a golden halbert", WeaponType.HALBERD, WeaponMaterial.GOLD, 48)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("gold heater", SpriteHashtable.get(133), "a golden heater", WeaponType.HEATER, WeaponMaterial.GOLD, 49)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("gold scimitar", SpriteHashtable.get(127), "an golden scimitar", WeaponType.SCIMITAR, WeaponMaterial.GOLD, 50)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("gold spear", SpriteHashtable.get(104), "a golden spear", WeaponType.SPEAR, WeaponMaterial.GOLD, 51)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("gold sword", SpriteHashtable.get(117), "a golden sword", WeaponType.SWORD, WeaponMaterial.GOLD, 52)));

		registerItem(new ItemInfo<>(Weapon.class, new Weapon("crystal axe", SpriteHashtable.get(110), "a crystal axe", WeaponType.AXE, WeaponMaterial.CRYSTAL, 53)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("crystal buckler", SpriteHashtable.get(136), "a crystal buckler", WeaponType.BUCKLER, WeaponMaterial.CRYSTAL, 54)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("crystal dagger", SpriteHashtable.get(123), "a crystal dagger", WeaponType.DAGGER, WeaponMaterial.CRYSTAL, 55)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("crystal halbert", SpriteHashtable.get(113), "a crystal halbert", WeaponType.HALBERD, WeaponMaterial.CRYSTAL, 56)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("crystal heater", SpriteHashtable.get(137), "a crystal heater", WeaponType.HEATER, WeaponMaterial.CRYSTAL, 57)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("crystal scimitar", SpriteHashtable.get(128), "an crystal scimitar", WeaponType.SCIMITAR, WeaponMaterial.CRYSTAL, 58)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("crystal spear", SpriteHashtable.get(109), "a crystal spear", WeaponType.SPEAR, WeaponMaterial.CRYSTAL, 59)));
		registerItem(new ItemInfo<>(Weapon.class, new Weapon("crystal sword", SpriteHashtable.get(118), "a crystal sword", WeaponType.SWORD, WeaponMaterial.CRYSTAL, 60)));

		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Orange shirt", SpriteHashtable.get(70), "an orange shirt", ClothingType.SHIRT, 0.1f, 61)));
		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Orange shirt", SpriteHashtable.get(71), "an orange shirt", ClothingType.SHIRT, 0.1f, 62)));
		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Orange shirt", SpriteHashtable.get(72), "an orange shirt", ClothingType.SHIRT, 0.1f, 63)));
		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Orange shirt", SpriteHashtable.get(73), "an orange shirt", ClothingType.SHIRT, 0.1f, 64)));
		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Green shirt", SpriteHashtable.get(74), "a green shirt", ClothingType.SHIRT, 0.1f, 65)));
		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Green shirt", SpriteHashtable.get(75), "a green shirt", ClothingType.SHIRT, 0.1f, 66)));
		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Green shirt", SpriteHashtable.get(76), "a green shirt", ClothingType.SHIRT, 0.1f, 67)));
		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Green shirt", SpriteHashtable.get(77), "a green shirt", ClothingType.SHIRT, 0.1f, 68)));
		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Grey shirt", SpriteHashtable.get(78), "a grey shirt", ClothingType.SHIRT, 0.1f, 69)));
		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Grey shirt", SpriteHashtable.get(79), "a grey shirt", ClothingType.SHIRT, 0.1f, 70)));
		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Grey shirt", SpriteHashtable.get(80), "a grey shirt", ClothingType.SHIRT, 0.1f, 71)));
		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Grey shirt", SpriteHashtable.get(81), "a grey shirt", ClothingType.SHIRT, 0.1f, 72)));
		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Black trousers", SpriteHashtable.get(69), "a pair of black trousers", ClothingType.TROUSERS, 0.1f, 73)));
		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Brown trousers", SpriteHashtable.get(88), "a pair of brown trousers", ClothingType.TROUSERS, 0.1f, 74)));
		registerItem(new ItemInfo<>(Clothing.class, new Clothing("Orange trousers", SpriteHashtable.get(89), "a pair of orange trousers", ClothingType.TROUSERS, 0.1f, 75)));
	}

}
