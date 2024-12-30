package entity.dynamic.mob.work;

import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.dynamic.item.ItemInfo;
import entity.dynamic.mob.Villager;
import entity.nondynamic.building.container.Chest;
import entity.nondynamic.building.container.Workstation;
import entity.nondynamic.building.farming.TilledSoil;
import entity.nondynamic.resources.Ore;
import entity.nondynamic.resources.OreType;
import entity.nondynamic.resources.Tree;
import entity.pathfinding.PathFinder;
import map.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JobTests {

	private Level[] level;
	private Villager villager;

	@BeforeEach
	void setUp() {
		level = new Level[]{new Level(10, 10, 1, false)};
		villager = new Villager(48, 48, 0, level);
		PathFinder.init(10, 10);
	}

	@Test
	void shouldPickupItemJob() {
		ItemInfo<Item> itemInfo = ItemHashtable.getTestItemInfo();
		Item item = itemInfo.createInstance();
		item.setLocation(96, 96, 0);
		level[0].addItem(item);
		assertTrue(villager.getNearestItemOfType(itemInfo).isPresent());
		villager.addJob(new PickUpItemJob(villager, item));
		for (int i = 0; i < 50; i++) {
			villager.update();
		}
		assertTrue(villager.isHolding(item));
	}

	@Test
	void shouldDropItemJob() {
		Item item = ItemHashtable.getTestItemInfo().createInstance();
		villager.setHolding(item);
		assertTrue(villager.isHolding(item));
		villager.addJob(new DropItemJob(villager, 2, 2, 0));
		for (int i = 0; i < 50; i++) {
			villager.update();
		}
		assertFalse(villager.isHolding(item));
		assertEquals(96, item.getX());
		assertEquals(96, item.getY());
	}

	@Test
	void shouldChopTree() {
		level[0].addEntity(new Tree(192, 48, 0, level[0]), true);
		Optional<Tree> tree = level[0].getEntityOn(4, 1, Tree.class);
		assertTrue(tree.isPresent());
		villager.addJob(tree.get());
		for (int i = 0; i < 150; i++) {
			villager.update();
		}
		assertTrue(level[0].getEntityOn(4, 1, Tree.class).isEmpty());
		assertTrue(level[0].getItemOn(4, 1).isPresent());
	}

	@Test
	void shouldMineOre() {
		level[0].addEntity(new Ore(192, 48, 0, level[0], OreType.GOLD), true);
		Optional<Ore> ore = level[0].getEntityOn(4, 1, Ore.class);
		assertTrue(ore.isPresent());
		villager.addJob(ore.get());
		for (int i = 0; i < 150; i++) {
			villager.update();
		}
		assertTrue(level[0].getEntityOn(4, 1, Ore.class).isEmpty());
		assertTrue(level[0].getItemOn(4, 1).isPresent());
	}

	@Test
	void shouldBuildObject() {
		ItemInfo<Item> itemInfo = ItemHashtable.getTestItemInfo();
		Item item = itemInfo.createInstance();
		item.setLocation(96, 96, 0);
		level[0].addItem(item);
		villager.addBuildJob(4, 2, 0, new Chest(), itemInfo);
		for (int i = 0; i < 75; i++) {
			villager.update();
		}
		assertTrue(level[0].getEntityOn(4, 2, Chest.class).isPresent());
	}

	@Test
	void shouldTillSoil() {
		villager.addBuildJob(4, 2, 0, new TilledSoil(), null);
		for (int i = 0; i < 75; i++) {
			villager.update();
		}
		assertTrue(level[0].selectTilledSoil(4, 2).isPresent());
	}

	@Test
	void shouldSowSeeds() {
		TilledSoil soil = new TilledSoil();
		soil.initialise(4, 2, level, 0);
		villager.addJob(soil);
		for (int i = 0; i < 150; i++) {
			villager.update();
		}
		assertTrue(soil.isPlanted());
	}

	@Test
	void shouldHarvest() {
		TilledSoil soil = new TilledSoil();
		soil.initialise(4, 2, level, 0);
		for (int i = 0; i < 100; i++) {
			soil.sow();
		}
		for (int i = 0; i < 150; i++) {
			villager.update();
		}
		assertFalse(soil.isPlanted());
	}

	@Test
	void shouldCraftItem() {
		Workstation anvil = Workstation.anvil();
		anvil.setLocation(48, 96, 0);
		level[0].addEntity(anvil, true);
		Item[] resources = new Item[]{ItemHashtable.getTestItemInfo().createInstance()};
		Item result = ItemHashtable.getTestItemInfo().createInstance();
		String name = result.getName();
		villager.addJob(new CraftJob(villager, resources, result, anvil));
		for (int i = 0; i < 150; i++) {
			villager.update();
		}
		assertEquals(0, villager.getJobSize());
		assertEquals(name, level[0].getItems().getFirst().getName());
	}
}
