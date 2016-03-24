package goalp.model;

public class ArtifactBuilder {
	
	private Artifact artifact;
	
	private ArtifactBuilder(){
		this.artifact = new Artifact();
	}
	
	public static ArtifactBuilder create(){
		return new ArtifactBuilder();
	}
	
	public Artifact build(){
		Artifact built = this.artifact;
		this.artifact = null;
		return built;
	}

	public ArtifactBuilder identification(String identification) {
		this.artifact.identification = identification;
		return this;
	}

	public ArtifactBuilder providesGoal(String goalIdentification){
		this.artifact.getDependencies().add((new Goal(goalIdentification)));
		return this;
	}
	
	public ArtifactBuilder provides(IDependency dependency){
		this.artifact.getDependencies().add(dependency);
		return this;
	}
	
	public ArtifactBuilder dependsOn(IDependency dependency){
		this.artifact.getDependencies().add(dependency);
		return this;
	}
}
