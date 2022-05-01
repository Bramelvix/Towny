package entity.non_dynamic.building.container;

import graphics.Sprite;
import graphics.SpriteHashtable;

public abstract class Workstation extends Container {

	public enum Type {
		ANVIL, FURNACE
	}

	private final Sprite runningSprite;
	private final Sprite passiveSprite;
	private final Type type;

	Workstation(Type type, Sprite passive, Sprite running) {
		super(4);
		this.type = type;
		this.passiveSprite = passive;
		this.runningSprite = running;
		setName(type.toString().toLowerCase());
		sprite = passive;
	}

	public void setRunning(boolean running) {
		sprite = running ? runningSprite : passiveSprite;
	}

	public Type getType() {
		return type;
	}

	public static Workstation furnace() {
		return new Workstation(Type.FURNACE, SpriteHashtable.get(82), SpriteHashtable.get(271)) {
			@Override
			public Workstation instance() {
				return furnace();
			}
		};
	}

	public static Workstation anvil() {
		return new Workstation(Type.ANVIL, SpriteHashtable.get(85), SpriteHashtable.get(85)) {
			@Override
			public Workstation instance() {
				return anvil();
			}
		};
	}

}
