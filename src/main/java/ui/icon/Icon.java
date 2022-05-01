package ui.icon;

import events.PointerMoveEvent;
import graphics.TextureInfo;
import graphics.opengl.OpenGLUtils;
import input.PointerInput;
import ui.UiElement;
import util.vectors.Vec2f;

import static events.EventListener.onlyWhen;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

//icon on the bottom left of the screen (pickaxe, axe,...)
public class Icon extends UiElement {

	private boolean hover; // is the mouse hovering over the icon
	private boolean selected; // is the icon selected
	private int id; //OpenGL texture id
	private final Runnable deselect;

	// constructor
	public Icon(float x, float y, String path, float scale, PointerInput pointer, Runnable deselect) {
		this(x, y, OpenGLUtils.loadTexture(path), scale, pointer, deselect);
	}

	public Icon(float x, float y, TextureInfo texture, float scale, PointerInput pointer, Runnable deselect) {
		super(new Vec2f(x, y), new Vec2f(texture.width() * scale, texture.height() * scale));
		setTexture(texture.id());
		pointer.on(PointerInput.EType.MOVE, this::update); //TODO THIS SUCKS
		this.deselect = deselect;
		if (deselect != null) {
			setOnClick(pointer, () -> {
				this.deselect.run();
				setSelect(true);
			});
		}
	}

	public Icon(float x, float y, String path, float scale, PointerInput pointer) {
		this(x, y, OpenGLUtils.loadTexture(path), scale, pointer, null);
	}

	public Icon(float x, float y, TextureInfo texture, float scale, PointerInput pointer) {
		this(x, y, texture, scale, pointer, null);
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

	public float getHeight() {
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

	public void update(PointerMoveEvent event) {
		update(event.x, event.y);
	}

	public void update(double x, double y) {
		setHover(x >= getX() && (x <= getX() + getWidth()) && (y >= getY()) && (y <= getY() + getHeight()));
	}

	public void setOnClick(PointerInput pointer, Runnable action) {
		pointer.on(PointerInput.EType.RELEASED, onlyWhen(
				event -> event.button == GLFW_MOUSE_BUTTON_LEFT && hoverOn(),
				event -> action.run())
		);
	}

	public void setTexture(int textureId) {
		this.id = textureId;
	}

	public int getTextureId() {
		return id;
	}

	public void destroy() {
		OpenGLUtils.deleteTexture(id);
	}

}
