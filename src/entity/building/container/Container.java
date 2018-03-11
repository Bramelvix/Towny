package entity.building.container;

import entity.building.BuildAbleObject;
import entity.item.Item;

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
            items.add(item);
        }
    }

    public <T extends Item> T takeItem(int index) {
        Item item = items.get(index);
        items.remove(item);
        return (T) item;
    }

    public <T extends Item> T takeItem(T e) {
        for (Item i : items) {
            if (i.getId() == e.getId()) {
                return (T) i;
            }
        }
        return null;
    }

    public Item[] getItems() {
        return items.toArray(new Item[0]);
    }

    public boolean isFull() {
        return items.size() == containerSize;
    }

}
