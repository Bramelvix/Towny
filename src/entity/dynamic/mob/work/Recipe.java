package entity.dynamic.mob.work;

import entity.Entity;
import entity.dynamic.item.Item;

public class Recipe {
    private Item[] resources;
    Entity product;

    <T extends Entity> Recipe(T product, Item[] res) {
        this.product = product;
        if (res == null) {
            resources = new Item[1];
        } else {
            this.resources = res;
        }
    }

    public String getRecipeName() {
        return product.getName();
    }

    public Item[] getResources() {
        return resources;
    }

}
