package goalp.model;

import java.util.List;

public class Plan {

	public Plan(Goal capability, List<Goal> requirements, List<Resource> dependencies) {
		super();
		this.capability = capability;
		this.requirements = requirements;
		this.dependencies = dependencies;
	}

	public Goal getCapability() {
		return capability;
	}

	public void setCapability(Goal capability) {
		this.capability = capability;
	}

	public List<Goal> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<Goal> requirements) {
		this.requirements = requirements;
	}

	public List<Resource> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Resource> dependencies) {
		this.dependencies = dependencies;
	}

	protected Goal capability;

	protected List<Goal> requirements;

	protected List<Resource> dependencies;

	public static class Builder {
		private Goal capability;
		private List<Goal> requirements;
		private List<Resource> dependencies;

		public Builder capability(Goal capability) {
			this.capability = capability;
			return this;
		}

		public Builder requirements(List<Goal> requirements) {
			this.requirements = requirements;
			return this;
		}

		public Builder dependencies(List<Resource> dependencies) {
			this.dependencies = dependencies;
			return this;
		}

		public Plan build() {
			return new Plan(this);
		}
	}

	private Plan(Builder builder) {
		this.capability = builder.capability;
		this.requirements = builder.requirements;
		this.dependencies = builder.dependencies;
	}
}
