package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	public boolean up, down, right, left;
	private boolean[] keys = new boolean[120];

	public void update() {
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];

	}

	public void keyPressed(KeyEvent arg0) {
		keys[arg0.getKeyCode()] = true;
		update();

	}

	public void keyReleased(KeyEvent arg0) {
		keys[arg0.getKeyCode()] = false;
		update();

	}

	public void keyTyped(KeyEvent arg0) {

	}

}
