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
	private int trueX = -1;
	private int trueY = -1;
	private int trueXpixels = -1;
	private int trueYpixels = -1;

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
		trueX = arg0.getX() /3;
		trueY = arg0.getY() / 3;
		trueXpixels = arg0.getX();
		trueYpixels = arg0.getY();
		mouseX = trueX + game.xScroll;
		mouseY = trueY + game.yScroll;
		mouseTileX = mouseX >> 4;
		mouseTileY = mouseY >> 4;

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		trueX = arg0.getX() /3;
		trueY = arg0.getY() / 3;
		mouseX = trueX + game.xScroll;
		mouseY = trueY + game.yScroll;
		trueXpixels = arg0.getX();
		trueYpixels = arg0.getY();
		mouseTileX = mouseX >> 4;
		mouseTileY = mouseY >> 4;

	}
	public int getTrueX() {
		return trueX;
	}
	public int getTrueY() {
		return trueY;
	}
	public int getTrueXPixels() {
		return trueXpixels;
	}
	public int getTrueYPixels() {
		return trueYpixels;
	}

}
