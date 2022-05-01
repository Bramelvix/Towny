package events;

public class PointerClickEvent implements Event {
	public final int button;
	public final int action;
	public final double x;
	public final double y;

	public PointerClickEvent(int button, int action, double x, double y) {
		this.button = button;
		this.action = action;
		this.x = x;
		this.y = y;
	}
}
