package graphics.ui.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entity.Entity;
import entity.item.Item;
import input.Mouse;

public class MenuItem {
	private String text; // text on the menuitem
	public int x, y; // x and y of top left corner
	private int width; // width of the menuitem
	private boolean hover; // is the mouse hovering over the item
	private static final Font font = new Font("Dialog", Font.LAYOUT_LEFT_TO_RIGHT, 15); // font
	// some static strings to use as menuitem texts
	public static final String CANCEL = "Cancel";
	public static final String MOVE = "Move";
	public static final String CHOP = "Chop";
	public static final String MINE = "Mine";
	public static final String BUILD = "Build";
	public static final String PICKUP = "Pick up";
	public static final String DROP = "Drop";
	public static final String FIGHT = "Fight";
	public static final String EQUIP = "Equip";
	public static final String WEAR = "Wear";
	public static final String CRAFT = "Craft";
	private Entity entity;

	// constructor
	public MenuItem(String text) {
		this.text = text;

	}

	public MenuItem(String text, Entity e) {
		this(text);
		this.entity = e;

	}

	public void init(Menu menu) {
		x = menu.getX();
		width = menu.getWidth();
		y = menu.getYLocForMenuItem();
	}

	public Entity getEntity() {
		return entity;
	}

	public static String getMenuItemText(String menuItem, Entity entity) {
		if (entity instanceof Item)
			return menuItem + " " + entity.getName() + " (" + ((Item) entity).quantity + ")";
		return menuItem + " " + entity.getName();
	}

	// rendering the menuitem's text
	public void render(Graphics g) {
		g.setColor(hover ? Color.red : Color.black);
		g.setFont(font);
		g.drawString(text, x, y + 15);
	}

	// updateing the mouse hover
	public void update(Mouse mouse) {
		hover = ((((mouse.getTrueXPixels()) >= x) && ((mouse.getTrueXPixels()) <= x + width)
				&& ((mouse.getTrueYPixels()) >= y) && ((mouse.getTrueYPixels()) <= y + 10)));
	}

	// getter
	public boolean clicked(Mouse mouse) {
		return hover && mouse.getButton() == 1;
	}

	// the menuitem is equal to other menuitems with the same text
	public boolean equals(String i) {
		return text.contains(i);
	}

	public String getText() {
		return text;
	}

}
