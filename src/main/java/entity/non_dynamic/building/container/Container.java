package entity.non_dynamic.building.container;

import entity.dynamic.item.Item;
import entity.non_dynamic.building.BuildAbleObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Container extends BuildAbleObject {

	private final ArrayList<Item> items;
	private final int containerSize;

	protected Container(int containerSize) {
		items = new ArrayList<>();
		this.containerSize = containerSize;
	}

	public <T extends Item> void addItemTo(T item) {
		if (!isFull()) {
			item.setLocation(location.x, location.y, z);
			items.add(item);
		}
	}

	public <T extends Item> Optional<Item> takeItem(T e) {
		Optional<Item> result = items.stream().filter(item -> item.isSameType(e)).findAny();
		result.ifPresent(items::remove);
		return result;
	}

	public List<Item> getItemList() {
		return items;
	}

	private boolean isFull() {
		return items.size() >= containerSize;
	}

}
