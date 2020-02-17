package graphics.ui.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.dynamic.mob.work.Recipe;
import graphics.opengl.OpenGLUtils;
import input.PointerInput;

public class Menu { // the menu is the little options menu that shows up when you right click

	private int x, y; // x and y of the top left corner
	private int ingameX, ingameY;
	private int width = 70; // width and height hardcoded
	private int height = 20;
	private List<MenuItem> items; // spritesheets of items on the menu
	private boolean visible; // is the item visible

	// constructor
	public Menu() {
		x = 0;
		y = 0;
		items = new ArrayList<>();
		hide();
	}

	// render method
	public void render() {
		if (visible) {
			OpenGLUtils.menuDraw(x,y,width,height);
			items.forEach(MenuItem::render);
		}
	}

	// showing the menu
	public void show(PointerInput pointer) {
		visible = true;
		ingameX = pointer.getX();
		ingameY = pointer.getY();
	}

	// getter
	public boolean isVisible() {
		return visible;
	}

	// hiding the menu
	public void hide() {
		this.visible = false;
		this.height = 0;
		items = new ArrayList<>();
	}

	// adding items to the menu
	public void addItems(MenuItem... items) {
		for (MenuItem i : items) {
			addItem(i);
		}
	}

	// updating the menu
	public void update(PointerInput pointer, boolean forceInvisible) {
		if (forceInvisible) {
			hide();
		} else {
			items.forEach(item -> item.update (pointer));
			if (!((pointer.getTrueX() >= getX() - 10)
					&& (pointer.getTrueX() <= getX() + (getWidth() + 10))
					&& (pointer.getTrueY() >= getY() - 10)
					&& (pointer.getTrueY() <= getY() + (getHeight() + 10)))
			){
				hide();
			}
		}
	}

	// getters
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	int getYLocForMenuItem() {
		return y + (items.size() * 15);
	}

	public int getIngameX() {
		return ingameX;
	}

	public int getIngameY() {
		return ingameY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	private Optional<MenuItem> clickedItem(PointerInput pointer, String type) {
		Optional<MenuItem> clickedItem = clickedItem(pointer);
		return clickedItem.isPresent() && clickedItem.get().getText().contains(type) ? clickedItem : Optional.empty();
	}

	public Optional<MenuItem> clickedItem(PointerInput pointer) {
		return items.stream().filter(item -> item.clicked (pointer)).findAny();
	}

	public <T extends Recipe> T recipeFromMenuOption(PointerInput pointer, String menuItem) {
		Optional<MenuItem> clickedItem = clickedItem(pointer, menuItem);
		return clickedItem.<T>map(MenuItem::getRecipe).orElse(null);
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
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}
