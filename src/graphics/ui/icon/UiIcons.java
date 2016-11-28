package graphics.ui.icon;

import graphics.Screen;
import input.Mouse;

public class UiIcons {
	public static Icon[] icons;

	public static void init() {
		icons = new Icon[3];
		icons[0] = new Icon(10, 190, "/res/icons/wood-axe.png", 20, 20);
		icons[1] = new Icon(40, 190, "/res/icons/mining.png", 20, 20);
		icons[2] = new Icon(70, 190, "/res/icons/trowel.png", 20, 20);

	}

	public static void render(Screen screen) {
		for (Icon i : icons) {
			i.render(screen);
		}

	}

	public static void update(Mouse mouse) {
		for (Icon i : icons) {
			i.setHoverOn((((mouse.getTrueX()) >= i.getX()) && ((mouse.getTrueX()) <= i.getX() + (i.WIDTH))
					&& ((mouse.getTrueY()) >= i.getY()) && ((mouse.getTrueY()) <= i.getY() + (i.HEIGHT))));
		}

	}
	public static boolean isWoodHover() {
		return icons[0].hoverOn();
	}
	public static boolean isMiningHover() {
		return icons[1].hoverOn();
	}
	public static boolean isTrowelHover() {
		return icons[2].hoverOn();
	}
	public static void select() {
		for (Icon i : icons) {
			i.setSelect(i.hoverOn());
		}
	}
	public static boolean isWoodSelected() {
		return icons[0].selected();
	}
	public static boolean isMiningSelected() {
		return icons[1].selected();
	}
	public static boolean isTrowelSelected() {
		return icons[2].selected();
	}
	public static void setMiningSelected(boolean select) {
		icons[1].setSelect(select);
	}
	public static void setWoodSelected(boolean select) {
		icons[0].setSelect(select);
	}
	public static void setTrowelSelected(boolean select) {
		icons[2].setSelect(select);
	}
	

}
