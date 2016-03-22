package goalp.systems;

import java.util.List;

import goalp.model.Agent;
import goalp.model.DeploymentRequest;
import goalp.model.Plan;

//Goal
public interface PlanSelector {

	public List<Plan> select(DeploymentRequest request, Agent agent, List<Plan> knownPlans) throws PlanSelectionException;

}
