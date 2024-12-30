package entity.dynamic.item;

public class ItemInfo<T extends Item> {
	private final Class<T> clazz;
	private final T instance;

	public ItemInfo(Class<T> clazz, T instance) {
		this.clazz = clazz;
		this.instance = instance;
	}

	public T createInstance() {
		return clazz.cast(instance.copy());
	}

	public T createInstance(float x, float y, int z) {
		return clazz.cast(instance.copy(x, y, z));
	}

	public int getId() {
		return instance.getId();
	}
}
