package entity.nonDynamic.resources;

import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import graphics.MultiSprite;
import graphics.Sprite;
import graphics.SpriteHashtable;
import map.Level;
import sound.Sound;
import util.vectors.Vec2f;

import java.util.ArrayList;
import java.util.List;

public class Ore extends Resource {

	private byte mined = 100; // mined percentage (100 = unfinished / 0 = finished)
	private Item minedItem; // Item dropped when the ore is mined
	private Sprite originalSprite;

	// basic constructor
	public Ore(float x, float y, int z, Level level, OreType type) {
		super(x, y, z, level);
		decideSprite(type);
		setTransparent(false);
		setVisible(true);
	}

	// decide the sprite for the ore
	private void decideSprite(OreType type) {
		String name = type == OreType.STONE ? type.toString().toLowerCase() : type.toString().toLowerCase() + " ore";
		setOre(name, SpriteHashtable.get(type.getSpriteId()), ItemHashtable.get(type.getItemSpriteId(), getX(), getY(), this.z));
	}

	private void setOre(String name, Sprite oreSprite, Item item) {
		setName(name);
		this.originalSprite = oreSprite;
		this.minedItem = item;
	}

	// work method executed by the villager when mining
	public boolean work() {
		if (mined > 0) {
			if (mined % 20 == 0) { Sound.playSound(Sound.stoneMining); }
			mined--;
			return false;
		}
		level.removeEntity(this);
		resetSpritesAroundThis();
		level.addItem(minedItem.copy());
		return true;

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
		decideSprite(leftHasOre, rightHasOre, topHasOre, bottomHasOre, topRightHasOre, bottomRightHasOre, bottomLeftHasOre, topLeftHasOre);
	}

	private void decideSprite(boolean leftHasOre, boolean rightHasOre, boolean topHasOre, boolean bottomHasOre, boolean topRightHasOre, boolean bottomRightHasOre, boolean bottomLeftHasOre, boolean topLeftHasOre) {
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

		Vec2f[] texCoordList = new Vec2f[sprites.size()];

		for (int i = 0; i < sprites.size(); i++) {
			texCoordList[i] = (sprites.get(i).getTexCoords());
		}
		sprite = new MultiSprite(texCoordList, 1);
	}

}
