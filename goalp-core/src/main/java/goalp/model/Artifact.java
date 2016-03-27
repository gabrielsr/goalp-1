package goalp.model;

import java.util.ArrayList;
import java.util.List;

public class Artifact implements IDependency, IDependant{

	protected List<Goal> provide;
	
	protected List<IDependency> dependencies;

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

	public List<IDependency> getDependencies() {
		if(this.dependencies == null){
			this.dependencies = new ArrayList<>();
		}
		return dependencies;
	}

	public void setDependencies(List<IDependency> dependencies) {
		this.dependencies = dependencies;
	}

	public List<String> getContextRequirement() {
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

}
