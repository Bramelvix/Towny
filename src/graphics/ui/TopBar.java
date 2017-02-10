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
	private static Image pauseimg, playimg; // the play and pause icons
	private static boolean visible = true; // is the topbar visible
	private static final Color COL = new Color(91, 94, 99, 110); // the blue-grey colour of the background
	private static final Font font = new Font("Dialog", Font.BOLD, 15); // the font
	private boolean paused; // is the game paused
	
	//constructor
	public TopBar(int x, int y) {
		this.x = x;
		this.y = y;
		width = 200;
		height = 82;
		init();
	}

	//intialise
	private void init() {
		try {
			solimg = ImageIO.read(Icon.class.getResource("/res/icons/soldier.png"));
			vilimg = ImageIO.read(Icon.class.getResource("/res/icons/villager.png"));
			pauseimg = ImageIO.read(Icon.class.getResource("/res/icons/pause.png"));
			playimg = ImageIO.read(Icon.class.getResource("/res/icons/play.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//update the villager and soldier counts
	public void updateCounts(int solcount, int vilcount) {
		this.solcount = solcount;
		this.vilcount = vilcount;
	}

	//render the topbar on the screen
	public void render(Graphics g) {
		if (visible) {
			g.setColor(COL);
			g.setFont(font);
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
			g.drawImage(vilimg, x + 10, y + 17, null);
			g.drawString("Villagers", x + 10, y + 12);
			g.drawString(vilcount + "", x + 30, y + 82);
			if (!paused) {
				g.drawImage(pauseimg, x + (width / 3) + 25, y + 30, null);
			} else {
				g.drawImage(playimg, x + (width / 3) + 25, y + 30, null);
			}
			g.drawImage(solimg, x + (width / 2) + 35, y + 17, null);
			g.drawString("Soldiers", x + (width / 2) + 35, y + 12);
			g.drawString(solcount + "", x + (width / 2) + 55, y + 82);
		}

	}

	//has the user clicked on the pause button
	public boolean clickedOnPause(Mouse mouse) {
		return (mouse.getTrueXPixels() >= x + (width / 3) + 25
				&& mouse.getTrueXPixels() <= x + (width / 3) + 25 + pauseimg.getWidth(null)
				&& mouse.getTrueYPixels() >= (y + 30) && mouse.getTrueYPixels() <= (y + 30) + pauseimg.getHeight(null)
				&& mouse.getClicked());
	}
	
	//toggle pausing
	public void togglePause() {
		if (paused) {
			paused = false;
		} else {
			paused = true;
		}
	}
	//getter
	public boolean getPaused() {
		return paused;
	}

}
