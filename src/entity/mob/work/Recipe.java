package entity.mob.work;

import entity.Entity;
import entity.item.Item;

public class Recipe {
    private Item[] resources;
    Entity product;

    <T extends Entity> Recipe(T product, Item[] res) {
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
