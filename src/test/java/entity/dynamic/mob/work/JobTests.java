package entity.dynamic.mob.work;

import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.dynamic.mob.Villager;
import entity.nonDynamic.building.container.Chest;
import entity.nonDynamic.resources.Ore;
import entity.nonDynamic.resources.OreType;
import entity.nonDynamic.resources.Tree;
import entity.pathfinding.PathFinder;
import map.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JobTests {

	private Level[] level;
	private Villager villager;

	@BeforeEach
	void setUp() {
		level = new Level[] { new Level(10, 10, 1, false) };
		villager = new Villager(48, 48, 0, level);
		PathFinder.init(10, 10);
	}

	@Test
	void shouldPickupItemJob() {
		Item item = ItemHashtable.getTestItem();
		item.setLocation(96, 96, 0);
		level[0].addItem(item);
		assertTrue(villager.getNearestItemOfType(item).isPresent());
		villager.addJob(new MoveItemJob(item, villager));
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
		villager.addJob(new MoveItemJob(2, 2, 0, villager));
		for (int i = 0; i < 50; i++) {
			villager.update();
		}
		assertFalse(villager.isHolding(item));
		assertEquals(96, item.getX());
		assertEquals(96, item.getY());
	}

	@Test
	void shouldChopTree() {
		level[0].addEntity( new Tree(192, 48, 0, level[0]), true);
		Optional<Tree> tree = level[0].getEntityOn(4, 1,  Tree.class);
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
		level[0].addEntity( new Ore(192, 48, 0, level[0], OreType.GOLD), true);
		Optional<Ore> ore = level[0].getEntityOn(4, 1,  Ore.class);
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
}
