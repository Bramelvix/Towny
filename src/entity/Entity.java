package entity;

import java.util.Random;

import graphics.Sprite;
import input.MousePosition;

public abstract class Entity {
    protected int x, y, z; // x and y of the entity
    public static final Random RANDOM = new Random(); // random needed for
    // various chance
    // calculations
    public Sprite sprite; // the entity's sprite
    private boolean visible; // is the entity visible or not
    private boolean selected = false;
    private String displayName;

    // setters
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return displayName;
    }

    public void setName(String name) {
        displayName = name;
    }

    public void setLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Entity() {
    }

    // getters
    public boolean isVisible() {
        return visible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public boolean isSelected() {
        return selected;
    }

    // basic constructor
    public Entity(int x, int y, int z) {
        this();
        setLocation(x, y, z);
    }

    // does the mouse hover over the entity
    public boolean hoverOn(int z) {
        return hoverOn(MousePosition.getX(), MousePosition.getY(), z);
    }

    // does the mouse hover over the entity
    public boolean hoverOn(int x, int y, int z) {
        return (z == this.z && x >= this.x && x <= this.x + Sprite.SIZE && y >= this.y && y <= this.y + Sprite.SIZE);
    }

    // render method
    public void render() {
        sprite.draw(x,y);

    }
}
