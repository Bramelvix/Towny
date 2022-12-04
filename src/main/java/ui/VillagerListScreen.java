package ui;


import graphics.opengl.OpenGLUtils;
import util.vectors.Vec2f;

public class VillagerListScreen extends UiElement {
	protected VillagerListScreen(Vec2f position, Vec2f size) {
		super(position, size);
	}

	@Override
	public void render() {
		OpenGLUtils.drawFilledSquare(position, size, colour);
	}
}
