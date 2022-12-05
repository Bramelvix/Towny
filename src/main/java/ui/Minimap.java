package ui;


import events.PointerClickEvent;
import events.PointerDragEvent;
import events.Subscription;
import graphics.opengl.OpenGLUtils;
import input.PointerInput;
import main.Game;
import map.Level;
import map.Tile;
import util.vectors.Vec2f;

import static events.EventListener.onlyWhen;

class Minimap extends UiElement {

	private int z;
	private float xoff;
	private float yoff; // offset
	private int textureId;
	private final Vec2f filledRectSize;
	private final Vec2f filledRectLoc;
	private final Game game;
	private final Subscription mousePressedSubscription;
	private final Subscription mouseDraggedSubscription;

	// constructor
	Minimap(int x, int y, Level map, Game game) {
		super(new Vec2f(x, y), new Vec2f(200, 200));
		this.game = game;
		this.z = 0;
		filledRectSize = new Vec2f(Game.WIDTH / Tile.SIZE * 2f, Game.HEIGHT / Tile.SIZE * 2f);
		filledRectLoc = new Vec2f(0, 0);
		init(map);
		mousePressedSubscription = PointerInput.getInstance().on(PointerInput.EType.PRESSED, onlyWhen(this::mouseOver, this::setScroll));
		mouseDraggedSubscription = PointerInput.getInstance().on(PointerInput.EType.DRAG, onlyWhen(this::mouseOver, this::setScroll));
	}

	// initialise the image
	private void init(Level map) {
		int[] pixels = new int[(int) size.x * (int) size.y];
		for (int x = 0; x < size.x; x += 2) {
			for (int y = 0; y < size.y; y += 2) {
				int colour = map.getTile(x / 2, y / 2).getAvgColour();
				pixels[x + y * (int) size.x] = colour;
				pixels[(x + 1) + y * (int) size.x] = colour;
				pixels[x + (y + 1) * (int) size.x] = colour;
				pixels[(x + 1) + (y + 1) * (int) size.x] = colour;
			}
		}
		OpenGLUtils.deleteTexture(textureId);
		textureId = OpenGLUtils.loadTexture(pixels, (int) size.x, (int) size.y).id();
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
		filledRectLoc.x = position.x + (xoff * (1f / (Game.WIDTH / filledRectSize.x)));
		filledRectLoc.y = position.y + (yoff * (1f / (Game.HEIGHT / filledRectSize.y)));
		OpenGLUtils.drawTexturedQuadScaled(position, new Vec2f(size.x, size.y), textureId);
		OpenGLUtils.drawFilledSquare(filledRectLoc, filledRectSize, colour);
	}

	// setter
	void update() {
		xoff = game.getxScroll();
		yoff = game.getyScroll();
	}

	void destroy() {
		mousePressedSubscription.unsubscribe();
		mouseDraggedSubscription.unsubscribe();
		OpenGLUtils.deleteTexture(textureId);
	}

	private Vec2f minimapToIngameCoords(float x, float y) {
		Vec2f coordsOnMinimap = new Vec2f(x - getX(), y - getY());
		return new Vec2f(coordsOnMinimap.x / getWidth() * Game.LEVEL_SIZE * Tile.SIZE, coordsOnMinimap.y / getHeight() * Game.LEVEL_SIZE * Tile.SIZE);
	}

	private void setScrollFromIngameCoords(Vec2f coords) {
		game.setXScroll(coords.x - Game.WIDTH/2f);
		game.setYScroll(coords.y - Game.HEIGHT/2f);
	}

	private void setScroll(PointerClickEvent event) {
		setScrollFromIngameCoords(minimapToIngameCoords((float) event.x, (float) event.y));
	}

	private void setScroll(PointerDragEvent event) {
		setScrollFromIngameCoords(minimapToIngameCoords((float) event.x, (float) event.y));
	}

	private boolean mouseOver(PointerClickEvent event) {
		return super.mouseOver(event.x, event.y);
	}

	private boolean mouseOver(PointerDragEvent event) {
		return super.mouseOver(event.x, event.y);
	}
}
