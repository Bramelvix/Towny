package graphics.ui;


import graphics.opengl.OpenGLUtils;
import map.Level;
import util.vectors.Vec2f;
import util.vectors.Vec4f;

class Minimap {

	private final Vec2f position;
	private final float width;
	private final float height; // width and height of the minimap
	private int z;
	private static final Vec4f colour = new Vec4f(0.3568f, 0.3686f, 0.3882f, 0.43137f); //colour for background
	private float xoff, yoff; // offset
	private int textureId;

	// constructor
	Minimap(int x, int y, Level map) {
		this.position = new Vec2f(x, y);
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
		float xLoc = (position.x + (xoff * 0.04225f)); //TODO fix this. Pulled these numbers out of my arse
		float yLoc = (position.y + (yoff * 0.04225f));
		OpenGLUtils.drawTexturedQuadScaled(position, new Vec2f(width, height), new Vec2f(0,0), textureId);
		OpenGLUtils.drawFilledSquare(new Vec2f(xLoc, yLoc), new Vec2f(62.5f, 35.15625f), new Vec2f(0,0), colour);
	}

	// setter
	void setOffset(float x, float y) {
		xoff = x;
		yoff = y;
	}

}
