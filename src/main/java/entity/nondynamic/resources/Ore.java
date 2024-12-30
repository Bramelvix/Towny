package entity.nondynamic.resources;

import entity.dynamic.item.ItemHashtable;
import graphics.MultiSprite;
import graphics.Sprite;
import graphics.SpriteHashtable;
import graphics.StaticSprite;
import map.Level;

import java.util.ArrayList;
import java.util.List;

public class Ore extends Resource {
	private final Sprite originalSprite;

	// basic constructor
	public Ore(float x, float y, int z, Level level, OreType type) {
		super(x, y, z, level, false, type == OreType.STONE ? type.toString().toLowerCase() : type.toString().toLowerCase() + " ore", ItemHashtable.get(type.getItemId()).createInstance());
		this.originalSprite = SpriteHashtable.get(type.getSpriteId());
		setVisible(true);
	}

	// work method executed by the villager when mining
	@Override
	public boolean work() {
		if (super.work()) {
			resetSpritesAroundThis();
			return true;
		}

		return false;
	}

	private void resetSpritesAroundThis() {
		level.selectOre(getTileX() - 1, getTileY()).ifPresent(Ore::checkSides);
		level.selectOre(getTileX() + 1, getTileY()).ifPresent(Ore::checkSides);
		level.selectOre(getTileX(), getTileY() - 1).ifPresent(Ore::checkSides);
		level.selectOre(getTileX(), getTileY() + 1).ifPresent(Ore::checkSides);
		level.selectOre(getTileX() + 1, getTileY() - 1).ifPresent(Ore::checkSides);
		level.selectOre(getTileX() + 1, getTileY() + 1).ifPresent(Ore::checkSides);
		level.selectOre(getTileX() - 1, getTileY() + 1).ifPresent(Ore::checkSides);
		level.selectOre(getTileX() - 1, getTileY() - 1).ifPresent(Ore::checkSides);
	}

	public void checkSides() {
		boolean leftHasOre = level.selectOre(getTileX() - 1, getTileY()).isPresent();
		boolean rightHasOre = level.selectOre(getTileX() + 1, getTileY()).isPresent();
		boolean topHasOre = level.selectOre(getTileX(), getTileY() - 1).isPresent();
		boolean bottomHasOre = level.selectOre(getTileX(), getTileY() + 1).isPresent();

		boolean topRightHasOre = level.selectOre(getTileX() + 1, getTileY() - 1).isPresent();
		boolean bottomRightHasOre = level.selectOre(getTileX() + 1, getTileY() + 1).isPresent();
		boolean bottomLeftHasOre = level.selectOre(getTileX() - 1, getTileY() + 1).isPresent();
		boolean topLeftHasOre = level.selectOre(getTileX() - 1, getTileY() - 1).isPresent();
		setOreSprite(leftHasOre, rightHasOre, topHasOre, bottomHasOre, topRightHasOre, bottomRightHasOre, bottomLeftHasOre, topLeftHasOre);
	}

	private void setOreSprite(boolean leftHasOre, boolean rightHasOre, boolean topHasOre, boolean bottomHasOre, boolean topRightHasOre, boolean bottomRightHasOre, boolean bottomLeftHasOre, boolean topLeftHasOre) {
		List<Sprite> sprites = new ArrayList<>();
		sprites.add(originalSprite);

		//check if places are empty
		if (!topHasOre) sprites.add(SpriteHashtable.get(164)); //3
		if (!rightHasOre) sprites.add(SpriteHashtable.get(163)); //2
		if (!bottomHasOre) sprites.add(SpriteHashtable.get(165)); //4
		if (!leftHasOre) sprites.add(SpriteHashtable.get(162)); //1

		// top right corner
		if (!topHasOre && !rightHasOre) sprites.add(SpriteHashtable.get(167)); //6
		else if (!rightHasOre) sprites.add(SpriteHashtable.get(172)); //11
		else if (!topHasOre) sprites.add(SpriteHashtable.get(174)); //13
		else if (!topRightHasOre) sprites.add(SpriteHashtable.get(181)); //20

		// bottom right corner
		if (!bottomHasOre && !rightHasOre) sprites.add(SpriteHashtable.get(169)); //8
		else if (!rightHasOre) sprites.add(SpriteHashtable.get(173)); //12
		else if (!bottomHasOre) sprites.add(SpriteHashtable.get(177)); //16
		else if (!bottomRightHasOre) sprites.add(SpriteHashtable.get(179)); //18

		// bottom left corner
		if (!bottomHasOre && !leftHasOre) sprites.add(SpriteHashtable.get(168)); //7
		else if (!leftHasOre) sprites.add(SpriteHashtable.get(170)); //9
		else if (!bottomHasOre) sprites.add(SpriteHashtable.get(176)); //15
		else if (!bottomLeftHasOre) sprites.add(SpriteHashtable.get(178)); //17

		// top left corner
		if (!topHasOre && !leftHasOre) sprites.add(SpriteHashtable.get(166)); //5
		else if (!leftHasOre) sprites.add(SpriteHashtable.get(171)); //10
		else if (!topHasOre) sprites.add(SpriteHashtable.get(175)); //14
		else if (!topLeftHasOre) sprites.add(SpriteHashtable.get(180)); //19

		sprite = new MultiSprite(sprites.stream().map(StaticSprite.class::cast).toArray(StaticSprite[]::new));
	}

}
