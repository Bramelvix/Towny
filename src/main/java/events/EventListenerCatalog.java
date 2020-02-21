package events;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;


public class EventListenerCatalog {

	// TODO: listeners containers are not thread safe.
	protected Map<EventType<?>, Set<EventListener<? extends Event>>> listeners = new ConcurrentHashMap<>();

	public <T extends Event> Subscription register (EventType<T> type, EventListener<T> listener) {
		Set<EventListener<?>> ls = listeners.computeIfAbsent (type, t -> ConcurrentHashMap.newKeySet());
		ls.add (listener);
		return () -> CompletableFuture.runAsync(() -> ls.remove(listener));
	}

	public <T extends Event> void fire (EventType<T> type, T event) {
		Set<EventListener<Event>> ls = (Set) listeners.computeIfAbsent (type, t -> ConcurrentHashMap.newKeySet());
		ls.forEach(l -> {
			try { l.handle (event); }
			catch ( RuntimeException e) { throw e; }
			catch ( Exception e ) { throw new RuntimeException (e); }
		});
	}

}
