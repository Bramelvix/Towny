package graphics.ui.icon;

import graphics.opengl.OpenGLUtils;
import graphics.ui.UiElement;
import input.PointerInput;
import input.PointerMoveEvent;
import util.TextureInfo;
import util.vectors.Vec2f;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

//icon on the bottom left of the screen (pickaxe, axe,...)
public class Icon extends UiElement {

	private boolean hover; // is the mouse hovering over the icon
	private boolean selected; // is the icon selected
	private int id; //OpenGL texture id

	// constructor
	public Icon(float x, float y, String path, float scale, PointerInput pointer) throws IOException {
		this(x, y, OpenGLUtils.loadTexture(path), scale, pointer);
	}

	public Icon (float x, float y, TextureInfo texture, float scale, PointerInput pointer) {
		super(new Vec2f(x, y), new Vec2f(texture.width*scale, texture.height*scale));
		setTexture(texture.id);
		pointer.on(PointerInput.EType.MOVE, this::update);
	}

	// getters
	boolean isSelected() {
		return selected;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public boolean hoverOn() {
		return hover;
	}

	public float getWidth() {
		return size.x;
	}
	public float getHeight () {
		return size.y;
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
		OpenGLUtils.iconDraw(id, position, size, selected || hover);
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

	public void setTexture(int textureId) {
		this.id = textureId;
	}

	public int getTextureId() {
		return id;
	}

}
