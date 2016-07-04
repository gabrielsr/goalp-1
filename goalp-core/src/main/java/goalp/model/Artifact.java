package goalp.model;

import java.util.ArrayList;
import java.util.List;

public class Artifact{

	protected List<Goal> provide;
	
	protected List<Goal> dependencies;

	protected String identification;
	
	protected List<String> contextRequirement;


	public String getIdentification() {
		return this.identification;
	}
	
	public List<Goal> getProvide() {
		if(this.provide == null){
			this.provide = new ArrayList<>();
		}
		return provide;
	}

	public void setProvide(List<Goal> provide) {
		this.provide = provide;
	}

	public List<Goal> getDependencies() {
		if(this.dependencies == null){
			this.dependencies = new ArrayList<>();
		}
		return dependencies;
	}

	public void setDependencies(List<Goal> dependencies) {
		this.dependencies = dependencies;
	}

	public List<String> getContextConditions() {
		if(contextRequirement == null){
			contextRequirement = new ArrayList<>();
		}
		return contextRequirement;
	}

	public void setContextRequirement(List<String> contextRequirement) {
		this.contextRequirement = contextRequirement;
	}
	
	public boolean isProvider(Goal goal) {
		for(Goal providedGoal: this.getProvide()){
			if(goal.equals(providedGoal)){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Artifact [provide=" + provide + ", dependencies=" + dependencies + ", identification=" + identification
				+ ", contextRequirement=" + contextRequirement + "]";
	}

}
