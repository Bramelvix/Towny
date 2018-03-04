package graphics;

import main.Game;

public class HairSprite extends Sprite {

	//constructor
    public HairSprite(int x, int y, Spritesheet sheet) {
		super(x, y, sheet);
	}

    //spritesheets of all the hair sprites in the game. Static so they are loaded only once and reused
    private static final Sprite brownHair1 = new Sprite(19, 0, SpritesheetHashtable.get(2));
    private static final Sprite brownHair2 = new Sprite(20, 0, SpritesheetHashtable.get(2));
    private static final Sprite brownHairBeard1 = new Sprite(21, 0, SpritesheetHashtable.get(2));
    private static final Sprite brownHairBeard2 = new Sprite(22, 0, SpritesheetHashtable.get(2));
    private static final Sprite lightBrownHair1 = new Sprite(23, 0, SpritesheetHashtable.get(2));
    private static final Sprite lightBrownHair2 = new Sprite(24, 0, SpritesheetHashtable.get(2));
    private static final Sprite lightBrownHairBeard1 = new Sprite(25, 0, SpritesheetHashtable.get(2));
    private static final Sprite lightBrownHairBeard2 = new Sprite(26, 0, SpritesheetHashtable.get(2));
    private static final Sprite brownHair3 = new Sprite(19, 1, SpritesheetHashtable.get(2));
    private static final Sprite brownHair4 = new Sprite(20, 1, SpritesheetHashtable.get(2));
    private static final Sprite brownHair5 = new Sprite(21, 1, SpritesheetHashtable.get(2));
    private static final Sprite brownHair6 = new Sprite(22, 1, SpritesheetHashtable.get(2));
    private static final Sprite lightBrownHair3 = new Sprite(23, 1, SpritesheetHashtable.get(2));
    private static final Sprite lightBrownHair4 = new Sprite(24, 1, SpritesheetHashtable.get(2));
    private static final Sprite lightBrownHair5 = new Sprite(25, 1, SpritesheetHashtable.get(2));
    private static final Sprite lightBrownHair6 = new Sprite(26, 1, SpritesheetHashtable.get(2));
    private static final Sprite brownHairBeard3 = new Sprite(19, 2, SpritesheetHashtable.get(2));
    private static final Sprite brownHair7 = new Sprite(20, 2, SpritesheetHashtable.get(2));
    private static final Sprite brownHair8 = new Sprite(21, 2, SpritesheetHashtable.get(2));
    private static final Sprite brownHair9 = new Sprite(22, 2, SpritesheetHashtable.get(2));
    private static final Sprite lightBrownHairBeard3 = new Sprite(23, 2, SpritesheetHashtable.get(2));
    private static final Sprite lightBrownHair7 = new Sprite(24, 2, SpritesheetHashtable.get(2));
    private static final Sprite lightBrownHair8 = new Sprite(25, 2, SpritesheetHashtable.get(2));
    private static final Sprite lightBrownHair9 = new Sprite(26, 2, SpritesheetHashtable.get(2));
    private static final Sprite blondeHair1 = new Sprite(19, 4, SpritesheetHashtable.get(2));
    private static final Sprite blondeHair2 = new Sprite(20, 4, SpritesheetHashtable.get(2));
    private static final Sprite blondeHairBeard1 = new Sprite(21, 4, SpritesheetHashtable.get(2));
    private static final Sprite blondeHairBeard2 = new Sprite(22, 4, SpritesheetHashtable.get(2));
    private static final Sprite blackHair1 = new Sprite(23, 4, SpritesheetHashtable.get(2));
    private static final Sprite blackHair2 = new Sprite(24, 4, SpritesheetHashtable.get(2));
    private static final Sprite blackHairBeard1 = new Sprite(25, 4, SpritesheetHashtable.get(2));
    private static final Sprite blackHairBeard2 = new Sprite(26, 4, SpritesheetHashtable.get(2));
    private static final Sprite blondeHair3 = new Sprite(19, 5, SpritesheetHashtable.get(2));
    private static final Sprite blondeHair4 = new Sprite(20, 5, SpritesheetHashtable.get(2));
    private static final Sprite blondeHair5 = new Sprite(21, 5, SpritesheetHashtable.get(2));
    private static final Sprite blondeHair6 = new Sprite(22, 5, SpritesheetHashtable.get(2));
    private static final Sprite blackHair3 = new Sprite(23, 5, SpritesheetHashtable.get(2));
    private static final Sprite blackHair4 = new Sprite(24, 5, SpritesheetHashtable.get(2));
    private static final Sprite blackHair5 = new Sprite(25, 5, SpritesheetHashtable.get(2));
    private static final Sprite blackHair6 = new Sprite(26, 5, SpritesheetHashtable.get(2));
    private static final Sprite blondeHairBeard3 = new Sprite(19, 6, SpritesheetHashtable.get(2));
    private static final Sprite blondeHair7 = new Sprite(20, 6, SpritesheetHashtable.get(2));
    private static final Sprite blondeHair8 = new Sprite(21, 6, SpritesheetHashtable.get(2));
    private static final Sprite blondeHair9 = new Sprite(22, 6, SpritesheetHashtable.get(2));
    private static final Sprite blackHairBeard3 = new Sprite(23, 6, SpritesheetHashtable.get(2));
    private static final Sprite blackHair7 = new Sprite(24, 6, SpritesheetHashtable.get(2));
    private static final Sprite blackHair8 = new Sprite(25, 6, SpritesheetHashtable.get(2));
    private static final Sprite blackHair9 = new Sprite(26, 6, SpritesheetHashtable.get(2));
    private static final Sprite whiteHair1 = new Sprite(19, 8, SpritesheetHashtable.get(2));
    private static final Sprite whiteHair2 = new Sprite(20, 8, SpritesheetHashtable.get(2));
    private static final Sprite whiteHairBeard1 = new Sprite(21, 8, SpritesheetHashtable.get(2));
    private static final Sprite whiteHairBeard2 = new Sprite(22, 8, SpritesheetHashtable.get(2));
    private static final Sprite whiteHair3 = new Sprite(19, 9, SpritesheetHashtable.get(2));
    private static final Sprite whiteHair4 = new Sprite(20, 9, SpritesheetHashtable.get(2));
    private static final Sprite whiteHair5 = new Sprite(21, 9, SpritesheetHashtable.get(2));
    private static final Sprite whiteHair6 = new Sprite(22, 9, SpritesheetHashtable.get(2));
    private static final Sprite whiteHairBeard3 = new Sprite(19, 10, SpritesheetHashtable.get(2));
    private static final Sprite whiteHair7 = new Sprite(20, 10, SpritesheetHashtable.get(2));
    private static final Sprite whiteHair8 = new Sprite(21, 10, SpritesheetHashtable.get(2));
    private static final Sprite whiteHair9 = new Sprite(22, 10, SpritesheetHashtable.get(2));

	//array of male hair sprites (so a random number can get a random sprite)
	public static Sprite[] maleHair = { brownHair1, brownHairBeard1, brownHairBeard2, lightBrownHair1,
			lightBrownHairBeard1, lightBrownHairBeard2, brownHair3, lightBrownHair3, brownHairBeard3, brownHair7,
			brownHair8, brownHair9, lightBrownHairBeard3, lightBrownHair7, lightBrownHair8, lightBrownHair9,
			blondeHair1, blondeHairBeard1, blondeHairBeard2, blackHair1, blackHairBeard1, blackHairBeard2, blondeHair3,
			blackHair3, blondeHairBeard3, blondeHair7, blondeHair8, blondeHair9, blackHairBeard3, blackHair7,
			blackHair8, blackHair9, whiteHair1, whiteHairBeard1, whiteHairBeard2, whiteHair3, whiteHairBeard3,
			whiteHair7, whiteHair8, whiteHair9 };
	//same thing for female hair
	public static Sprite[] femaleHair = { brownHair2, lightBrownHair2, brownHair4, brownHair5, brownHair6,
			lightBrownHair4, lightBrownHair5, lightBrownHair6, blondeHair2, blackHair2, blondeHair4, blondeHair5,
			blondeHair6, blackHair4, blackHair5, blackHair6, whiteHair2, whiteHair4, whiteHair5, whiteHair6 };

}
