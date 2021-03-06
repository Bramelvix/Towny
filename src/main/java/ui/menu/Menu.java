package ui.menu;

import java.util.ArrayList;
import java.util.List;

import events.Subscription;
import graphics.opengl.OpenGLUtils;
import ui.UiElement;
import input.PointerInput;
import events.PointerMoveEvent;
import util.vectors.Vec2f;

public class Menu extends UiElement { // the menu is the little options menu that shows up when you right click

	private final List<MenuItem> items; // list of items on the menu
	private final Subscription subscription;

	// constructor
	public Menu(PointerInput pointer) {
		super(new Vec2f(pointer.getTrueX(), pointer.getTrueY()), new Vec2f(70, 0), false);
		items = new ArrayList<>();
		subscription = pointer.on(PointerInput.EType.MOVE, this::update);
	}

	// render method
	public void render() {
		if (visible) {
			OpenGLUtils.menuDraw(position, size);
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
		this.size.y = 0;
		items.forEach(MenuItem::destroy);
		subscription.unsubscribe();
	}

	// adding items to the menu
	public void addItems(MenuItem... items) {
		for (MenuItem i : items) {
			addItem(i);
		}
	}

	public void update (PointerMoveEvent event) {
		if (visible && (
			event.x <= getX() - 10
			|| event.x >= getX() + getWidth() + 10
			|| event.y <= getY() - 10
			|| event.y >= getY() + getHeight() + 10
		)) {
			hide();
		}
	}

	// getters
	public float getWidth() {
		return size.x;
	}

	public float getHeight() {
		return size.y;
	}

	float getYLocForMenuItem() {
		return position.y + (items.size() * 24);
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	// adding an item to the menu
	private void addItem(MenuItem o) {
		if (o == null || items.contains(o)) { return; }
		o.init(this);
		items.add(o);
		if (o.getText().length() > size.x / 10) {
			size.x += ((o.getText().length() * 10) - size.x);
		}
		size.y += 24;

	}

}
