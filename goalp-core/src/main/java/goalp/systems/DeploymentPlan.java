package goalp.systems;

import java.util.List;

import goalp.model.Artifact;

public class DeploymentPlan {

	protected List<Artifact> selectedArtifacts;

	protected Status status;
	
	/**
	 * Time of clock took in planning in milisecons.
	 */
	protected Long planDuration;

	public enum Status {
		SUCCESS, FAILURE
	}

	public List<Artifact> getSelectedArtifacts() {
		return selectedArtifacts;
	}

	public void setSelectedArtifacts(List<Artifact> selectedArtifacts) {
		this.selectedArtifacts = selectedArtifacts;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
