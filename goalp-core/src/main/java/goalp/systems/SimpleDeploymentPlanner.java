package goalp.systems;

import java.util.List;

import goalp.model.Artifact;
import goalp.model.DeploymentRequest;
import goalp.systems.DeploymentPlan.Status;

public class SimpleDeploymentPlanner implements IDeploymentPlanner {
	
	private List<Artifact> knownArtifacts;

	public SimpleDeploymentPlanner(List<Artifact> knownArtifacts) {
		this.knownArtifacts = knownArtifacts;

	}

	public SimpleDeploymentPlanner(Repository repo) {
		this.knownArtifacts = repo.getKnownArtifacts();
	}

	@Override
	public DeploymentPlan doPlan(DeploymentRequest request, Agent agent) throws PlanSelectionException {
		return doPlan(request, agent, knownArtifacts);
	}

	@Override
	public DeploymentPlan doPlan(DeploymentRequest request, Agent agent, List<Artifact> knownArtifacts)
			throws PlanSelectionException {
		DeploymentPlan plan = new DeploymentPlan();
		plan.setSelectedArtifacts(knownArtifacts);
		plan.setStatus(Status.SUCCESS);
		return plan;
	}

}
