package entity.nonDynamic.building.workstations;

import entity.nonDynamic.building.BuildAbleObject;

public abstract class Workstation extends BuildAbleObject{
	private boolean running = false;
    byte animationcounter = 0;

    Workstation() {
		super();
	}

    void anmiationCounterTick() {
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
