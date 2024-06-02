package events;

public record PointerClickEvent(int button, int action, double x, double y) implements Event {
}
