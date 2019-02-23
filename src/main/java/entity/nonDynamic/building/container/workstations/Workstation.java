package entity.nonDynamic.building.container.workstations;

import entity.nonDynamic.building.container.Container;

public abstract class Workstation extends Container {
	private boolean running = false;
	byte animationcounter = 0;

    Workstation() {
		super(4);
	}

    void animationCounterTick() {
		animationcounter++;
		if (animationcounter==60) {
			animationcounter = 0;
		}
	}

    boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}

}
