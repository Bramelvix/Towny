package ui;

import events.PointerClickEvent;
import events.PointerMoveEvent;
import events.Subscription;
import graphics.opengl.OpenGLUtils;
import input.PointerInput;
import map.Level;
import map.Tile;
import util.vectors.Vec2f;
import util.vectors.Vec4f;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static events.EventListener.onlyWhen;

//the green or red outline used to select where to build things
public class BuildOutline {

	private static final Vec4f buildable = new Vec4f(0.3058f, 0.9568f, 0.2588f, 0.8235f); // green
	private static final Vec4f notbuildable = new Vec4f(0.9568f, 0.2588f, 0.2588f, 0.8235f); // red
	private float buildSquareXS; // x coord of the start in the game world
	private float buildSquareYS; // y coord of the start in the game world
	private float buildSquareXSDraw; // x coord of the start on the screen
	private float buildSquareYSDraw; // y coord of the start on the screen
	private float buildSquareXE; // x coord of the end in the game world
	private float buildSquareYE; // y coord of the end in the game world
	private static final float WIDTH = Tile.SIZE; // width of a square
	private int squarewidth; // width of the outline in squares
	private int squareheight; // height of the outline in squares
	private boolean visible; // is the outline visible
	private final Level[] levels; // the map is needed to decide if a square is empty
	private boolean lockedSize = false;
	private int z = 0;
	private float xScroll;
	private float yScroll;
	private Subscription onClick;

	// rendering the outline
	public void render() {
		if (!visible) {
			return;
		}

		if (lockedSize || buildSquareXE == 0 && buildSquareYE == 0) {
			OpenGLUtils.buildOutlineDraw(new Vec2f(buildSquareXSDraw, buildSquareYSDraw), WIDTH,
					notBuildable(buildSquareXS, buildSquareYS, z) ? notbuildable : buildable
			);
			return;
		}
		if (squarewidth > squareheight) {
			if (buildSquareXSDraw < buildSquareXE) { // START LEFT OF END == DRAG TO RIGHT
				for (int i = 0; i < squarewidth; i++) {
					OpenGLUtils.buildOutlineDraw(new Vec2f(buildSquareXSDraw + i, buildSquareYSDraw), WIDTH,
							notBuildable(buildSquareXS + i, buildSquareYS, z) ? notbuildable : buildable
					);
				}
				return;
			}
			// START RIGHT OF END == DRAG TO LEFT
			for (int i = 0; i < squarewidth; i++) {
				OpenGLUtils.buildOutlineDraw(new Vec2f(buildSquareXSDraw - squarewidth - 1 + i, buildSquareYSDraw), WIDTH,
						notBuildable(buildSquareXS - squarewidth - 1 + i, buildSquareYS, z) ? notbuildable : buildable
				);
			}
			return;
		}

		if (buildSquareYSDraw < buildSquareYE) { // START ABOVE END == DRAG DOWN
			for (int i = 0; i < squareheight; i++) {
				OpenGLUtils.buildOutlineDraw(new Vec2f(buildSquareXSDraw, buildSquareYSDraw + i), squareheight - i,
						notBuildable(buildSquareXS, buildSquareYS + i, z) ? notbuildable : buildable
				);
			}
			return;
		}
		// START BELOW END == DRAG UP
		for (int i = 0; i < squareheight; i++) {
			OpenGLUtils.buildOutlineDraw(new Vec2f(buildSquareXSDraw, buildSquareYSDraw - squareheight - 1 + i), WIDTH,
					notBuildable(buildSquareXS, buildSquareYS - squareheight - 1 + i, z) ? notbuildable : buildable
			);
		}
	}

	// is the tile empty
	private boolean notBuildable(float x, float y, int z) {
		return !levels[z].tileIsEmpty((int) (x / Tile.SIZE), (int) (y / Tile.SIZE));

	}

	// getters
	float[][] getSquareCoords() {
		float[][] coords;
		if (buildSquareXE == 0 && buildSquareYE == 0) {
			coords = new float[1][2];
			coords[0][0] = buildSquareXS;
			coords[0][1] = buildSquareYS;
			return coords;
		}

		if (squarewidth > squareheight) {
			if (buildSquareXSDraw < buildSquareXE) { // START LEFT OF END == DRAG RIGHT
				coords = new float[squarewidth][2];
				for (int i = 0; i < squarewidth; i++) {
					coords[i][0] = buildSquareXS + (i * Tile.SIZE);
					coords[i][1] = buildSquareYS;
				}
				return coords;
			}
			// START RIGHT OF END == DRAG LEFT
			coords = new float[squarewidth][2];
			for (int i = 0; i < squarewidth; i++) {
				coords[i][0] = (buildSquareXS - (WIDTH * (squarewidth - 1))) + (i * Tile.SIZE);
				coords[i][1] = buildSquareYS;
			}
			return coords;
		}

		if (buildSquareYSDraw < buildSquareYE) { // START ABOVE END == DRAG DOWN
			coords = new float[squareheight][2];
			for (int i = 0; i < squareheight; i++) {
				coords[i][0] = buildSquareXS;
				coords[i][1] = buildSquareYS + (i * Tile.SIZE);
			}
			return coords;
		}

		// START BELOW END == DRAG UP
		coords = new float[squareheight][2];
		for (int i = 0; i < squareheight; i++) {
			coords[i][0] = buildSquareXS;
			coords[i][1] = buildSquareYS - (WIDTH * (squareheight - 1)) + (i * Tile.SIZE);
		}
		return coords;

	}

	boolean isVisible() {
		return visible;
	}

	private void update(PointerMoveEvent e) {
		buildSquareXS = (float) ((int) (e.x + xScroll) / (int) Tile.SIZE * (int) Tile.SIZE); //this is on purpose ;)))
		buildSquareXSDraw = buildSquareXS - xScroll;
		buildSquareYS = (float) ((int) (e.y + yScroll) / (int) Tile.SIZE * (int) Tile.SIZE);
		buildSquareYSDraw = buildSquareYS - yScroll;

		if (visible) {
			squarewidth = 1;
			squareheight = 1;
			buildSquareXE = 0;
			buildSquareYE = 0;
		}
	}

	// update the outline
	public void update(int z, float xScroll, float yScroll) {
		this.z = z;
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}

	// constructor
	BuildOutline(Level[] levels, PointerInput pointer) {
		this.levels = levels;
		pointer.on(PointerInput.EType.MOVE, this::update);
	}

	// show the outline
	void show(int z, float xScroll, float yScroll, boolean lockedSize, PointerInput pointer, Consumer<float[][]> consumer, Predicate<PointerClickEvent> preReq) {
		if (!visible) {
			visible = true;
			update(z, xScroll, yScroll);
			squarewidth = 1;
			squareheight = 1;
			this.lockedSize = lockedSize;
			onClick = pointer.on(PointerInput.EType.PRESSED, onlyWhen(preReq, event -> consumer.accept(getSquareCoords())));
		}
	}

	// hide the outline
	void remove() {
		visible = false;
		onClick.unsubscribe();
	}

}
