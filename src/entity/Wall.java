package entity;

import entity.item.Item;
import graphics.Sprite;
import map.Map;
import sound.Sound;

public class Wall extends Entity implements Buildable {
	public int condition = 0;
	public boolean initialised = false;
	private boolean topHasWall;
	private boolean bottomHasWall;
	private boolean leftHasWall;
	private boolean rightHasWall;

	public Wall(int x, int y) {
		super(x, y);
		setVisible(false);
		condition = 0;
	}

	public void checkSides(boolean eerstekeer) {
		Wall left = (level.getWallOn((x - 16), y));
		Wall right = (level.getWallOn((x + 16), y));
		Wall up = (level.getWallOn(x, (y - 16)));
		Wall down = (level.getWallOn(x, (y + 16)));
		leftHasWall = false;
		rightHasWall = false;
		topHasWall = false;
		bottomHasWall = false;
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

	public void checkSides() {
		checkSides(true);
	}

	public boolean initialise(Item material, Map level) {
		this.level = level;
		if (material == null) {
			return false;
		}
		if (material.quantity > 0) {
			material.quantity--;
		}
		level.entities.add(this);
		if (material.getName().equals("Logs")) {
			checkSides();
		}
		initialised = true;
		return true;

	}

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

	public boolean build() {
		if (initialised) {
			if (condition < 100) {
				if (condition == 1)
					Sound.speelGeluid(Sound.drill);
				condition++;
				return false;
			} else {
				this.setVisible(true);
				level.getTile(x >> 4, y >> 4).setSolid(true);
				return true;
			}

		}
		return false;
	}

}
