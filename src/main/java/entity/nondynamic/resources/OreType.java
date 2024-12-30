package entity.nondynamic.resources;

//the types of ore in the game
public enum OreType {

	IRON(54, 5), GOLD(56, 7), COAL(53, 8), COPPER(55, 6), CRYSTAL(57, 9), STONE(161, 10);

	private final int spriteId;
	private final int itemId;

	OreType(int spriteId, int itemSpriteId) {
		this.spriteId = spriteId;
		this.itemId = itemSpriteId;
	}

	public int getSpriteId() {
		return spriteId;
	}

	public int getItemId() {
		return itemId;
	}

}
