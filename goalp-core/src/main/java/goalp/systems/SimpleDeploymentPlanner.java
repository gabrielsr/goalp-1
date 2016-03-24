package goalp.systems;

import java.util.List;

import goalp.model.Agent;
import goalp.model.Artifact;
import goalp.model.DeploymentRequest;

public class SimpleDeploymentPlanner implements IDeploymentPlanner {

	@Override
	public List<Artifact> select(DeploymentRequest request, Agent agent, List<Artifact> knownArtifacts)
			throws PlanSelectionException {
		return knownArtifacts;
	}

}
