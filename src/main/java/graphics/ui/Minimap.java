package graphics.ui;

import java.awt.Color;

import graphics.opengl.OpenGLUtils;
import map.Level;
import util.vectors.Vec2f;
import util.vectors.Vec3f;
import util.vectors.Vec4f;

class Minimap {

	private float width, height; // width and height of the minimap
	private float x, y; // x and y of the top left corner
	private int z;
	private static final Color COL = new Color(91, 94, 99, 110); // colour of the small rectangle on the minimap showing where the screen is
	private int xoff, yoff; // offset
	private int textureId;

	// constructor
	Minimap(int x, int y, Level map) {
		this.x = x;
		this.y = y;
		this.z = 0;
		width = 200;
		height = 200;
		init(map);
	}

	// intialise the image
	private void init(Level map) { //TODO fix entities rendering on the minimap
		int[] pixels = new int[(int)width * (int)height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels[x + y * (int)width] = map.getTile(y / 2, x / 2).sprite.getAvgColor();
			}
		}
		OpenGLUtils.deleteTexture(textureId);
		textureId = OpenGLUtils.loadTexture(pixels, (int)width, (int)height);
	}

	public void update(Level[] map, int z) {
		if (this.z != z) {
			this.z = z;
			init(map[z]);
		}
	}

	// render the minimap
	public void render() {
		float xLoc = (x + (xoff * 0.04225f)); //TODO fix this. Pulled these numbers out of my arse
		float yLoc = (y + (yoff * 0.04225f));
		OpenGLUtils.drawTexturedQuadScaled(new Vec2f(x,y), new Vec2f(width, height), new Vec2f(0,0), textureId);
		Vec4f outColor = new Vec4f(COL.getRed() / 255f, COL.getGreen() / 255f, COL.getBlue() / 255f, COL.getAlpha() / 255f);
		OpenGLUtils.drawFilledSquare(new Vec2f(xLoc, yLoc), new Vec2f(62.5f, 35.15625f), new Vec2f(0,0), outColor);
	}

	// setter
	void setOffset(int x, int y) {
		xoff = x;
		yoff = y;
	}

}
