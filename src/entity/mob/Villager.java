package entity.mob;

import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import entity.Ore;
import entity.Tree;
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

	public Villager(int x, int y, Map level) {
		super(level);
		this.sprite = Sprite.villager1;
		wearing = new ArrayList<Item>();
		initHair(true);
		this.x = x;
		this.y = y;

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

	public void harvestEntity(Entity e) {
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
					}
				}
				if (!hasJob) {
					jobEntity = null;
					movement = null;
				}
			}
		}

	}

	public void setJob(Entity e, Path p) {
		if (e != null && p != null) {
			jobEntity = e;
			movement = p;
			hasJob = true;
		}
	}

	public Path getShortest(Entity e) {
		if (e != null) {
			movement = PathFinder.getShortest(new Path[] { getPath(x >> 4, y >> 4, (e.x - 16) >> 4, e.y >> 4),
					getPath(x >> 4, y >> 4, (e.x + 16) >> 4, e.y >> 4),
					getPath(x >> 4, y >> 4, e.x >> 4, (e.y - 16) >> 4),
					getPath(x >> 4, y >> 4, e.x >> 4, (e.y + 16) >> 4) });
		} else {
			movement = null;
		}
		return movement;
	}

	public void update(Mouse mouse) {
		if (hasJob) {
			harvestEntity(jobEntity);
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
			if (hasJob) {
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

	}

	public void render(Screen screen) {
		screen.renderEntity(x, y, sprite);
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
