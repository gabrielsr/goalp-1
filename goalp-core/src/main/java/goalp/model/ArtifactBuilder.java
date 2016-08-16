package goalp.model;

public class ArtifactBuilder {
	
	protected Artifact artifact;
	
	protected ArtifactBuilder(){
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
		this.artifact.getProvide().add((new Goal(identification)));
		return this;
	}

	public ArtifactBuilder provides(String identification){
		this.artifact.getProvide().add((new Goal(identification)));
		return this;
	}
	
	public ArtifactBuilder dependsOn(String identification){
		this.artifact.getDependencies().add(new Goal(identification));
		return this;
	}
	
	public ArtifactBuilder dependsOn(String[] identifications){
		for(String identification:identifications){
			dependsOn(identification);
		}
		return this;
	}
	
	
	public ArtifactBuilder condition(String requirement){
		if(requirement != null){
			this.artifact.getContextConditions().add(requirement);			
		}
		return this;
	}

}
