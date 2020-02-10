package entity.dynamic.mob.work;

import entity.Entity;
import entity.dynamic.item.Item;

public class Recipe {

	private final Item[] resources;
	final Entity product;

	<T extends Entity> Recipe(T product, Item[] res) {
		this.product = product;
		this.resources = res;
	}

	<T extends Entity> Recipe(T product) {
		this(product, new Item[1]);
	}

	public String getRecipeName() {
		return product.getName();
	}

	public Item[] getResources() {
		return resources;
	}

}
