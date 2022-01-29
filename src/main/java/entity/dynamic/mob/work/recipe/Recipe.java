package entity.dynamic.mob.work.recipe;

import entity.Entity;
import entity.dynamic.item.Item;

public class Recipe<T extends Entity> {

	private final Item[] resources;
	final T product;

	Recipe(T product, Item[] res) {
		this.product = product;
		this.resources = res;
	}

	Recipe(T product) {
		this(product, new Item[1]);
	}

	public String getRecipeName() {
		return product.getName();
	}

	public Item[] getResources() {
		return resources;
	}

}
