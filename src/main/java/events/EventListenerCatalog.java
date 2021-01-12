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
		getCurrentListeners(type).forEach(l -> {
			try { l.handle(event); }
			catch ( RuntimeException e) { throw e; }
			catch ( Exception e ) { throw new RuntimeException (e); }
		});
	}

	/**
	 * Get a copy of the {@link Set} of currently registered {@link EventListener} to an {@link EventType}
	 *
	 * Event listeners could add new listeners when an event is fired. If a copy of the listener set is not used, a new
	 * listener could be added to the set and fired instantly. Adding the new listeners is subject to race conditions.
	 * Sometimes the event will fire on the new listener, sometimes it will not. Creating a copy of the Set prevents all
	 * this.
	 *
	 * @param type The {@link EventType}
	 * @param <T> The {@link Event} class
	 * @return The {@link HashSet} of listeners.
	 */
	@SuppressWarnings({"unchecked"})
	private <T extends Event> Set<EventListener<Event>> getCurrentListeners(EventType<T> type) {
		return new HashSet<EventListener<Event>>((Set) listeners.computeIfAbsent (type, t -> ConcurrentHashMap.newKeySet()));
	}

}
