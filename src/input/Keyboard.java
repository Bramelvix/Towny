package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	//the keyboard class is used to register keys
	private static boolean[] keys = new boolean[120];

	public void keyPressed(KeyEvent arg0) {
		keys[arg0.getKeyCode()] = true;

	}
	public static boolean getKeyPressed(int e) {
		return keys[e];
	}
	public static boolean getKeyReleased(int e) {
		return !getKeyPressed(e);
	}

	public void keyReleased(KeyEvent arg0) {
		keys[arg0.getKeyCode()] = false;

	}

	public void keyTyped(KeyEvent arg0) {

	}

}
