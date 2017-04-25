package entity.workstations;

import graphics.Screen;
import graphics.Sprite;

public class Furnace extends Workstation {
	public Furnace(int x, int y) {
		super(x, y);
	}

	private static final Sprite furnaceOn1 = Sprite.furnaceOn1;
	private static final Sprite furnaceOn2 = Sprite.furnaceOn2;
	private boolean running = false;
	private byte animationcounter = 60;


	public void render(Screen s) {
		if (!running) {
			s.renderSprite(x, y, Sprite.furnaceOff);
		}
	}

}
