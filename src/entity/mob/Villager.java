package entity.mob;

import java.util.ArrayList;
import java.util.List;

import entity.building.BuildAbleObject;
import entity.Entity;
import entity.Resource;
import entity.item.Clothing;
import entity.item.Item;
import entity.item.VillagerInventory;
import entity.item.weapon.Weapon;
import entity.mob.work.Job;
import entity.pathfinding.Path;
import graphics.HairSprite;
import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteHashtable;
import map.Level;

public class Villager extends Humanoid {
	public VillagerInventory inventory; // clothing item spritesheets
	public boolean male; // is the villager male
	private Sprite hair; // hair sprite
	private int hairnr; // hair number (needed for the hair sprite to be
						// decided)
	private List<Job> jobs; // jobs the villager needs to do

	// basic constructors
	public Villager(int x, int y, Level level) {
		this(level, x, y);
		this.sprite = SpriteHashtable.getPerson();
		inventory = new VillagerInventory(this);
		jobs = new ArrayList<Job>();
		male = Entity.RANDOM.nextBoolean();
		initHair(true);
		setName("villager");

	}

	@Override
	public float getDamage() {
		return inventory.getWeaponDamage();
	}

	public int getMeleeDamage() {
		return 10;
	}

	private Villager(Level level, int x, int y) {
		super(level, x, y);
	}

	public Villager(int x, int y, Level level, int hairnr, VillagerInventory wearing, Item holding, boolean male) {
		this(x, y, level);
		this.hairnr = hairnr;
		this.male = male;
		initHair(false);
		this.inventory = wearing;
		this.setHolding(holding);
	}

	public int getJobSize() {
		return jobs.size();
	}

	// initialise the hairsprite
	private void initHair(boolean generate) {
		if (generate) {
			hairnr = male ? Entity.RANDOM.nextInt(HairSprite.maleHair.length)
					: Entity.RANDOM.nextInt(HairSprite.femaleHair.length);
		}
		hair = male ? HairSprite.maleHair[hairnr] : HairSprite.femaleHair[hairnr];

	}

	// gets the item nearest to the villager with a specific name (and
	// unreserved)
	public Item getNearestItemOfType(Item item) {
		if (getHolding() != null && getHolding().isSameType(item)) {
			return getHolding();
		}
		Item closest = null;
		Path path = null;
		for (Item level_item : level.getItems()) {
			if (level_item != null && level_item.isSameType(item) && level_item.isReserved(this)) {
				if (closest == null || path == null
						|| (getPath(level_item.getX() >> 4, level_item.getY() >> 4) != null
								&& path.getStepsSize() > getPath(level_item.getX() >> 4, level_item.getY() >> 4)
										.getStepsSize())) {
					closest = level_item;
					path = getPath(closest.getX() >> 4, closest.getY() >> 4);
				}
			}

		}
		if (closest == null || path == null) {
			return null;
		}
		return closest;
	}

	// work method for the villager to execute his jobs
	public void work() {
		if (jobs.get(0) != null) {
			if (!jobs.get(0).isCompleted()) {
				jobs.get(0).execute();
			} else {
				jobs.remove(0);
			}
		}

	}

	// pickup an item
	public <T extends Item> boolean pickUp(T e) {
		if (onSpot(e.getX(), e.getY())) {
			e.setReserved(this);
			level.removeItem(e);
			if (e instanceof Weapon) {
				inventory.addWeapon((Weapon) e);
				return true;
			} else {
				if (e instanceof Clothing) {
					inventory.addClothing((Clothing) e);
					return true;
				}
			}
			drop();
			setHolding(e);
			return true;
		}
		return false;

	}

	// drop the item the villager is holding
	public void drop() {
		if (getHolding() != null) {
			getHolding().removeReserved();
			level.addItem(getHolding());
			setHolding(null);
		}
	}

	public <T extends Item> void dropItem(T item) {
		if (item != null) {
			level.addItem(item);
		}
	}

	// add a job to the jobs spritesheets for the villager to do
	public void addJob(Resource e) {
		if (e != null) {
			addJob(new Job(e, this));
		}

	}

	public void addJob(Job e) {
		if (e != null) {
			jobs.add(e);
		}
	}

	public void addJob(Job e, int prio) {
		if (e != null) {
			jobs.add(prio, e);
		}

	}

	// add a buildjob
	public void addBuildJob(int x, int y, BuildAbleObject object, Item resource) {
		addJob(new Job(x, y, getNearestItemOfType(resource), object, this));

	}

	// updates the villager in the game logic
	public void update() {
		if (jobs.size() != 0) {
			work();
		} else {
			// IDLE
			if (idleTime()) {
				idle();
			}
			move();
		}
		inventory.update(x, y);

	}

	// add clothing to the villager
	public void addClothing(Clothing item) {
		inventory.addClothing(item);
	}

	public void addWeapon(Weapon item) {
		inventory.addWeapon(item);
	}

	public void resetAll() {
		jobs.clear();
		setMovement(null);
	}

	// render onto the screen
	@Override
	public void render(Screen screen) {
		super.render(screen);
		screen.renderSprite(x, y, hair); // renders the hair
		inventory.render(screen);
		if (getHolding() != null) {
			screen.renderSprite(x, y, getHolding().sprite); // renders the item the
			// villager is holding
		}
		if (this.isSelected()) {
			screen.renderSelection(x, y, this); // render the red square around
												// selected villagers
		}

	}

	@Override
	public void die() {
		super.die();
		inventory.dropAll();

	}

}
