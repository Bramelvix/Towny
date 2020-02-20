package events;


@FunctionalInterface
public interface EventListener<T extends Event> {

	public void handle (T event) throws Exception;

}
