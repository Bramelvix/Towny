package map;

import entity.non_dynamic.building.Stairs;
import entity.non_dynamic.building.container.Chest;
import entity.pathfinding.PathFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.vectors.Vec2i;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LevelTests {

	private Level level;

	@BeforeEach
	void setUp() {
		level = new Level(10, 10, 1, false);
		PathFinder.init(10, 10);
	}

	@Test
	void shouldFindNearestSpotThatHasX() {
		Chest chest1 = new Chest();
		chest1.setLocation(48, 48, 0);
		level.addEntity(chest1, false);

		Chest chest2 = new Chest();
		chest2.setLocation(192, 192, 0);
		level.addEntity(chest2, false);

		Vec2i found = level.getNearestSpotThatHasX(0, 0, Chest.class);
		assertEquals(1, found.x);
		assertEquals(1, found.y);

	}

	@Test
	void shouldFindStairs() {
		Stairs stairs = new Stairs(true);
		stairs.setLocation(48, 48, 0);
		level.addStairs(stairs);
		Optional<Stairs> found = level.getNearestStairs(5, 5, true);
		assertTrue(found.isPresent());

	}
}
