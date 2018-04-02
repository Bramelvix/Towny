package graphics.ui.menu;

import java.util.ArrayList;
import java.util.List;

import entity.dynamic.mob.work.Recipe;
import graphics.OpenglUtils;
import input.MousePosition;

public class Menu { // the menu is the little options menu that shows up when
					// you right click
	private int x, y; // x and y of the top left corner
	private int ingameX, ingameY;
	private int width = 70; // width and height hardcoded
	private int height = 20;
    private List<MenuItem> items; // spritesheets of items on the menu
	private boolean visible; // is the item visible

	// render method
	public void render() {
		if (visible) {
			OpenglUtils.menuDraw(x,y,width,height);
			items.forEach((MenuItem i) -> i.render());
		}
	}

	// showing the menu
	public void show() {
		visible = true;
		ingameX = MousePosition.getX();
		ingameY = MousePosition.getY();
	}

	// getter
	public boolean isVisible() {
		return visible;
	}

	// setter
    private void setVisible(boolean vis) {
		if (visible) {
			visible = vis;
		}
	}

	// hiding the menu
	public void hide() {
		setVisible(false);
		this.width = 0;
		this.height = 0;
	}

	// adding items to the menu
	public void addItems(MenuItem... items) {
		for (MenuItem i : items) {
			addItem(i);
		}
	}

	// updating the menu
	public void update(boolean forceinvisible) {
		if (forceinvisible) {
			hide();
		} else {
			items.forEach((MenuItem i) -> i.update());
			setVisible((((MousePosition.getTrueXPixels()) >= getX() - 10)
					&& ((MousePosition.getTrueXPixels()) <= getX() + (getWidth() + 10))
					&& ((MousePosition.getTrueYPixels()) >= getY() - 10)
					&& ((MousePosition.getTrueYPixels()) <= getY() + (getHeight() + 10))));
		}
	}

	// constructor
	public Menu() {
		x = 0;
		y = 0;
        items = new ArrayList<>();
		setVisible(false);
	}

	// getters
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getYLocForMenuItem() {
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

    private MenuItem clickedItem(String type) {
		return clickedItem() != null && clickedItem().getText().contains(type) ? clickedItem() : null;

	}

	public MenuItem clickedItem() {
		for (MenuItem i : items) {
			if (i.clicked()) {
				return i;
			}
		}
		return null;
	}

	public <T extends Recipe> T recipeFromMenuOption(String menuItem) {
		MenuItem clickedItem = clickedItem(menuItem);
		if (clickedItem != null) {
			return clickedItem.getRecipe();
		}
		return null;
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
