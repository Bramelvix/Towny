package entity.mob;

import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import entity.Ore;
import entity.Tree;
import entity.Wall;
import entity.item.Item;
import entity.pathfinding.Path;
import entity.pathfinding.PathFinder;
import entity.pathfinding.Point;
import graphics.HairSprite;
import graphics.Screen;
import graphics.Sprite;
import input.Mouse;
import map.Map;

public class Villager extends Mob {
	public Path movement;
	private int counter;
	private boolean arrived = false;
	public List<Item> wearing;
	public boolean male;
	public Item holding;
	private Sprite hair;
	private int hairnr;
	private boolean hasJob = false;
	private Entity jobEntity;
	private boolean onJobLoc;
	private boolean selected;
	private boolean itemNeeded;
	private Item material;

	public Villager(int x, int y, Map level) {
		super(level);
		this.sprite = Sprite.getPerson();
		wearing = new ArrayList<Item>();
		initHair(true);
		this.x = x;
		this.y = y;

	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public Villager(int x, int y, Map level, int hairnr, List<Item> wearing, Item holding) {
		this(x, y, level);
		this.hairnr = hairnr;
		initHair(false);
		this.wearing = wearing;
		this.holding = holding;
	}

	private void initHair(boolean generate) {
		if (generate)
			hairnr = random.nextInt(HairSprite.maleHair.length);
		hair = HairSprite.getHair(HairSprite.maleHair, hairnr);

	}

	private Item getNearestItemOfType(String name) {
		Item closest = null;
		Path path = null;
		for (int i = 0; i < level.items.size(); i++) {
			if (level.items.get(i).getName().equals(name)) {
				closest = level.items.get(i);
				System.out.println(
						(x >> 4) + " / " + (y >> 4) + " ////// " + (closest.x >> 4) + " / " + (closest.y >> 4));
				path = getPath(x >> 4, y >> 4, closest.x >> 4, closest.y >> 4);
			}
		}
		if (closest == null || path == null) {
			return null;
		}
		for (Item i : level.items) {
			if (i.getName().equals(name)) {
				if (path.getLength() > getPath(x >> 4, y >> 4, i.x >> 4, i.y >> 4).getLength()) {
					closest = i;
					path = getPath(x >> 4, y >> 4, i.x >> 4, i.y >> 4);
				}
			}
		}
		return closest;
	}

	public void work(Entity e) {
		work(e, null);

	}

	public void work(Entity e, Item i) {
		if (!(i == null)) {
			if (itemNeeded) {
				pickUp(i);
			} else {
				drop(i);
			}
		}
		if (movement != null) {
			if (!movement.isArrived()) {
				move();
			}
		} else {
			if (onJobLoc) {
				if (jobEntity instanceof Tree) {
					hasJob = !((Tree) e).chop(level);
				} else {
					if (jobEntity instanceof Ore) {
						hasJob = !((Ore) e).mine(level);
					} else {
						if (jobEntity instanceof Wall) {
							// System.out.println((((Wall)
							// (jobEntity)).condition) + " / " + i);
							if (((Wall) (jobEntity)).condition == 0 && i != null) {
								((Wall) jobEntity).initWall(i, level);
							}
							hasJob = !((Wall) e).build();
						}
					}
				}
				if (!hasJob) {
					jobEntity = null;
					movement = null;
				}
			}
		}

	}

	public void pickUp(Item e) {
		movement = getShortest(e);
		if (!(movement == null)) {
			if (!movement.isArrived()) {
				move();
			} else {
				itemNeeded = false;
				setJob(jobEntity);
			}
			level.items.remove(e);
			holding = e;
		}

	}

	public void drop(Item e) {
		level.items.add(e);
		holding = null;
	}

	public void setJob(Entity e) {
		if (e != null) {
			jobEntity = e;
			movement = getShortest(e);
			hasJob = true;
		}
	}

	public void setJob(Entity e, boolean itemneeded) {
		setJob(e);
		this.itemNeeded = itemneeded;
		material = getNearestItemOfType("Logs");
	}

	public Path getShortest(Entity e) {
		if (e != null) {
			movement = getShortest(e.x >> 4, e.y >> 4);
		} else {
			movement = null;
		}
		return movement;
	}

	public Path getShortest(int x, int y) {
		movement = PathFinder.getShortest(
				new Path[] { getPath(this.x >> 4, this.y >> 4, x - 1, y), getPath(this.x >> 4, this.y >> 4, x + 1, y),
						getPath(this.x >> 4, this.y >> 4, x, y - 1), getPath(this.x >> 4, this.y >> 4, x, y + 1) });
		return movement;
	}

	public void update(Mouse mouse) {
		if (hasJob) {
			if (material != null) {
				work(jobEntity, material);
			} else {
				work(jobEntity);
			}
		} else {
			move();
		}

	}

	public void setJob(boolean job) {
		hasJob = job;
	}

	public void addClothing(Item item) {
		wearing.add(item);
	}

	public void move() {
		if (movement == null)
			return;
		if (arrived) {
			counter++;
			arrived = false;
		}
		if (movement.getLength() == counter) {
			counter = 0;
			if (hasJob && !itemNeeded) {
				onJobLoc = true;
			}
			movement = null;
			arrived = false;
			return;
		} else {
			if (!arrived) {
				Point step = movement.getStep(counter);
				moveTo(step.x << 4, step.y << 4);
				if (x == step.x << 4 && y == step.y << 4) {
					arrived = true;
				}

			}
		}

	}

	public void resetMove() {
		counter = 0;
		arrived = false;
		movement = null;
		hasJob = false;
		jobEntity = null;
	}

	public void moveTo(int x, int y) {
		int xmov, ymov;
		if (this.x > x) {
			xmov = -1;
		} else {
			if (this.x == x) {
				xmov = 0;
			} else {
				xmov = 1;
			}
		}
		if (this.y > y) {
			ymov = -1;
		} else {
			if (this.y == y) {
				ymov = 0;
			} else {
				ymov = 1;
			}
		}
		move(xmov, ymov);
		if (!(holding == null)) {
			holding.x = x;
			holding.y = y;
		}

	}

	public void render(Screen screen) {
		screen.renderVillager(x, y, this);
		if (wearing != null) {
			for (int i = 0; i < wearing.size(); i++) {
				if (wearing.get(i) != null) {
					screen.renderEntity(x, y, wearing.get(i).sprite1);
				}
			}
		}
		if (holding != null) {
			screen.renderEntity(x, y, holding.sprite1);
		}
		screen.renderEntity(x, y, hair);

	}

}
