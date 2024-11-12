package entity.dynamic.mob;

import entity.Entity;
import entity.dynamic.item.Clothing;
import entity.dynamic.item.Item;
import entity.dynamic.item.VillagerInventory;
import entity.dynamic.item.weapon.Weapon;
import entity.dynamic.mob.work.*;
import entity.non_dynamic.building.BuildAbleObject;
import entity.non_dynamic.building.container.Container;
import entity.non_dynamic.building.farming.TilledSoil;
import entity.non_dynamic.resources.Resource;
import entity.pathfinding.Path;
import graphics.Sprite;
import graphics.SpriteHashtable;
import graphics.opengl.OpenGLUtils;
import map.Level;
import org.jetbrains.annotations.NotNull;
import util.vectors.Vec2f;
import util.vectors.Vec3f;

import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;

public class Villager extends Humanoid {

	private final VillagerInventory inventory; // clothing item list
	private final boolean male; // is the villager male (true = male, false = female)
	private final Sprite hair; // hair sprite
	private final PriorityQueue<PriorityJob> jobs = new PriorityQueue<>(); // jobs the villager needs to do

	private boolean isSoldier;

	private static final int DEFAULT_DAMAGE = 10;

	// basic constructors
	public Villager(float x, float y, int z, Level[] levels) {
		this(x, y, z, levels, Entity.RANDOM.nextBoolean(), SpriteHashtable.getPerson());
	}

	public Villager(float x, float y, int z, Level[] levels, boolean male, Sprite body) {
		this(x, y, z, levels, male, body, SpriteHashtable.getHair(male));
	}

	public Villager(float x, float y, int z, Level[] levels, boolean male, Sprite body, Sprite hair) {
		super(levels, x, y, z);
		sprite = body;
		inventory = new VillagerInventory(this);
		this.male = male;
		this.hair = hair;
		setName("villager");
		location.z = 0.1f;
	}

	@Override
	public float getDamage() {
		return inventory.getWeaponDamage();
	}

	public int getMeleeDamage() {
		return DEFAULT_DAMAGE;
	}

	public Villager(int x, int y, int z, Level[] levels, int hairnr, Item holding, boolean male) {
		this(x, y, z, levels, male, SpriteHashtable.getPerson(), SpriteHashtable.get(hairnr));
		this.setHolding(holding);
	}

	public int getJobSize() {
		return jobs.size();
	}

	// gets the item nearest to the villager(of the same kind and unreserved)
	public Optional<Item> getNearestItemOfType(Item itemToSearch) {
		if (getHolding() != null && getHolding().isSameType(itemToSearch)) {
			return Optional.of(getHolding());
		}

		return levels[z].getItems().stream().filter(item -> item.isSameType(itemToSearch) && item.isReserved(this)
				&& getPath(item).isPresent()).min(Comparator.comparingInt(item -> getPath(item).get().getLength()));
	}

	// work method for the villager to execute his jobs
	public void work() {
		PriorityJob priorityJob = jobs.peek();
		if (priorityJob != null) {
			if (priorityJob.completed()) {
				jobs.remove(priorityJob);
				return;
			}
			priorityJob.getJob().execute();
			priorityJob.nextJobIfDone();

		}
	}

	// pick up an item
	public <T extends Item> boolean pickUp(T e) {
		if (!onSpot(e.getTileX(), e.getTileY(), e.getZ())) {
			return false;
		}
		e.setReserved(this);
		levels[z].removeItem(e.getTileX(), e.getTileY());
		pickUpItem(e);
		return true;
	}

	private <T extends Item> void pickUpItem(T e) {
		if (e instanceof Weapon weapon) {
			addWeapon(weapon);
			return;
		} else {
			if (e instanceof Clothing clothing) {
				addClothing(clothing);
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
		if (getHolding() != null && this.aroundTile(container.getTileX(), container.getTileY(), container.getZ())) {
			container.addItemTo(getHolding());
			getHolding().removeReserved();
			setHolding(null);
		}
	}

	public <T extends Item> boolean pickUp(T e, Container container) {
		if (aroundTile(container.getTileX(), container.getTileY(), container.getZ())) {
			Optional<Item> foundItem = container.takeItem(e);
			foundItem.ifPresent(this::pickUpItem);
			return true;
		}
		return false;
	}

	// add a job to the jobs list for the villager to do
	public void addJob(@NotNull Resource e) {
		addJob(new GatherJob(this, e));
	}

	public void addJob(@NotNull TilledSoil e) {
		addJob(new FarmJob(this, e));
	}

	public void addJob(@NotNull Job e) {
		jobs.add(new PriorityJob(e, distanceToJob(e)));
	}

	private int distanceToJob(@NotNull Job job) {
		return getPath(job.getXloc(), job.getYloc()).map(Path::getLength).orElse(-1);
	}

	public void prependJobToChain(@NotNull Job e) {
		if (!jobs.isEmpty()) {
			jobs.peek().addJob(e, 0);
		}
	}

	// add a buildjob
	public void addBuildJob(int x, int y, int z, BuildAbleObject object, Item resource) {
		if (resource == null) {
			addJob(new BuildJob(this, x, y, z, object));
		} else {
			getNearestItemOfType(resource).ifPresent(item -> addJob(new BuildJob(this, x, y, z, item, object)));
		}
	}

	// updates the villager in the game logic
	@Override
	public void update() {
		if (!jobs.isEmpty()) {
			work();
		} else {
			// IDLE
			if (idleTime()) {
				idle();
			}
			move();
		}
		inventory.update(location.x, location.y, z);
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
		drawVillager(location);
		if (this.isSelected()) {
			OpenGLUtils.addOutline(location.xy(), new Vec2f(Sprite.SIZE));
		}
	}

	private void drawVillager(Vec3f pos) {
		if (isVisible()) {
			sprite.draw(pos);
			//if graphical clipping happens, mess with these Z values
			hair.draw(new Vec3f(pos.x, pos.y, pos.z));
			inventory.render(pos.z);
			if (getHolding() != null) {
				getHolding().getSprite().draw(new Vec3f(pos.x, pos.y, pos.z));// renders the item the villager is holding
			}
		}
	}

	@Override
	public void die() {
		super.die();
		inventory.dropAll();
	}

	public boolean isSoldier() {
		return isSoldier;
	}

	public void setSoldier(boolean soldier) {
		isSoldier = soldier;
	}
}
