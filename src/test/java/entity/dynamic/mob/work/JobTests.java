package entity.dynamic.mob.work;

import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.dynamic.mob.Villager;
import entity.pathfinding.PathFinder;
import map.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
	void shouldPickupItem() {
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
}
