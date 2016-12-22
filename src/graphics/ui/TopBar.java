package graphics.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.ui.icon.Icon;

public class TopBar {
	private int x, y;
	private int width, height;
	private int vilcount, solcount;
	private static Image solimg, vilimg;
	private static boolean visible = true;
	private static final Color COL = new Color(91, 94, 99, 110);
	private static final Font font = new Font("Dialog", Font.BOLD, 15);

	public TopBar(int x, int y) {
		this.x = x;
		this.y = y;
		width = 160;
		height = 82;
		init();
	}

	private void init() {
		try {
			solimg = ImageIO.read(Icon.class.getResource("/res/icons/soldier.png"));
			vilimg = ImageIO.read(Icon.class.getResource("/res/icons/villager.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateCounts(int solcount, int vilcount) {
		this.solcount = solcount;
		this.vilcount = vilcount;
	}

	public void render(Graphics g) {
		if (visible) {
			g.setColor(COL);
			g.setFont(font);
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
			g.drawImage(vilimg, x + 10, y + 17, null);
			g.drawString("Villagers", x + 10, y + 12);
			g.drawString(vilcount + "", x + 30, y + 82);
			g.drawImage(solimg, x + (width / 2) + 15, y + 17, null);
			g.drawString("Soldiers", x + (width / 2) + 15, y + 12);
			g.drawString(solcount + "", x + (width / 2) + 35, y + 82);
		}

	}

}
