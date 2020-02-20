package graphics.ui.icon;

import input.PointerClickEvent;
import input.PointerInput;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class UiIcons {

	private final Icon[] icons; // array of the 4 icons

	// initialising the icons
	public UiIcons (float scale, PointerInput pointer) {

		icons = new Icon[7];
		icons[0] = new Icon(15, 660, "/icons/tools/wood-axe.png", scale, pointer);
		icons[1] = new Icon(120, 660, "/icons/tools/mining.png", scale, pointer);
		icons[2] = new Icon(225, 660, "/icons/tools/sickle.png", scale, pointer);
		icons[3] = new Icon(330, 660, "/icons/tools/saw.png", scale, pointer);
		icons[4] = new Icon(435, 660, "/icons/tools/swords.png", scale, pointer);
		icons[5] = new Icon(540, 660, "/icons/tools/spade.png", scale, pointer);
		icons[6] = new Icon(645, 660, "/icons/tools/plow.png", scale, pointer);
		pointer.on(PointerInput.EType.RELEASED, this::setSelected);
	}

	// rendering the icons on the screen
	public void render() {
		for (Icon i : icons) {
			i.render();
		}
	}

	// getters
	public boolean hoverOnNoIcons() {
		for (Icon i : icons) {
			if (i.hoverOn()) {
				return false;
			}
		}
		return true;
	}

	public void setShovelOnClick(PointerInput pointer, Runnable action) {
		icons[5].setOnClick(pointer, action);
	}

	public void setPlowOnclick(PointerInput pointer, Runnable action) {
		icons[6].setOnClick(pointer, action);
	}

	public void setSawOnClick(PointerInput pointer, Runnable action) {
		icons[3].setOnClick(pointer, action);
	}

	public boolean isAxeSelected() {
		return icons[0].isSelected();
	}

	public boolean isMiningSelected() {
		return icons[1].isSelected();
	}

	public boolean isSwordsSelected() {
		return icons[4].isSelected();
	}

	// checks if all icons (other than the one provided in num) are unselected
	private boolean allOtherIconsNotSelected(int num) {
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
	private void setSelected(PointerClickEvent event) {
		for (int i = 0; i < icons.length; i++) {
			if (event.button == GLFW_MOUSE_BUTTON_LEFT && icons[i].hoverOn() && allOtherIconsNotSelected(i)) {
				icons[i].setSelect(true);
			}
		}
	}

	// deseleting all icons
	public void deSelect() {
		for (Icon i : icons) {
			i.setSelect(false);
		}
	}

}
