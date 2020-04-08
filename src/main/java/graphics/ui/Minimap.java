package graphics.ui;


import graphics.opengl.OpenGLUtils;
import main.Game;
import map.Level;
import map.Tile;
import util.vectors.Vec2f;

class Minimap extends UiElement{

	private int z;
	private float xoff, yoff; // offset
	private int textureId;
	private Vec2f filledRectSize;
	private Vec2f filledRectLoc;

	// constructor
	Minimap(int x, int y, Level map) {
		super(new Vec2f(x, y), new Vec2f(200, 200));
		filledRectSize = new Vec2f((float) Game.width / Tile.SIZE * 2f, (float) Game.height / Tile.SIZE * 2f);
		filledRectLoc = new Vec2f(0, 0);
		this.z = 0;
		init(map);
	}

	// initialise the image
	private void init(Level map) {
		int[] pixels = new int[(int)size.x * (int)size.y];
		for (int x = 0; x < size.x; x+=2) {
			for (int y = 0; y < size.y; y+=2) {
				int colour = map.getTile(x/2, y/2).getAvgColour();
				pixels[x + y * (int) size.x] = colour;
				pixels[(x + 1) + y * (int) size.x] = colour;
				pixels[x + (y + 1) * (int) size.x] = colour;
				pixels[(x + 1) + (y + 1) * (int) size.x] = colour;
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
		filledRectLoc.x = position.x + (xoff * (1f / (Game.width / filledRectSize.x)));
		filledRectLoc.y = position.y + (yoff * (1f / (Game.height / filledRectSize.y)));
		OpenGLUtils.drawTexturedQuadScaled(position, new Vec2f(size.x, size.y), new Vec2f(0,0), textureId);
		OpenGLUtils.drawFilledSquare(filledRectLoc, filledRectSize, new Vec2f(0,0), colour);
	}

	// setter
	void setOffset(float x, float y) {
		xoff = x;
		yoff = y;
	}

}
