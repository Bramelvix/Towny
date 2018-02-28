package entity.mob.work;

import entity.Entity;
import entity.item.Item;

public class Recipe {
	protected Item[] resources;
	protected Entity product;

	protected <T extends Entity> Recipe(T product, Item[] res) {
		this.product = product;
		this.resources = res;
	}

	public String getRecipeName() {
		return product.getName();
	}

	public Item[] getResources() {
		return resources;
	}

}
