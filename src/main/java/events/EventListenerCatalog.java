package events;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventListenerCatalog {

	// TODO: listeners containers are not thread safe.
	protected Map<EventType<?>, List<EventListener<? extends Event>>> listeners = new HashMap<> ();

	public <T extends Event> Subscription register (EventType<T> type, EventListener<T> listener) {
		List<EventListener<?>> ls = listeners.computeIfAbsent (type, t -> new ArrayList<> ());
		ls.add (listener);
		return () -> ls.remove (listener);
	}

	public <T extends Event> void fire (EventType<T> type, T event) {
		List<EventListener<Event>> ls = (List) listeners.computeIfAbsent (type, t -> new ArrayList<> ());
		ls.forEach (l -> {
			try { l.handle (event); }
			catch ( RuntimeException e) { throw e; }
			catch ( Exception e ) { throw new RuntimeException (e); }
		});
	}

}
