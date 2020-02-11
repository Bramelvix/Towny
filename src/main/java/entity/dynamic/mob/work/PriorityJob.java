package entity.dynamic.mob.work;

public class PriorityJob implements Comparable<PriorityJob> {

	private final Job job;
	private int priority;

	public PriorityJob(Job job, int priority) {
		if (job == null) {
			throw new NullPointerException("Cannot create PriorityJob with null Job");
		}
		this.job = job;
		this.priority = priority;
	}

	public Job getJob() {
		return job;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(PriorityJob o) {
		return Integer.compare(priority, o.priority)*-1;
	}
}
