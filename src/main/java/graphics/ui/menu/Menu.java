package graphics.ui.menu;

import java.util.ArrayList;
import java.util.List;

import graphics.opengl.OpenGLUtils;
import input.PointerInput;
import util.vectors.Vec2f;

public class Menu { // the menu is the little options menu that shows up when you right click

	private final Vec2f position; // x and y of the top left corner
	private float width = 70; // width and height hardcoded
	private float height = 20;
	private List<MenuItem> items; // list of items on the menu
	private boolean visible; // is the item visible
	private final PointerInput pointer;

	// constructor
	public Menu(PointerInput pointer) {
		this.pointer = pointer;
		position = new Vec2f(pointer.getTrueX(), pointer.getTrueY());
		//ingame = new Vec2f(0);
		items = new ArrayList<>();
		hide();
	}

	// render method
	public void render() {
		if (visible) {
			OpenGLUtils.menuDraw(position, new Vec2f(width,height));
			items.forEach(MenuItem::render);
		}
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	// getter
	public boolean isVisible() {
		return visible;
	}

	// hiding the menu
	public void hide() {
		this.visible = false;
		this.height = 0;
		items.forEach(MenuItem::destroy);
		items = new ArrayList<>();
	}

	// adding items to the menu
	public void addItems(MenuItem... items) {
		for (MenuItem i : items) {
			addItem(i);
		}
	}

	// updating the menu
	public void update(boolean forceInvisible) {
		if (forceInvisible) {
			hide();
		} else {
			if (!(
					(pointer.getTrueX() >= getX() - 10)
					&& (pointer.getTrueX() <= getX() + (getWidth() + 10))
					&& (pointer.getTrueY() >= getY() - 10)
					&& (pointer.getTrueY() <= getY() + (getHeight() + 10))
			)){
				hide();
			}
		}
	}

	// getters
	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	float getYLocForMenuItem() {
		return position.y + (items.size() * 15);
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	// adding an item to the menu
	private void addItem(MenuItem o) {
		if (o != null) {
			if (!(items.contains(o))) {
				o.init(this);
				items.add(o);
				if (o.getText().length() > width / 10) {
					width += ((o.getText().length() * 10) - width);
				}
				height = height + 15;
			}
		}
	}

	// setter
	public void setX(float x) {
		this.position.x = x;
	}

	public void setY(float y) {
		this.position.y = y;
	}

}
