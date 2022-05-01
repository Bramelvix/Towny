package entity.dynamic.mob.work;

import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.dynamic.mob.Villager;
import entity.non_dynamic.building.container.Chest;
import entity.non_dynamic.building.container.Workstation;
import entity.non_dynamic.building.farming.TilledSoil;
import entity.non_dynamic.resources.Ore;
import entity.non_dynamic.resources.OreType;
import entity.non_dynamic.resources.Tree;
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
		Item item = ItemHashtable.getTestItem();
		item.setLocation(96, 96, 0);
		level[0].addItem(item);
		assertTrue(villager.getNearestItemOfType(item).isPresent());
		villager.addJob(new MoveItemJob(villager, item));
		for (int i = 0; i < 50; i++) {
			villager.update();
		}
		assertTrue(villager.isHolding(item));
	}

	@Test
	void shouldDropItemJob() {
		Item item = ItemHashtable.getTestItem();
		villager.setHolding(item);
		assertTrue(villager.isHolding(item));
		villager.addJob(new MoveItemJob(villager, 2, 2, 0));
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
		Item item = ItemHashtable.getTestItem();
		item.setLocation(96, 96, 0);
		level[0].addItem(item);
		villager.addBuildJob(4, 2, 0, new Chest(), item);
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
		Item[] resources = new Item[]{ItemHashtable.getTestItem()};
		Item result = ItemHashtable.getTestItem();
		result.setName("This item was crafted");
		villager.addJob(new CraftJob(villager, resources, result, anvil));
		for (int i = 0; i < 150; i++) {
			villager.update();
		}
		assertEquals(0, villager.getJobSize());
		assertEquals("This item was crafted", level[0].getItems().get(0).getName());
	}
}
