package events;

public class PointerMoveEvent implements Event {
	public final double x;
	public final double y;

	public PointerMoveEvent (double x, double y) {
		this.x = x;
		this.y = y;
	}
}
