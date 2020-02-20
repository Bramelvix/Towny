package graphics.ui.icon;

import graphics.opengl.OpenGLUtils;
import input.PointerInput;
import input.PointerMoveEvent;
import util.TextureInfo;
import util.vectors.Vec2f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

//icon on the bottom left of the screen (pickaxe, axe,...)
public class Icon {

	private final float x;
	private final float y; // x and y of the top left corner
	private boolean hover; // is the mouse hovering over the icon
	private float width;
	private float height; // width and length
	private boolean selected; // is the icon selected
	private int id; //OpenGL texture id

	// constructor
	public Icon(float x, float y, String path, float scale, PointerInput pointer) {
		this(x, y, OpenGLUtils.loadTexture(path), scale, pointer);
	}

	public Icon (float x, float y, TextureInfo texture, float scale, PointerInput pointer) {
		this.x = x;
		this.y = y;
		setTexture(texture, scale);
		pointer.on(PointerInput.EType.MOVE, this::update);
	}

	// getters
	boolean isSelected() {
		return selected;
	}

	public float getX() {
		return x;
	}

	public float getY() {
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

	public void setOnClick(PointerInput pointer, Runnable action) {
		pointer.on(PointerInput.EType.RELEASED, event -> {
			if (event.button == GLFW_MOUSE_BUTTON_LEFT && hoverOn()) {
				action.run();
			}
		});
	}

	public void setTexture(TextureInfo info, float scale) {
		this.id = info.id;
		this.height = info.height * scale;
		this.width = info.width * scale;
	}

	public int getTextureId() {
		return id;
	}

}
