package entity;

import entity.item.Item;
import graphics.Sprite;
import map.Level;
import map.Tile;

public class Wall extends BuildAbleObject {
	private boolean topHasWall = false; // is there a wall above this wall
	private boolean bottomHasWall = false; // is there a wall below this wall
	private boolean leftHasWall = false; // is there a wall to the left of this
											// wall
	private boolean rightHasWall = false; // is there a wall to the right of
											// this wall
	private Sprite[] sprites;
	private boolean door;

	// basic constructor
	public Wall(WallType type) {
		this(type, false);
	}

	private void decideSprites(WallType type, boolean door) {
		String name = "";
		switch (type) {
		case WOOD:
			name = name.concat("wooden ");
			if (door) {
				sprites = Sprite.WOODDOORSPRITES;
			} else {
				sprites = Sprite.WOODWALLSPRITES;
			}
			break;
		case STONE:
			name = name.concat("stone ");
			if (door) {
				sprites = Sprite.STONEDOORSPRITES;
			} else {
				sprites = Sprite.STONEWALLSPRITES;
			}
			break;
		}
		name = name.concat(door ? "door" : "wall");
		setName(name);
	}

	public Wall(WallType type, boolean door) {
		super();
		this.door = door;
		decideSprites(type, door);
	}

	// checks the 4 sides of this wall to see if there are walls next to it. The
	// sprite is decided based on this.
	// this method has a boolean that stops the walls next to this wall to
	// retrigger checking the sides of this wall, which would create an infinite
	// loop of walls checking eachother again and again
	private void checkSides(boolean eerstekeer) {
		Wall left = level.getWallOn((x - Tile.SIZE), y);
		Wall right = level.getWallOn((x + Tile.SIZE), y);
		Wall up = level.getWallOn(x, (y - Tile.SIZE));
		Wall down = level.getWallOn(x, (y + Tile.SIZE));
		if (left != null) {
			leftHasWall = true;
			if (eerstekeer)
				left.checkSides(false);
		}
		if (right != null) {
			rightHasWall = true;
			if (eerstekeer)
				right.checkSides(false);
		}
		if (up != null) {
			topHasWall = true;
			if (eerstekeer)
				up.checkSides(false);
		}
		if (down != null) {
			bottomHasWall = true;
			if (eerstekeer)
				down.checkSides(false);
		}
		decideSprite();

	}

	// Checksides method for the walls around this wall
	private void checkSides() {
		checkSides(true);
	}

	// called by villagers when they start building the wall.
	public boolean initialise(int x, int y, Item material, Level level) {
		boolean initGelukt = super.initialise(x, y, material, level);
		if (initGelukt) {
			checkSides();
		}
		if (door) {
			setOpened(true);
		}
		return initGelukt;

	}

	// decide the sprite for the wall, depending on the other 4 sides next to
	// the wall
	private void decideSprite() {
		sprite = sprites[0];
		if (topHasWall) {
			if (bottomHasWall) {
				sprite = sprites[1];
				if (leftHasWall) {
					sprite = sprites[2];
					if (rightHasWall) {
						sprite = sprites[3];
					}
				} else {
					if (rightHasWall) {
						sprite = sprites[4];
					}
				}
			} else {
				if (leftHasWall) {
					sprite = sprites[5];
					if (rightHasWall) {
						sprite = sprites[6];
					}
				} else {
					if (rightHasWall) {
						sprite = sprites[7];
					}
				}
			}
		} else {
			if (bottomHasWall) {
				sprite = sprites[8];
				if (leftHasWall) {
					sprite = sprites[9];
					if (rightHasWall) {
						sprite = sprites[10];
					}

				} else {
					if (rightHasWall) {
						sprite = sprites[11];
					}
				}
			} else {
				if (leftHasWall) {
					sprite = sprites[12];
					if (rightHasWall) {
						sprite = sprites[13];
					}
				} else {
					if (rightHasWall) {
						sprite = sprites[14];
					}
				}
			}
		}

	}

}
