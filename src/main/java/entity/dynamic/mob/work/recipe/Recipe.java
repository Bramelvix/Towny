package entity.dynamic.mob.work.recipe;

import entity.dynamic.item.ItemInfo;

import java.util.Collections;
import java.util.List;

public abstract class Recipe {

	private final List<ItemInfo<?>> resources;

	Recipe(List<ItemInfo<?>> resources) {
		this.resources = resources;
	}

	Recipe() {
		this(Collections.emptyList());
	}

	public abstract String getRecipeName();

	public List<ItemInfo<?>> getResources() {
		return resources;
	}

}
