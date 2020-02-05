package graphics.ui;


import java.awt.Color;
import java.awt.Graphics;

public class VillagerListScreen {
	
	private int x, y;
	private int width = 500;
	private int height = 500;
	private boolean visible;
	private Color colour = new Color(91, 94, 99, 230);
	
	public void Render(Graphics g) {
		if (visible) {
			g.setColor(colour);
			g.fillRect(x, y, width, height);
		}
	}

}
