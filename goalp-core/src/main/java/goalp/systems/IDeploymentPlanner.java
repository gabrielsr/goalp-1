package goalp.systems;

import java.util.List;

import goalp.model.Artifact;
import goalp.model.DeploymentRequest;

//Goal
public interface IDeploymentPlanner {

	public DeploymentPlan doPlan(DeploymentRequest request, Agent agent, List<Artifact> knownArtifacts) throws PlanSelectionException;

	DeploymentPlan doPlan(DeploymentRequest request, Agent agent) throws PlanSelectionException;

}
