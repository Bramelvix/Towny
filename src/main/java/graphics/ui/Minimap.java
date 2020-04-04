package graphics.ui;


import graphics.opengl.OpenGLUtils;
import map.Level;
import util.vectors.Vec2f;

class Minimap extends UiElement{

	private int z;
	private float xoff, yoff; // offset
	private int textureId;

	// constructor
	Minimap(int x, int y, Level map) {
		super(new Vec2f(x, y), new Vec2f(200, 200));
		this.z = 0;
		init(map);
	}

	// initialise the image
	private void init(Level map) {
		int[] pixels = new int[(int)size.x * (int)size.y];
		for (int x = 0; x < size.x; x+=2) {
			for (int y = 0; y < size.y; y+=2) {
				int colour = map.getTile(x/2, y/2).getAvgColour();
				pixels[x + y * (int)size.x] = colour;
				pixels[(x + 1) + y * (int)size.x] = colour;
				pixels[x + (y + 1) * (int)size.x] = colour;
				pixels[(x + 1) + (y + 1) * (int)size.x] = colour;
			}
		}
		OpenGLUtils.deleteTexture(textureId);
		textureId = OpenGLUtils.loadTexture(pixels, (int)size.x, (int)size.y).id;
	}

	public void update(Level[] map, int z) {
		if (this.z != z) {
			this.z = z;
			init(map[z]);
		}
	}

	// render the minimap
	@Override
	public void render() {
		float xLoc = (position.x + (xoff * 0.04225f)); //TODO fix this. Pulled these numbers out of my arse
		float yLoc = (position.y + (yoff * 0.04225f));
		OpenGLUtils.drawTexturedQuadScaled(position, new Vec2f(size.x, size.y), new Vec2f(0,0), textureId);
		OpenGLUtils.drawFilledSquare(new Vec2f(xLoc, yLoc), new Vec2f(62.5f, 35.15625f), new Vec2f(0,0), colour);
	}

	// setter
	void setOffset(float x, float y) {
		xoff = x;
		yoff = y;
	}

}
