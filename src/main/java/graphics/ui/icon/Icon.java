package graphics.ui.icon;

import graphics.opengl.OpenGLUtils;
import input.PointerInput;
import util.TextureInfo;
import util.vectors.Vec2f;

//icon on the bottom left of the screen (pickaxe, axe,...)
public class Icon {

	private int x, y; // x and y of the top left corner
	private boolean hover; // is the mouse hovering over the icon
	private float width,height; // width and length
	private boolean selected; // is the icon selected
	private int id; //OpenGL texture id

	// constructor
	public Icon(int x, int y, String path, float scale) {
		this.x = x;
		this.y = y;
		TextureInfo imgInfo = OpenGLUtils.loadTexture(path);
		id = imgInfo.id;
		width = imgInfo.width * scale;
		height = imgInfo.height * scale;
	}

	// getters
	boolean isSelected() {
		return selected;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean hoverOn() {
		return hover;
	}

	public float getWidth() {
		return width;
	}
	public float getHeight () {
		return height;
	}

	// setters
	void setSelect(boolean select) {
		this.selected = select;
	}

	private void setHover(boolean hover) {
		this.hover = hover;
	}

	//render the icon on the screen
	public void render() {
		OpenGLUtils.iconDraw(id, new Vec2f(x, y), new Vec2f(width, height), selected || hover);
	}

	public void update(PointerInput pointer) {
		setHover((pointer.getTrueX() >= getX())
				&& (pointer.getTrueX() <= getX() + getWidth())
				&& (pointer.getTrueY() >= getY())
				&& (pointer.getTrueY() <= getY() + getHeight())
		);
	}

}
