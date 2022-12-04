package ui.menu;

import events.PointerMoveEvent;
import events.Subscription;
import graphics.opengl.OpenGLUtils;
import input.PointerInput;
import ui.UiElement;
import util.vectors.Vec2f;

import java.util.ArrayList;
import java.util.List;

public class Menu extends UiElement { // the menu is the little options menu that shows up when you right click

	private final List<MenuItem> items; // list of items on the menu
	private final Subscription subscription;

	// constructor
	public Menu() {
		super(new Vec2f(PointerInput.getInstance().getTrueX(), PointerInput.getInstance().getTrueY()), new Vec2f(70, 0), false);
		items = new ArrayList<>();
		subscription = PointerInput.getInstance().on(PointerInput.EType.MOVE, this::update);
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

	public void update(PointerMoveEvent event) {
		if (visible && !mouseOver(event.x, event.y, 10)) {
			hide();
		}
	}

	float getYLocForMenuItem() {
		return position.y + (items.size() * 24);
	}

	// adding an item to the menu
	private void addItem(MenuItem o) {
		if (o == null || items.contains(o)) {
			return;
		}
		o.init(this);
		items.add(o);
		if (o.getText().length() > size.x / 10) {
			size.x += ((o.getText().length() * 10) - size.x);
		}
		size.y += 24;

	}

}
