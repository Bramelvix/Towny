package entity.mob.work;

import entity.Entity;

public class Recipe {
	protected String[] resources;
	protected Entity product;

	protected <T extends Entity> Recipe(T product, String[] res) {
		this.product = product;
		this.resources = res;
	}

	public String getRecipeName() {
		return product.getName();
	}
	public String[] getResources() {
		return resources;
	}

}
