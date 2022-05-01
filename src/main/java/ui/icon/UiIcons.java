package ui.icon;

import input.PointerInput;

public class UiIcons {

	private final Icon[] icons; // array of the 4 icons

	// initialising the icons
	public UiIcons(float scale, PointerInput pointer) {

		icons = new Icon[7];
		icons[0] = new Icon(15, 660, "/icons/tools/wood-axe.png", scale, pointer, this::deSelect);
		icons[1] = new Icon(120, 660, "/icons/tools/mining.png", scale, pointer, this::deSelect);
		icons[2] = new Icon(225, 660, "/icons/tools/sickle.png", scale, pointer, this::deSelect);
		icons[3] = new Icon(330, 660, "/icons/tools/saw.png", scale, pointer, this::deSelect);
		icons[4] = new Icon(435, 660, "/icons/tools/swords.png", scale, pointer, this::deSelect);
		icons[5] = new Icon(540, 660, "/icons/tools/spade.png", scale, pointer, this::deSelect);
		icons[6] = new Icon(645, 660, "/icons/tools/plow.png", scale, pointer, this::deSelect);
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

	// deseleting all icons
	public void deSelect() {
		for (Icon i : icons) {
			i.setSelect(false);
		}
	}

	public void destroy() {
		for (Icon i : icons) {
			i.destroy();
		}
	}

}
