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

	// basic constructor
	public Wall() {
		super();
		resourceName = "Logs";
	}

	// checks the 4 sides of this wall to see if there are walls next to it. The
	// sprite is decided based on this.
	// this method has a boolean that stops the walls next to this wall to
	// retrigger checking the sides of this wall, which would create an infinite
	// loop of walls checking eachother again and again
	public void checkSides(boolean eerstekeer) {
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
	public void checkSides() {
		checkSides(true);
	}

	// called by villagers when they start building the wall.
	public boolean initialise(int x, int y, Item material, Level level) {
		boolean initGelukt = super.initialise(x, y, material, level);
		if (initGelukt && material.getName().equals("Logs"))
			checkSides();
		return initGelukt;

	}

	// decide the sprite for the wall, depending on the other 4 sides next to
	// the wall
	private void decideSprite() {
		sprite = null;
		sprite = Sprite.woodenWallVerticalTop;
		if (topHasWall) {
			if (bottomHasWall) {
				sprite = Sprite.woodenWallVerticalBoth;
				if (leftHasWall) {
					sprite = Sprite.woodenWallTLeft;
					if (rightHasWall) {
						sprite = Sprite.woodenWall4sides;
					}
				} else {
					if (rightHasWall) {
						sprite = Sprite.woodenWallTRight;
					}
				}
			} else {
				if (leftHasWall) {
					sprite = Sprite.woodenWallCornerTopLeft;
					if (rightHasWall) {
						sprite = Sprite.woodenWallTTop;
					}
				} else {
					if (rightHasWall) {
						sprite = Sprite.woodenWallCornerTopRight;
					}
				}
			}
		} else {
			if (bottomHasWall) {
				sprite = Sprite.woodenWallVerticalBottom;
				if (leftHasWall) {
					sprite = Sprite.woodenWallCornerBottomLeft;
					if (rightHasWall) {
						sprite = Sprite.woodenWallTBottom;
					}

				} else {
					if (rightHasWall) {
						sprite = Sprite.woodenWallCornerBottomRight;
					}
				}
			} else {
				if (leftHasWall) {
					sprite = Sprite.woodenWallHorizontalLeft;
					if (rightHasWall) {
						sprite = Sprite.woodenWallHorizontalBoth;
					}
				} else {
					if (rightHasWall) {
						sprite = Sprite.woodenWallHorizontalRight;
					}
				}
			}
		}

	}

}
