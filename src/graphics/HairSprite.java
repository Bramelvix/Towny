package graphics;

public class HairSprite extends Sprite {

	public HairSprite(int x, int y, SpriteSheet sheet) {
		super(x, y, sheet);
	}

	private static Sprite brownHair1 = new Sprite(19, 0, SpriteSheet.entities);
	private static Sprite brownHair2 = new Sprite(20, 0, SpriteSheet.entities);
	private static Sprite brownHairBeard1 = new Sprite(21, 0, SpriteSheet.entities);
	private static Sprite brownHairBeard2 = new Sprite(22, 0, SpriteSheet.entities);
	private static Sprite lightBrownHair1 = new Sprite(23, 0, SpriteSheet.entities);
	private static Sprite lightBrownHair2 = new Sprite(24, 0, SpriteSheet.entities);
	private static Sprite lightBrownHairBeard1 = new Sprite(25, 0, SpriteSheet.entities);
	private static Sprite lightBrownHairBeard2 = new Sprite(26, 0, SpriteSheet.entities);
	private static Sprite brownHair3 = new Sprite(19, 1, SpriteSheet.entities);
	private static Sprite brownHair4 = new Sprite(20, 1, SpriteSheet.entities);
	private static Sprite brownHair5 = new Sprite(21, 1, SpriteSheet.entities);
	private static Sprite brownHair6 = new Sprite(22, 1, SpriteSheet.entities);
	private static Sprite lightBrownHair3 = new Sprite(23, 1, SpriteSheet.entities);
	private static Sprite lightBrownHair4 = new Sprite(24, 1, SpriteSheet.entities);
	private static Sprite lightBrownHair5 = new Sprite(25, 1, SpriteSheet.entities);
	private static Sprite lightBrownHair6 = new Sprite(26, 1, SpriteSheet.entities);
	private static Sprite brownHairBeard3 = new Sprite(19, 2, SpriteSheet.entities);
	private static Sprite brownHair7 = new Sprite(20, 2, SpriteSheet.entities);
	private static Sprite brownHair8 = new Sprite(21, 2, SpriteSheet.entities);
	private static Sprite brownHair9 = new Sprite(22, 2, SpriteSheet.entities);
	private static Sprite lightBrownHairBeard3 = new Sprite(23, 2, SpriteSheet.entities);
	private static Sprite lightBrownHair7 = new Sprite(24, 2, SpriteSheet.entities);
	private static Sprite lightBrownHair8 = new Sprite(25, 2, SpriteSheet.entities);
	private static Sprite lightBrownHair9 = new Sprite(26, 2, SpriteSheet.entities);
	private static Sprite blondeHair1 = new Sprite(19, 4, SpriteSheet.entities);
	private static Sprite blondeHair2 = new Sprite(20, 4, SpriteSheet.entities);
	private static Sprite blondeHairBeard1 = new Sprite(21, 4, SpriteSheet.entities);
	private static Sprite blondeHairBeard2 = new Sprite(22, 4, SpriteSheet.entities);
	private static Sprite blackHair1 = new Sprite(23, 4, SpriteSheet.entities);
	private static Sprite blackHair2 = new Sprite(24, 4, SpriteSheet.entities);
	private static Sprite blackHairBeard1 = new Sprite(25, 4, SpriteSheet.entities);
	private static Sprite blackHairBeard2 = new Sprite(26, 4, SpriteSheet.entities);
	private static Sprite blondeHair3 = new Sprite(19, 5, SpriteSheet.entities);
	private static Sprite blondeHair4 = new Sprite(20, 5, SpriteSheet.entities);
	private static Sprite blondeHair5 = new Sprite(21, 5, SpriteSheet.entities);
	private static Sprite blondeHair6 = new Sprite(22, 5, SpriteSheet.entities);
	private static Sprite blackHair3 = new Sprite(23, 5, SpriteSheet.entities);
	private static Sprite blackHair4 = new Sprite(24, 5, SpriteSheet.entities);
	private static Sprite blackHair5 = new Sprite(25, 5, SpriteSheet.entities);
	private static Sprite blackHair6 = new Sprite(26, 5, SpriteSheet.entities);
	private static Sprite blondeHairBeard3 = new Sprite(19, 6, SpriteSheet.entities);
	private static Sprite blondeHair7 = new Sprite(20, 6, SpriteSheet.entities);
	private static Sprite blondeHair8 = new Sprite(21, 6, SpriteSheet.entities);
	private static Sprite blondeHair9 = new Sprite(22, 6, SpriteSheet.entities);
	private static Sprite blackHairBeard3 = new Sprite(23, 6, SpriteSheet.entities);
	private static Sprite blackHair7 = new Sprite(24, 6, SpriteSheet.entities);
	private static Sprite blackHair8 = new Sprite(25, 6, SpriteSheet.entities);
	private static Sprite blackHair9 = new Sprite(26, 6, SpriteSheet.entities);
	private static Sprite whiteHair1 = new Sprite(19, 8, SpriteSheet.entities);
	private static Sprite whiteHair2 = new Sprite(20, 8, SpriteSheet.entities);
	private static Sprite whiteHairBeard1 = new Sprite(21, 8, SpriteSheet.entities);
	private static Sprite whiteHairBeard2 = new Sprite(22, 8, SpriteSheet.entities);
	private static Sprite whiteHair3 = new Sprite(19, 9, SpriteSheet.entities);
	private static Sprite whiteHair4 = new Sprite(20, 9, SpriteSheet.entities);
	private static Sprite whiteHair5 = new Sprite(21, 9, SpriteSheet.entities);
	private static Sprite whiteHair6 = new Sprite(22, 9, SpriteSheet.entities);
	private static Sprite whiteHairBeard3 = new Sprite(19, 10, SpriteSheet.entities);
	private static Sprite whiteHair7 = new Sprite(20, 10, SpriteSheet.entities);
	private static Sprite whiteHair8 = new Sprite(21, 10, SpriteSheet.entities);
	private static Sprite whiteHair9 = new Sprite(22, 10, SpriteSheet.entities);

	public static Sprite[] maleHair = { brownHair1, brownHairBeard1, brownHairBeard2, lightBrownHair1,
			lightBrownHairBeard1, lightBrownHairBeard2, brownHair3, lightBrownHair3, brownHairBeard3, brownHair7,
			brownHair8, brownHair9, lightBrownHairBeard3, lightBrownHair7, lightBrownHair8, lightBrownHair9,
			blondeHair1, blondeHairBeard1, blondeHairBeard2, blackHair1, blackHairBeard1, blackHairBeard2, blondeHair3,
			blackHair3, blondeHairBeard3, blondeHair7, blondeHair8, blondeHair9, blackHairBeard3, blackHair7,
			blackHair8, blackHair9, whiteHair1, whiteHairBeard1, whiteHairBeard2, whiteHair3, whiteHairBeard3,
			whiteHair7, whiteHair8, whiteHair9 };
	public static Sprite[] femaleHair = { brownHair2, lightBrownHair2, brownHair4, brownHair5, brownHair6,
			lightBrownHair4, lightBrownHair5, lightBrownHair6, blondeHair2, blackHair2, blondeHair4, blondeHair5,
			blondeHair6, blackHair4, blackHair5, blackHair6, whiteHair2, whiteHair4, whiteHair5, whiteHair6 };

}
