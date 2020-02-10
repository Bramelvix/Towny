package entity.dynamic.item;

public enum ClothingType {

	HAT(0), SHIRT(1), TROUSERS(2), SHOES(3);

	private int numVal;

	ClothingType(int numVal) {
		this.numVal = numVal;
	}

	public int getNumVal() {
		return numVal;
	}

}
