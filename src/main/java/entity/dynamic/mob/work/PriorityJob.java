package entity.dynamic.mob.work;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PriorityJob implements Comparable<PriorityJob> {

	private final ArrayList<Job> jobs = new ArrayList<>();
	private final int priority;

	public PriorityJob(@NotNull Job job, int priority) {
		jobs.add(job);
		this.priority = priority;
	}

	public Job getJob() {
		return jobs.getFirst();
	}

	public void nextJobIfDone() {
		if (jobs.getFirst().isCompleted()) {
			jobs.removeFirst();
		}
	}

	public boolean completed() {
		return jobs.isEmpty();
	}

	public void addJob(Job job, int index) {
		jobs.add(index, job);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj instanceof PriorityJob other) {
			return priority == other.priority && jobs.equals(other.jobs);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return jobs.hashCode() + priority;
	}

	@Override
	public int compareTo(PriorityJob o) {
		return Integer.compare(priority, o.priority);
	}
}
