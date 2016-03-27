package goalp.systems;

import goalp.model.DeploymentRequest;

//Goal
public interface IDeploymentPlanner {

	DeploymentPlan doPlan(DeploymentRequest request, Agent agent) throws PlanSelectionException;

}
