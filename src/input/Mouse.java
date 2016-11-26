package input;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import main.Game;

public class Mouse implements MouseInputListener {
	private Game game;
	private int mouseX = -1;
	private int mouseY = -1;
	private int mouseB = -1;
	private int mouseTileX = -1;
	private int mouseTileY = -1;

	public Mouse(Game game) {
		this.game = game;
	}

	public int getX() {
		return mouseX;
	}

	public int getY() {
		return mouseY;
	}

	public int getButton() {
		return mouseB;
	}

	public int getTileY() {
		return mouseTileY;
	}

	public int getTileX() {
		return mouseTileX;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		mouseB = arg0.getButton();

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouseB = -1;

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		mouseX = arg0.getX() / 3 + game.xScroll;
		mouseY = arg0.getY() / 3 + game.yScroll;
		mouseTileX = mouseX >> 4;
		mouseTileY = mouseY >> 4;

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mouseX = arg0.getX() / 3 + game.xScroll;
		mouseY = arg0.getY() / 3 + game.yScroll;
		mouseTileX = mouseX >> 4;
		mouseTileY = mouseY >> 4;

	}

}
