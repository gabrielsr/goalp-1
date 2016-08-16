package goalp.evaluation.model;

import java.util.List;

import goalp.systems.Agent;
import goalp.systems.IDeploymentPlanner;
import goalp.systems.IRepository;

public class ExperimentSetup {

	private IRepository repository;
	
	private List<String> rootGoals;
	
	private IDeploymentPlanner planner;
	
	private Agent agent;

	public IRepository getRepository() {
		return repository;
	}

	public void setRepository(IRepository repository) {
		this.repository = repository;
	}

	public List<String> getRootGoals() {
		return rootGoals;
	}

	public void setRootGoals(List<String> rootGoals) {
		this.rootGoals = rootGoals;
	}

	public IDeploymentPlanner getPlanner() {
		return planner;
	}

	public void setPlanner(IDeploymentPlanner planner) {
		this.planner = planner;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

}
