package events;

import java.util.function.Predicate;

@FunctionalInterface
public interface EventListener<T extends Event> {
	void handle (T event) throws Exception;

	static <T extends Event> EventListener<T> onlyWhen(Predicate<T> condition, EventListener<T> listener) {
		return e -> {
			if (condition.test(e)) {
				listener.handle(e);
			}
		};
	}
}
