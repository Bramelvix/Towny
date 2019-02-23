package entity.nonDynamic.building.container;

import entity.nonDynamic.building.BuildAbleObject;
import entity.dynamic.item.Item;

import java.util.ArrayList;

public abstract class Container extends BuildAbleObject {
    private ArrayList<Item> items;
    private int containerSize;

    protected Container(int containerSize) {
        items = new ArrayList<>();
        this.containerSize = containerSize;
    }

    public <T extends Item> void addItemTo(T item) {
        if (!isFull()) {
            item.setLocation(x, y, z);
            items.add(item);
        }
    }

    public <T extends Item> T takeItem(T e) {
        for (Item i : items) {
            if (i.getId() == e.getId()) {
                items.remove(e);
                return (T) i;
            }
        }
        return null;
    }

    public Item[] getItems() {
        return items.toArray(new Item[0]);
    }

    private boolean isFull() {
        return items.size() == containerSize;
    }

}
