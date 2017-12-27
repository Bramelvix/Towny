package graphics.ui.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import entity.mob.work.Recipe;
import input.Mouse;

public class Menu { // the menu is the little options menu that shows up when
					// you right click
	private int x, y; // x and y of the top left corner
	private int ingameX, ingameY;
	private int width = 70; // width and height hardcoded
	private int height = 20;
	public List<MenuItem> items; // list of items on the menu
	private List<Entity> menuItemEntities;
	private boolean visible; // is the item visible
	private Color colour = new Color(91, 94, 99, 210); // the grey-blue colour
														// of the background of
														// the menu

	// render method
	public void render(Graphics g) {
		if (visible) {
			g.setColor(colour);
			g.fillRect(x, y, width, height);
			items.forEach((MenuItem i) -> i.render(g));
		}
	}

	// showing the menu
	public void show(Mouse mouse) {
		visible = true;
		ingameX = mouse.getX();
		ingameY = mouse.getY();
	}

	// getter
	public boolean isVisible() {
		return visible;
	}

	// setter
	public void setVisible(boolean vis) {
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
	public void update(Mouse mouse, boolean forceinvisible) {
		if (forceinvisible) {
			hide();
			return;
		} else {
			items.forEach((MenuItem i) -> i.update(mouse));
			setVisible((((mouse.getTrueXPixels()) >= getX() - 10)
					&& ((mouse.getTrueXPixels()) <= getX() + (getWidth() + 10))
					&& ((mouse.getTrueYPixels()) >= getY() - 10)
					&& ((mouse.getTrueYPixels()) <= getY() + (getHeight() + 10))));
		}
	}

	// constructor
	public Menu() {
		x = 0;
		y = 0;
		items = new ArrayList<MenuItem>();
		menuItemEntities = new ArrayList<Entity>();
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

	public boolean clickedOnItem(String type, Mouse mouse) {
		return clickedItem(type, mouse) != null;
	}

	private MenuItem clickedItem(String type, Mouse mouse) {
		for (MenuItem i : items) {
			if (i.getText().contains((type)) && i.clicked(mouse)) {
				return i;
			}
		}
		return null;
	}

	public Recipe recipeFromCraftOption(Mouse mouse) {
		MenuItem clickedItem = clickedItem(MenuItem.CRAFT, mouse);
		if (clickedItem != null) {
			return clickedItem.getRecipe();
		}
		return null;
	}

	@SuppressWarnings("unchecked") // shouldnt ever be a problem
	public <T extends Entity> T getEntity(String... checkText) {
		for (MenuItem item : items) {
			for (String text : checkText) {
				if (item.getText().contains(text)) {
					return (T) item.getEntity();
				}
			}
		}
		return null;
	}

	// adding an item to the menu
	public void addItem(MenuItem o) {
		if (o != null) {
			if (!(items.contains(o))) {
				o.init(this);
				items.add(o);
				if (o.getText().length() > width / 10) {
					width += ((o.getText().length() * 10) - width);
				}
				height = height + 15;
				if (o.getEntity() != null) {
					menuItemEntities.add(o.getEntity());
				}
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
