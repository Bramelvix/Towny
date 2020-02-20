package graphics.ui.icon;

import graphics.opengl.OpenGLUtils;
import input.PointerInput;
import input.PointerMoveEvent;
import util.TextureInfo;
import util.vectors.Vec2f;

//icon on the bottom left of the screen (pickaxe, axe,...)
public class Icon {

	private final int x;
	private final int y; // x and y of the top left corner
	private boolean hover; // is the mouse hovering over the icon
	private final float width;
	private final float height; // width and length
	private boolean selected; // is the icon selected
	private final int id; //OpenGL texture id

	// constructor
	public Icon(int x, int y, String path, float scale, PointerInput pointer) {
		this.x = x;
		this.y = y;
		TextureInfo imgInfo = OpenGLUtils.loadTexture(path);
		id = imgInfo.id;
		width = imgInfo.width * scale;
		height = imgInfo.height * scale;
		pointer.on(PointerInput.EType.MOVE, this::update);
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
		update(pointer.getTrueX(), pointer.getTrueY());
	}
	public void update (PointerMoveEvent event) {
		update(event.x, event.y);
	}
	public void update(double x, double y) {
		setHover(x >= getX() && (x <= getX() + getWidth()) && (y >= getY()) && (y <= getY() + getHeight()));
	}

}
