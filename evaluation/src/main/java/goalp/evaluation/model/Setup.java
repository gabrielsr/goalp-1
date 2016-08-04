package goalp.evaluation.model;

import goalp.systems.Agent;
import goalp.systems.IDeploymentPlanner;
import goalp.systems.IRepository;

public class Setup {

	public IRepository repo;
	public IDeploymentPlanner planner;
	public Agent agent;
	
	
	public Setup(IRepository repo, IDeploymentPlanner planner, Agent agent) {
		this.repo = repo;
		this.planner = planner;
		this.agent = agent;
	}

}
