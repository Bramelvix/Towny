package ui.icon;

import events.PointerMoveEvent;
import events.Subscription;
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
	private final Subscription subscription;

	// constructor
	public Icon(float x, float y, String path, float scale, Runnable deselect) {
		this(x, y, OpenGLUtils.loadTexture(path), scale, deselect);
	}

	public Icon(float x, float y, TextureInfo texture, float scale, Runnable deselect) {
		super(new Vec2f(x, y), new Vec2f(texture.width() * scale, texture.height() * scale));
		setTexture(texture.id());
		subscription = PointerInput.getInstance().on(PointerInput.EType.MOVE, this::update); //TODO THIS SUCKS
		this.deselect = deselect;
		if (deselect != null) {
			setOnClick(() -> {
				this.deselect.run();
				setSelect(true);
			});
		}
	}

	public Icon(float x, float y, String path, float scale) {
		this(x, y, OpenGLUtils.loadTexture(path), scale, null);
	}

	public Icon(float x, float y, TextureInfo texture, float scale) {
		this(x, y, texture, scale, null);
	}

	// getters
	boolean isSelected() {
		return selected;
	}

	public boolean hoverOn() {
		return hover;
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
		setHover(mouseOver(x, y));
	}

	public void setOnClick(Runnable action) {
		PointerInput.getInstance().on(PointerInput.EType.RELEASED, onlyWhen(
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
		subscription.unsubscribe();
	}

}
