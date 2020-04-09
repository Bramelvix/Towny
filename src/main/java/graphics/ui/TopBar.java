package graphics.ui;

import graphics.opengl.OpenGLUtils;
import graphics.ui.icon.Icon;
import input.PointerInput;
import graphics.TextureInfo;
import util.vectors.Vec2f;

import javax.imageio.ImageIO;
import java.io.IOException;

public class TopBar extends UiElement{

	private int vilcount, solcount; // amount of villagers and soldiers
	private final Icon pause;
	private final Icon fast;
	private final Icon slow;
	private final Icon sol;
	private final Icon vil;
	private final TextureInfo pauseTexture;
	private final TextureInfo playTexture;
	private int speed;

	// constructor
	TopBar(float x, float y, float width, float height, PointerInput pointer) throws IOException {
		super(new Vec2f(x, y), new Vec2f(width, height));
		pauseTexture = OpenGLUtils.loadTexture(ImageIO.read(TopBar.class.getResource("/icons/pause-button.png")));
		playTexture = OpenGLUtils.loadTexture(ImageIO.read(TopBar.class.getResource("/icons/play-button.png")));
		pause = new Icon(x + 120, y + 25, pauseTexture, 0.060f, pointer);
		fast = new Icon(x + 160, y + 30, "/icons/fast.png", 1.0f, pointer);
		slow = new Icon(x + 75, y + 30, "/icons/slow.png", 1.0f, pointer);
		sol = new Icon(x + 210, y + 17, "/icons/soldier.png", 1.0f, pointer);
		vil = new Icon(x + 10, y + 17, "/icons/villager.png", 1.0f, pointer);
	}

	// update the villager and soldier counts
	void updateCounts(int solcount, int vilcount) {
		this.solcount = solcount;
		this.vilcount = vilcount;
	}

	void updateSpeed(int speed) {
		this.speed = speed;
	}

	void init(PointerInput pointer, Runnable togglePause, Runnable upSpeed, Runnable downSpeed) {
		this.pause.setOnClick(pointer, () -> {
			pause.setTexture(pause.getTextureId() == pauseTexture.id ? playTexture.id : pauseTexture.id);
			togglePause.run();
		});
		this.fast.setOnClick(pointer, upSpeed);
		this.slow.setOnClick(pointer, downSpeed);
	}

	// render the topbar on the screen
	@Override
	public void render() {
		OpenGLUtils.drawFilledSquare(position, size, new Vec2f(0, 0), colour);
		vil.render();
		sol.render();
		slow.render();
		fast.render();
		pause.render();
		OpenGLUtils.drawText("Villagers", position.x + 5, position.y - 5);
		OpenGLUtils.drawText(vilcount + "", position.x + 25, position.y + 65);
		OpenGLUtils.drawText("Speed: " + (speed/10-2), position.x + 95, position.y + 60);
		OpenGLUtils.drawText("Soldiers", position.x + 200, position.y - 5);
		OpenGLUtils.drawText(solcount + "", position.x + 225, position.y + 65);
	}

	void destroy() {
		vil.destroy();
		sol.destroy();
		slow.destroy();
		fast.destroy();
		pause.destroy();
		OpenGLUtils.deleteTexture(pauseTexture.id);
		OpenGLUtils.deleteTexture(playTexture.id);
	}

}
