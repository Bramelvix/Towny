package entity;

import java.util.Random;

import graphics.Sprite;
import input.PointerInput;

public abstract class Entity {

    protected int x, y, z; // x, y and z of the entity
    public static final Random RANDOM = new Random(); // random needed for various chance calculations
    public Sprite sprite; // the entity's sprite
    private boolean visible; // is the entity visible or not
	private boolean transparent = true; // non transparent entities will stop the Tile sprite under them from rendering unnecessarily
    private boolean selected = false;
    private String displayName;

    // setters
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setTransparent(boolean transparent) { this.transparent = transparent; }

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
    	setVisible(true);
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

    protected boolean isSelected() {
        return selected;
    }

    public boolean isTransparent() { return transparent; }

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
    public boolean hoverOn(PointerInput pointer, int z) {
        return (z == this.z && pointer.getX() >= this.x && pointer.getX() <= this.x + Sprite.SIZE && pointer.getY() >= this.y && pointer.getY() <= this.y + Sprite.SIZE);
    }

    // render method
    public void render(float xOffset, float yOffset) {
       if (isVisible()) {
		   sprite.draw(x, y, xOffset, yOffset);
	   }
    }

    public void renderIf(boolean ifCondition, float xOffset, float yOffset) {
        if (ifCondition) {
        	render(xOffset, yOffset);
        }
    }

}
