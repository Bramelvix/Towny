package graphics.ui.icon;

import input.MouseButton;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class UiIcons {
    private static Icon[] icons; // array of the 4 icons

	// initialising the icons
	public static void init() {
		float scale = 0.176056338028169f;
        icons = new Icon[7];
		icons[0] = new Icon(15, 660, "/res/icons/tools/wood-axe.png",scale);
		icons[1] = new Icon(120, 660, "/res/icons/tools/mining.png",scale);
		icons[2] = new Icon(225, 660, "/res/icons/tools/sickle.png",scale);
		icons[3] = new Icon(330, 660, "/res/icons/tools/saw.png",scale);
		icons[4] = new Icon(435, 660, "/res/icons/tools/swords.png",scale);
        icons[5] = new Icon(540, 660, "/res/icons/tools/spade.png",scale);
		icons[6] = new Icon(645, 660, "/res/icons/tools/plow.png",scale);
	}

	// rendering the icons on the screen
	public static void render() {
		for (Icon i : icons) {
			i.render();
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

	public static boolean isPlowSelected() { return icons[6].isSelected();}

	public static boolean isPlowHover() {return icons[6].hoverOn();}

	// update the icons
    public static void update() {
		for (Icon i : icons) {
			i.update();
	}
		setSelected();

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
    private static void setSelected() {
		for (int i = 0; i < icons.length; i++) {
			if (MouseButton.wasPressed(GLFW_MOUSE_BUTTON_LEFT) && icons[i].hoverOn() && allOtherIconsNotSelected(i)) {
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
