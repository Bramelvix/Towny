package entity.dynamic.mob;

import static org.junit.jupiter.api.Assertions.assertEquals;

import map.Level;
import map.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class VillagerTests {
	private Level[] level;
	private Villager villager;

	@BeforeEach
	void setUp() {
		level = new Level[] { new Level(10, 10, 1, false) };
	}

	@ParameterizedTest
	@CsvSource({
		"48, 96, 144, 192, 240",
		"240, 192, 144, 48, 96"
	})
	void villagerOnSpotWhenCreated(float x, float y) {
		villager = new Villager(x, y, 0, level);
		assertEquals(villager.getX(), x);
		assertEquals(villager.getY(), y);
	}

	@ParameterizedTest
	@CsvSource({
		"48, 96, 144, 192, 240",
		"240, 192, 144, 48, 96"
	})
	void villagerOnTileWhenCreated(float x, float y) {
		villager = new Villager(x, y, 0, level);
		assertEquals(villager.getTileX(), (int) (x/ Tile.SIZE));
		assertEquals(villager.getTileY(), (int) (y/ Tile.SIZE));
	}
}
