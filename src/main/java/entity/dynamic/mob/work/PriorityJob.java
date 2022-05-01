package entity.dynamic.mob.work;

import java.util.ArrayList;

public class PriorityJob implements Comparable<PriorityJob> {

	private final ArrayList<Job> jobs = new ArrayList<>();
	private int priority;

	public PriorityJob(Job job, int priority) {
		if (job == null) {
			throw new NullPointerException("Cannot create PriorityJob with null Job");
		}
		jobs.add(job);
		this.priority = priority;
	}

	public Job getJob() {
		return jobs.get(0);
	}

	public void nextJobIfDone() {
		if (jobs.get(0).isCompleted()) {
			jobs.remove(0);
		}
	}

	public boolean completed() {
		return jobs.isEmpty();
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void addJob(Job job, int index) {
		jobs.add(index, job);
	}

	@Override
	public int compareTo(PriorityJob o) {
		return Integer.compare(priority, o.priority) * -1;
	}
}
