package entity.workstations;

import entity.BuildAbleObject;

public abstract class Workstation extends BuildAbleObject{
	private boolean running = false;
	protected byte animationcounter = 0;

	public Workstation() {
		super();
	}
	protected void anmiationCounterTick() {
		animationcounter++;
		if (animationcounter==60) {
			animationcounter = 0;
		}
	}
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}

}
