package entity.dynamic.mob;

import java.util.Optional;
import java.util.PriorityQueue;

import entity.dynamic.mob.work.BuildJob;
import entity.dynamic.mob.work.GatherJob;
import entity.dynamic.mob.work.PriorityJob;
import entity.nonDynamic.building.BuildAbleObject;
import entity.Entity;
import entity.nonDynamic.building.container.Container;
import entity.nonDynamic.resources.Resource;
import entity.dynamic.item.Clothing;
import entity.dynamic.item.Item;
import entity.dynamic.item.VillagerInventory;
import entity.dynamic.item.weapon.Weapon;
import entity.dynamic.mob.work.Job;
import entity.pathfinding.Path;
import graphics.OpenglUtils;
import graphics.Sprite;
import graphics.SpriteHashtable;
import map.Level;

public class Villager extends Humanoid {

	private final VillagerInventory inventory; // clothing item list
	private boolean male; // is the villager male (true = male, false = female)
	private Sprite hair; // hair sprite
	private int hairnr; // hair number (needed for the hair sprite to be decided)
	private final PriorityQueue<PriorityJob> jobs; // jobs the villager needs to do

	// basic constructors
	public Villager(int x, int y, int z, Level[] levels) {
		super(levels, x, y, z);
		sprite = SpriteHashtable.getPerson();
		inventory = new VillagerInventory(this);
		jobs = new PriorityQueue<>();
		male = Entity.RANDOM.nextBoolean();
		hair = SpriteHashtable.get(generateHairNr());
		setName("villager");
	}

	@Override
	public float getDamage() {
		return inventory.getWeaponDamage();
	}

	public int getMeleeDamage() {
		return 10;
	}

	public Villager(int x, int y, int z, Level[] levels, int hairnr, Item holding, boolean male) {
		this(x, y, z, levels);
		this.hairnr = hairnr;
		this.male = male;
		hair = SpriteHashtable.get(hairnr);
		this.setHolding(holding);
	}

	public int getJobSize() {
		return jobs.size();
	}

   //generate a random number to use for the hairsprite
	private int generateHairNr() {
		return male ?
			SpriteHashtable.maleHairNrs[Entity.RANDOM.nextInt(SpriteHashtable.maleHairNrs.length)]
			: SpriteHashtable.femaleHairNrs[Entity.RANDOM.nextInt(SpriteHashtable.femaleHairNrs.length)];
	}

	// gets the item nearest to the villager(of the same kind and unreserved)
	public Optional<Item> getNearestItemOfType(Item item) {
		if (getHolding() != null && getHolding().isSameType(item)) {
			return Optional.of(getHolding());
		}
		Item closest = null;
		Path path = null;
		for (Item level_item : levels[z].getItems()) {
			if (item.isSameType(level_item) && level_item.isReserved(this)) {
				if (closest == null || path == null || (getPath(level_item.getX() / 48, level_item.getY() / 48) != null
					&& path.getStepsSize() > getPath(level_item.getX() / 48, level_item.getY() / 48).getStepsSize()
				)) {
					closest = level_item;
					path = getPath(closest.getX() / 48, closest.getY() / 48);
				}
			}
		}
		if (closest == null || path == null) {
			return Optional.empty();
		}
		return Optional.of(closest);
	}

	// work method for the villager to execute his jobs
	public void work() {
		if (jobs.peek() != null) {
			Job job = jobs.peek().getJob();
			if (job.isCompleted()) {
				jobs.remove(jobs.peek());
			}
			job.execute();

		}
	}

	// pickup an item
	public <T extends Item> boolean pickUp(T e) {
		if (!onSpot(e.getX(), e.getY(), e.getZ())) { return false; }
		e.setReserved(this);
		levels[z].removeItem(e);
		pickUpItem(e);
		return true;
	}

	private <T extends Item> void pickUpItem(T e) {
		if (e instanceof Weapon) {
			addWeapon((Weapon) e);
			return;
		} else {
			if (e instanceof Clothing) {
				addClothing((Clothing) e);
				return;
			}
		}
		drop();
		setHolding(e);
	}

	// drop the item the villager is holding
	public void drop() {
		if (getHolding() != null) {
			getHolding().removeReserved();
			levels[z].addItem(getHolding());
			setHolding(null);
		}
	}

	public void drop(Container container) {
		if (getHolding() != null && this.aroundTile(container.getX(), container.getY(), container.getZ())) {
			container.addItemTo(getHolding());
			getHolding().removeReserved();
			setHolding(null);
		}
	}

	public <T extends Item> boolean pickUp(T e, Container container) {
		if (aroundTile(container.getX(), container.getY(), container.getZ())) {
			Optional<Item> foundItem = container.takeItem(e);
			foundItem.ifPresent(this::pickUpItem);
			return true;
		}
		return false;
	}

	// add a job to the jobs spritesheets for the villager to do
	public void addJob(Resource e) {
		if (e != null) { addJob(new GatherJob(e, this)); }
	}

	public void addJob(Job e) {
		if (e != null) { jobs.add(new PriorityJob(e, 50)); }
	}

	public void addJob(Job e, int priority) {
		if (e != null) { jobs.add(new PriorityJob(e, priority)); }
	}

	// add a buildjob
	public void addBuildJob(int x, int y, int z, BuildAbleObject object, Item resource) {
		if (resource == null) {
			addJob(new BuildJob(x, y, z, object, this));
		} else {
			getNearestItemOfType(resource).ifPresent(item ->  addJob(new BuildJob(x, y, z, item, object, this)));
		}
	}

	// updates the villager in the game logic
	public void update() {
		if (!jobs.isEmpty()) {
			work();
		} else {
			// IDLE
			if (idleTime()) { idle(); }
			move();
		}
		inventory.update(x, y, z);
	}

	// add clothing to the villager
	public void addClothing(Clothing item) {
		inventory.addClothing(item);
	}

	private void addWeapon(Weapon item) {
		inventory.addWeapon(item);
	}

	public void resetAll() {
		jobs.clear();
		setPath(null);
	}

	// render onto the screen
	@Override
	public void render() {
		drawVillager(x,y);
		if (this.isSelected()) {
			OpenglUtils.drawSelection(x,y,Sprite.SIZE,Sprite.SIZE);// render the red square around selected villagers
		}
	}

	private void drawVillager(int x, int y) {
		if (isVisible()) {
			sprite.draw(x,y);
			hair.draw(x,y);
			inventory.render();
			if (getHolding() != null) {
				getHolding().sprite.draw(x,y);// renders the item the villager is holding
			}
		}
	}

	@Override
	public void die() {
		super.die();
		inventory.dropAll();
	}

}
