package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import graphics.Screen;
import graphics.Sprite;
import input.Mouse;
import map.Level;

public abstract class Entity {
    protected int x, y, z; // x and y of the entity
    public static final Random RANDOM = new Random(); // random needed for
    // various chance
    // calculations
    public List<Sprite> sprites; // the entity's sprites
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
        sprites = new ArrayList<>();
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
    public boolean hoverOn(Mouse mouse, int z) {
        return hoverOn(mouse.getX(), mouse.getY(), z);
    }

    // does the mouse hover over the entity
    public boolean hoverOn(int x, int y, int z) {
        return (z == this.z && x >= this.x && x <= this.x + Sprite.SIZE && y >= this.y && y <= this.y + Sprite.SIZE);
    }

    // render method
    public void render(Screen screen) {
        if (sprites.size() > 1) screen.renderMultiSprite(x, y, sprites);
        else screen.renderSprite(x, y, sprites.get(0));

    }
}
