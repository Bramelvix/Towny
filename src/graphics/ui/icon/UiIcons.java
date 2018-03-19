package graphics.ui.icon;

import java.awt.Graphics;
import input.Mouse;

public class UiIcons {
    private static Icon[] icons; // array of the 4 icons

	// initialising the icons
	public static void init() {
        icons = new Icon[6];
		icons[0] = new Icon(5, 220, "/res/icons/tools/wood-axe.png", 30, 30);
		icons[1] = new Icon(40, 220, "/res/icons/tools/mining.png", 30, 30);
		icons[2] = new Icon(75, 220, "/res/icons/tools/sickle.png", 30, 30);
		icons[3] = new Icon(110, 220, "/res/icons/tools/saw.png", 30, 30);
		icons[4] = new Icon(145, 220, "/res/icons/tools/swords.png", 30, 30);
        icons[5] = new Icon(180, 220, "/res/icons/tools/spade.png", 30, 30);

	}

	// rendering the icons on the screen
	public static void render(Graphics g) {
		for (Icon i : icons) {
			i.render(g);
		}

	}

	// getters
    public static boolean hoverOnNoIcons() {
		for (Icon i : icons) {
			if (i.hoverOn()) {
                return false;
            }
        }
        return true;
	}

	public static boolean isWoodHover() {
		return icons[0].hoverOn();
	}

	public static boolean isMiningHover() {
		return icons[1].hoverOn();
	}

	public static boolean isSickleHover() {
		return icons[2].hoverOn();
	}

	public static boolean isSawHover() {
		return icons[3].hoverOn();
	}

	public static boolean isSwordsHover() {
		return icons[4].hoverOn();
	}

	public static boolean isShovelHover() {
		return icons[5].hoverOn();
	}

	public static boolean isWoodSelected() {
		return icons[0].isSelected();
	}

	public static boolean isMiningSelected() {
		return icons[1].isSelected();
	}

	public static boolean isSickleSelected() {
		return icons[2].isSelected();
	}

	public static boolean isSawSelected() {
		return icons[3].isSelected();
	}

	public static boolean isSwordsSelected() {
		return icons[4].isSelected();
	}

	public static boolean isShovelSelected() {
		return icons[5].isSelected();
	}

	// update the icons
    public static void update(Mouse mouse) {
		for (Icon i : icons) {
			i.setHoverOn((((mouse.getTrueXPixels()) >= i.getX())
					&& ((mouse.getTrueXPixels()) <= i.getX() + (i.getWidth())) && ((mouse.getTrueYPixels()) >= i.getY())
					&& ((mouse.getTrueYPixels()) <= i.getY() + (i.getHeight()))));
		}
		setSelected(mouse);

	}

	// checks if all icons (other than the one provided in num) are unselected
	private static boolean allOtherIconsNotSelected(int num) {
		for (int i = 0; i < icons.length; i++) {
			if (i == num) {
				continue;
			}
			if (icons[i].isSelected()) {
				return false;
			}
		}
		return true;
	}

	// selecting an icon
    private static void setSelected(Mouse mouse) {
		for (int i = 0; i < icons.length; i++) {
			if (mouse.getClickedLeft() && icons[i].hoverOn() && allOtherIconsNotSelected(i)) {
				icons[i].setSelect(true);
			}
		}
	}

	// deseleting all icons
	public static void deSelect() {
		for (Icon i : icons) {
			i.setSelect(false);
		}
	}

}
