package entity;

import java.util.Random;

import graphics.Sprite;
import input.MousePosition;

public abstract class Entity {
    protected int x, y, z; // x and y of the entity
    public static final Random RANDOM = new Random(); // random needed for various chance calculations
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
    protected boolean isVisible() {
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

    protected boolean isSelected() {
        return selected;
    }

    // basic constructor
    public Entity(int x, int y, int z) {
        this();
        setLocation(x, y, z);
    }

    public Entity(String name, Sprite sprite) {
        this();
        setName(name);
        this.sprite = sprite;
    }

    // does the mouse hover over the entity
    public boolean hoverOn(int z) {
        return (z == this.z && MousePosition.getX() >= this.x && MousePosition.getX() <= this.x + Sprite.SIZE && MousePosition.getY() >= this.y && MousePosition.getY() <= this.y + Sprite.SIZE);
    }

    // render method
    public void render() {
        sprite.draw(x, y);
    }

    public void renderIf(boolean ifCondition) {
        if (ifCondition) { render(); }
    }
}
