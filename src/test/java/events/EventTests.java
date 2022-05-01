package events;


import entity.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTests {

	private EventListenerCatalog catalog;
	private int testNumber;
	private String testString;

	private static class TestEventType<T extends Event> implements EventType<T> {
		private static final TestEventType<TestEvent> testEventType = new TestEventType<>();
	}

	@BeforeEach
	void setUp() {
		catalog = new EventListenerCatalog();
		testNumber = Entity.RANDOM.nextInt();
		testString = UUID.randomUUID().toString();
	}

	@Test
	void eventShouldFire() {
		catalog.register(TestEventType.testEventType, this::eventFire);
		catalog.fire(TestEventType.testEventType, new TestEvent(testString, testNumber));

	}

	void eventFire(TestEvent event) {
		assertEquals(testNumber, event.intData());
		assertEquals(testString, event.stringData());
	}
}
