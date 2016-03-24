package goalp.systems;

import java.util.ArrayList;
import java.util.List;

import goalp.model.Artifact;
import goalp.model.Goal;
import goalp.model.Resource;

public class Agent {

	protected List<String> computingEnvironment;


	protected List<Resource> resources;

	protected List<Artifact> localRepository;

	protected List<Goal> achievableGoals;


	public List<String> getComputingEnvironment() {
		if(computingEnvironment == null){
			computingEnvironment = new ArrayList<>();
		}
		return computingEnvironment;
	}

	public void setComputingEnvironment(List<String> computingEnvironment) {
		this.computingEnvironment = computingEnvironment;
	}
	
	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public List<Artifact> getLocalRepository() {
		return localRepository;
	}

	public void setLocalRepository(List<Artifact> localRepository) {
		this.localRepository = localRepository;
	}

	public List<Goal> getAchievableGoals() {
		return achievableGoals;
	}

	public void setAchievableGoals(List<Goal> achievableGoals) {
		this.achievableGoals = achievableGoals;
	}

}
