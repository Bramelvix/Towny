package entity.dynamic.mob;

import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.dynamic.item.ItemInfo;
import entity.nondynamic.resources.Tree;
import entity.pathfinding.PathFinder;
import map.Level;
import map.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class VillagerTests {
	private Level[] level;
	private Villager villager;

	@BeforeEach
	void setUp() {
		level = new Level[]{new Level(10, 10, 1, false)};
		PathFinder.init(10, 10);
	}

	@ParameterizedTest
	@CsvSource({
			"48, 96, 144, 192, 240",
			"240, 192, 144, 48, 96"
	})
	void onSpotWhenCreated(float x, float y) {
		villager = new Villager(x, y, 0, level);
		assertEquals(x, villager.getX());
		assertEquals(y, villager.getY());
	}

	@ParameterizedTest
	@CsvSource({
			"48, 96, 144, 192, 240",
			"240, 192, 144, 48, 96"
	})
	void onTileWhenCreated(float x, float y) {
		villager = new Villager(x, y, 0, level);
		assertEquals((int) (x / Tile.SIZE), villager.getTileX());
		assertEquals((int) (y / Tile.SIZE), villager.getTileY());
	}

	@ParameterizedTest
	@ValueSource(floats = {48, 96, 144})
	void nextToSpotWhenOccupied(float num) {
		level[0].addEntity(new Tree(num, num, 0, level[0]), true);
		villager = new Villager(num, num, 0, level);
		assertEquals(num + 48, villager.getX());
		assertEquals(num + 48, villager.getY());
	}

	@Test
	void shouldPickUpItem() {
		Item item = ItemHashtable.getTestItemInfo().createInstance();
		item.setLocation(48, 48, 0);
		level[0].addItem(item);
		villager = new Villager(48, 48, 0, level);
		villager.pickUp(item);
		villager.update();
		assertTrue(villager.isHolding(item));
	}

	@Test
	void shouldDropItem() {
		Item item = ItemHashtable.getTestItemInfo().createInstance();
		item.setLocation(48, 48, 0);
		level[0].addItem(item);
		villager = new Villager(48, 48, 0, level);
		villager.pickUp(item);
		villager.update();
		assertTrue(villager.isHolding(item));
		villager.drop();
		villager.update();
		assertFalse(villager.isHolding(item));
	}

	@Test
	void shouldFindNearbyItem() {
		villager = new Villager(48, 48, 0, level);
		ItemInfo<Item> itemInfo = ItemHashtable.getTestItemInfo();
		Item item = itemInfo.createInstance();
		item.setLocation(96, 96, 0);
		level[0].addItem(item);
		assertTrue(villager.getNearestItemOfType(itemInfo).isPresent());
	}


}
