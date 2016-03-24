package goalp.systems;

import java.util.List;

import goalp.model.Agent;
import goalp.model.Artifact;
import goalp.model.DeploymentRequest;

//Goal
public interface IDeploymentPlanner {

	public List<Artifact> select(DeploymentRequest request, Agent agent, List<Artifact> knownArtifacts) throws PlanSelectionException;

}
