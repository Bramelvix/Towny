package graphics.ui;

import graphics.opengl.OpenGLUtils;
import graphics.ui.icon.Icon;
import input.PointerInput;
import main.Game;
import util.vectors.Vec2f;
import util.vectors.Vec4f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class TopBar {

	private final int x;
	private final int y; // x and y of the top left corner
	private final int width;
	private final int height; // width and height
	private int vilcount, solcount; // amount of villagers and soldiers
	private final Icon pause;
	private final Icon play;
	private final Icon fast;
	private final Icon slow;
	private final Icon sol;
	private final Icon vil;
	private static final float r = 0.3568f, g = 0.3686f, b = 0.3882f, a = 0.43137f; //colour for background
	private byte speed = 6;

	// constructor
	TopBar(int x, int y, int width, int height, PointerInput pointer) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		pause = new Icon(x + 120, y + 25, "/icons/pause-button.png", 0.060f, pointer);
		play = new Icon(x + 120, y + 25, "/icons/play-button.png", 0.060f, pointer);
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

	public void update(PointerInput pointer) {
		play.update(pointer);
		pause.update(pointer);
		slow.update(pointer);
		fast.update(pointer);
		sol.update(pointer);
		vil.update(pointer);
		if (clickedOnPause(pointer)) {
			togglePause();
		} else if (clickedOnFast(pointer)) {
			upSpeed();
		} else if (clickedOnSlow(pointer)) {
			downSpeed();
		}
	}

	byte getSpeed() {
		if (speed == 0) {
			return 6;
		}
		return speed;
	}

	private void upSpeed() {
		if (speed < 9) {
			speed++;
		}
	}

	private void downSpeed() {
		if (speed > 3) {
			speed--;
		}
	}

	// render the topbar on the screen
	public void render() {
		OpenGLUtils.drawFilledSquare(new Vec2f(x, y), new Vec2f(width, height), new Vec2f(0, 0), new Vec4f(r, g, b, a));
		vil.render();
		sol.render();
		slow.render();
		fast.render();
		if (!Game.paused) {
			pause.render();
		} else {
			play.render();
		}
		OpenGLUtils.drawText("Villagers", x +5, y -5);
		OpenGLUtils.drawText(vilcount + "", x + 25, y + 65);
		OpenGLUtils.drawText("Speed: " + (speed - 2), x + 95, y + 60);
		OpenGLUtils.drawText("Soldiers", x + 200, y - 5);
		OpenGLUtils.drawText(solcount + "", x + 225, y + 65);
	}

	// has the user clicked on the pause button
	private boolean clickedOnPause(PointerInput pointer) {
		return (pointer.wasPressed(GLFW_MOUSE_BUTTON_LEFT) && (Game.paused ? play.hoverOn() : pause.hoverOn()));
	}

	private boolean clickedOnFast(PointerInput pointer) {
		return fast.hoverOn() && pointer.wasPressed(GLFW_MOUSE_BUTTON_LEFT);
	}

	private boolean clickedOnSlow(PointerInput pointer) {
		return slow.hoverOn() && pointer.wasPressed(GLFW_MOUSE_BUTTON_LEFT);
	}

	// toggle pausing
	private void togglePause() {
		if (Game.paused) {
			Game.paused = false;
			speed = 6;
		} else {
			Game.paused = true;
			speed = 2;
		}
	}

}
