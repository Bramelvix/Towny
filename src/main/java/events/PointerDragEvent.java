package events;

public class PointerDragEvent extends PointerMoveEvent {
	public final int button;

	public PointerDragEvent(double x, double y, int button) {
		super(x, y);
		this.button = button;
	}
}
