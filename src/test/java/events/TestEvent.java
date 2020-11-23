package events;

public class TestEvent implements Event {
	final String stringData;
	final int intData;

	TestEvent(String stringData, int intData) {
		this.stringData = stringData;
		this.intData = intData;
	}
}
