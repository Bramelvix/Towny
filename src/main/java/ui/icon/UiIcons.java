package ui.icon;

public class UiIcons {

	private final Icon[] icons; // array of the 4 icons

	// initialising the icons
	public UiIcons(float scale) {

		icons = new Icon[7];
		icons[0] = new Icon(15, 660, "/icons/tools/wood-axe.png", scale, this::deSelect);
		icons[1] = new Icon(120, 660, "/icons/tools/mining.png", scale, this::deSelect);
		icons[2] = new Icon(225, 660, "/icons/tools/sickle.png", scale, this::deSelect);
		icons[3] = new Icon(330, 660, "/icons/tools/saw.png", scale, this::deSelect);
		icons[4] = new Icon(435, 660, "/icons/tools/swords.png", scale, this::deSelect);
		icons[5] = new Icon(540, 660, "/icons/tools/spade.png", scale, this::deSelect);
		icons[6] = new Icon(645, 660, "/icons/tools/plow.png", scale, this::deSelect);
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

	public void setShovelOnClick(Runnable action) {
		icons[5].setOnClick(action);
	}

	public void setPlowOnclick(Runnable action) {
		icons[6].setOnClick(action);
	}

	public void setSawOnClick(Runnable action) {
		icons[3].setOnClick(action);
	}

	public void setAxeOnClick(Runnable action) {
		icons[0].setOnClick(action);
	}

	public void setMiningOnClick(Runnable action) {
		icons[1].setOnClick(action);
	}

	public void setSwordsOnMouseReleaseWhenSelected(Runnable action) {
		icons[4].setOnMouseReleaseWhenSelected(action, this::hoverOnNoIcons);
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
