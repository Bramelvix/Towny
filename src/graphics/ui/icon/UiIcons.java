package graphics.ui.icon;

import java.awt.Graphics;
import java.security.AllPermission;

import input.Mouse;

public class UiIcons {
	public static Icon[] icons; // array of the 4 icons

	// initialising the icons
	public static void init() {
		icons = new Icon[4];
		icons[0] = new Icon(50, 700, "/res/icons/tools/wood-axe.png", 60, 60);
		icons[1] = new Icon(150, 700, "/res/icons/tools/mining.png", 60, 60);
		icons[2] = new Icon(250, 700, "/res/icons/tools/trowel.png", 60, 60);
		icons[3] = new Icon(350, 700, "/res/icons/tools/sickle.png", 60, 60);

	}

	// rendering the icons on the screen
	public static void render(Graphics g) {
		for (Icon i : icons) {
			i.render(g);
		}

	}

	// getters
	public static boolean hoverOnAnyIcon() {
		return isMiningHover() || isTrowelHover() || isWoodHover();
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

	public static boolean isSickleHover() {
		return icons[3].hoverOn();
	}

	public static boolean isWoodSelected() {
		return icons[0].isSelected();
	}

	public static boolean isMiningSelected() {
		return icons[1].isSelected();
	}

	public static boolean isTrowelSelected() {
		return icons[2].isSelected();
	}

	public static boolean isSickleSelected() {
		return icons[3].isSelected();
	}

	// update the icons
	public static void update(Mouse mouse, boolean forceInvis) {
		for (Icon i : icons) {
			i.setHoverOn((((mouse.getTrueXPixels()) >= i.getX())
					&& ((mouse.getTrueXPixels()) <= i.getX() + (i.getWidth())) && ((mouse.getTrueYPixels()) >= i.getY())
					&& ((mouse.getTrueYPixels()) <= i.getY() + (i.getHeight()))));
		}
		setSelected(mouse);

	}
	//checks if all icons (other than the one provided in num) are unselected
	private static boolean allOtherIconsNotSelected(int num) { 
		for (int i = 0; i <icons.length; i++) {
			if (i==num) continue;
			if (icons[i].isSelected()) return false;
		}
		return true;
	}

	// selecting an icon
	public static void setSelected(Mouse mouse) {
		for (int i = 0; i < 3; i++) {
			if (mouse.getClicked() && icons[i].hoverOn()&&allOtherIconsNotSelected(i))
				icons[i].setSelect(true);
		}
	}

	// deseleting all icons
	public static void deSelect() {
		for (Icon i : icons) {
			i.setSelect(false);
		}
	}

	// dehovering all icons
	public static void deHover() {
		for (Icon i : icons) {
			i.setHoverOn(false);
		}
	}

}
