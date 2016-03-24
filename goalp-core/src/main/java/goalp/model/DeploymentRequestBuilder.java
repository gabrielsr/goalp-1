package goalp.model;

public class DeploymentRequestBuilder {

	private DeploymentRequest deploymentRequest;

	private DeploymentRequestBuilder() {
		this.deploymentRequest = new DeploymentRequest();
	}

	public static DeploymentRequestBuilder create() {
		return new DeploymentRequestBuilder();
	}

	public DeploymentRequest build() {
		DeploymentRequest built = this.deploymentRequest;
		this.deploymentRequest = null;
		return built;
	}

	public DeploymentRequestBuilder addGoal(String goalIdentification) {
		this.deploymentRequest.getGoals().add((new Goal(goalIdentification)));
		return this;
	}
}
