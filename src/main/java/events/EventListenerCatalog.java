package events;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;


public class EventListenerCatalog {

	// TODO: listeners containers are not thread safe.
	protected Map<EventType<?>, Map<EventListener<? extends Event>, Predicate<? extends Event>>> listeners = new ConcurrentHashMap<>();

	public <T extends Event> Subscription register (EventType<T> type, EventListener<T> listener) {
		return register(type, listener, event -> true);
	}

	public <T extends Event> Subscription register (EventType<T> type, EventListener<T> listener, Predicate<T> condition) {
		Map<EventListener<? extends Event>, Predicate<? extends Event>> ls = listeners.computeIfAbsent (type, t -> new ConcurrentHashMap<>());
		ls.put (listener, condition);
		return () -> CompletableFuture.runAsync(() -> ls.remove(listener));
	}


	public <T extends Event> void fire (EventType<T> type, T event) {
		Map<EventListener<T>, Predicate<T>> ls = (Map) listeners.computeIfAbsent (type, t -> new ConcurrentHashMap<>());
		ls.forEach((k, v) -> {
			if (v.test(event)) {
				try { k.handle(event); }
				catch ( RuntimeException e) { throw e; }
				catch ( Exception e ) { throw new RuntimeException (e); }
			}
		});
	}

}
