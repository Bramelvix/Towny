package graphics.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.ui.icon.Icon;
import input.Mouse;

public class TopBar {
	private int x, y; // x and y of the top left corner
	private int width, height; // width and height
	private int vilcount, solcount; // amount of villagers and soldiers
	private static Image solimg, vilimg; // the icon of the soldier and villager
	private static Image pauseimg, playimg, fastimg, slowimg; // the play and
																// pause icons
	private static boolean visible = true; // is the topbar visible
	private static final Color COL = new Color(91, 94, 99, 110); // the
																	// colour of
																	// the
																	// background
	private static final Font font = new Font("Dialog", Font.BOLD, 15);
	private boolean paused; // is the game paused
	private byte speed = 6;

	// constructor
	public TopBar(int x, int y) {
		this.x = x;
		this.y = y;
		width = 270;
		height = 82;
		init();
	}

	// intialise
	private void init() {
		try {
			solimg = ImageIO.read(Icon.class.getResource("/res/icons/soldier.png"));
			vilimg = ImageIO.read(Icon.class.getResource("/res/icons/villager.png"));
			pauseimg = ImageIO.read(Icon.class.getResource("/res/icons/pause.png"));
			slowimg = ImageIO.read(Icon.class.getResource("/res/icons/slow.png"));
			fastimg = ImageIO.read(Icon.class.getResource("/res/icons/fast.png"));
			playimg = ImageIO.read(Icon.class.getResource("/res/icons/play.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// update the villager and soldier counts
	public void updateCounts(int solcount, int vilcount) {
		this.solcount = solcount;
		this.vilcount = vilcount;
	}

	public void update(Mouse mouse) {
		if (clickedOnPause(mouse)) {
			togglePause();
			return;
		}
		if (clickedOnFast(mouse)) {
			upSpeed();
			return;
		}
		if (clickedOnSlow(mouse)) {
			downSpeed();
			return;
		}
	}

	public byte getSpeed() {
		if (speed == 0) {
			return 6;
		}
		return speed;
	}

	private void upSpeed() {
		if (speed < 9)
			speed++;
	}

	private void downSpeed() {
		if (speed > 3)
			speed--;
	}

	// render the topbar on the screen
	public void render(Graphics g) {
		if (visible) {
			g.setColor(COL);
			g.setFont(font);
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
			g.drawImage(vilimg, x + 10, y + 17, null);
			g.drawString("Villagers", x + 10, y + 12);
			g.drawString(vilcount + "", x + 30, y + 82);
			g.drawString("Speed: " + (speed - 2), x + 105, y + 25);
			if (!paused) {
				g.drawImage(pauseimg, x + (width / 3) + 35, y + 30, null);
			} else {
				g.drawImage(playimg, x + (width / 3) + 35, y + 30, null);
			}
			g.drawImage(slowimg, x + (width >> 2) + 10, y + 35, null);
			g.drawImage(fastimg, x + (width >> 1) + 20, y + 35, null);
			g.drawImage(solimg, x + (width >> 1) + 70, y + 17, null);
			g.drawString("Soldiers", x + (width >> 1) + 70, y + 12);
			g.drawString(solcount + "", x + (width >> 1) + 90, y + 82);
		}

	}

	// has the user clicked on the pause button
	private boolean clickedOnPause(Mouse mouse) {
		return (mouse.getTrueXPixels() >= x + (width / 3) + 35
				&& mouse.getTrueXPixels() <= x + (width / 3) + 35 + pauseimg.getWidth(null)
				&& mouse.getTrueYPixels() >= (y + 30) && mouse.getTrueYPixels() <= (y + 30) + pauseimg.getHeight(null)
				&& mouse.getClicked());
	}

	private boolean clickedOnFast(Mouse mouse) {
		return (mouse.getTrueXPixels() >= x + (width >> 1) + 20
				&& mouse.getTrueXPixels() <= x + (width >> 1) + 20 + fastimg.getWidth(null)
				&& mouse.getTrueYPixels() >= (y + 35) && mouse.getTrueYPixels() <= (y + 35) + fastimg.getHeight(null)
				&& mouse.getClicked());
	}

	private boolean clickedOnSlow(Mouse mouse) {
		return (mouse.getTrueXPixels() >= x + (width >> 2) + 10
				&& mouse.getTrueXPixels() <= x + (width >> 2) + 10 + slowimg.getWidth(null)
				&& mouse.getTrueYPixels() >= (y + 35) && mouse.getTrueYPixels() <= (y + 35) + slowimg.getHeight(null)
				&& mouse.getClicked());
	}

	// toggle pausing
	private void togglePause() {
		if (paused) {
			paused = false;
			speed = 6;
		} else {
			paused = true;
			speed = 0;
		}
	}

	// getter
	public boolean isPaused() {
		return paused;
	}

}
