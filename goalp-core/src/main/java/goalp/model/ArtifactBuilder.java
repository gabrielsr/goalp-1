package goalp.model;

import java.util.List;

public class ArtifactBuilder {
	
	private Artifact artifact;
	
	private ArtifactBuilder(){
		this.artifact = new Artifact();
	}
	
	public static ArtifactBuilder create(){
		return new ArtifactBuilder();
	}
	
	public ArtifactBuilder plans(List<Plan> plans){
		this.artifact.plans = plans;
		return this;
	}
	
	public Artifact build(){
		Artifact built = this.artifact;
		this.artifact = null;
		return built;
	}
}
