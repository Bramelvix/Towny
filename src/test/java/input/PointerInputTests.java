package input;

import entity.Entity;
import events.EventListenerCatalog;
import events.PointerMoveEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointerInputTests {
	private EventListenerCatalog catalog;
	private PointerInput input;
	private long x;
	private long y;

	@BeforeEach
	void setUp() {
		catalog = new EventListenerCatalog();
		PointerInput.configure(null);
		input = PointerInput.getInstance();
		x = Entity.RANDOM.nextInt(); // Yes, this is Int on purpose
		y = Entity.RANDOM.nextInt();
	}

	@Test
	void moveEventShouldFire() {
		catalog.register(PointerInput.EType.MOVE, this::moveMouse);
		input.positionCallback().invoke(0, x, y);
		assertEquals(x, input.getTrueX());
		assertEquals(y, input.getTrueY());
	}

	private void moveMouse(PointerMoveEvent e) {
		assertEquals(x, e.x);
		assertEquals(y, e.y);
	}

}
