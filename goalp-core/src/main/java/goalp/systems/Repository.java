package goalp.systems;

import java.util.ArrayList;
import java.util.List;

import goalp.model.Artifact;

public class Repository {

	protected List<Artifact> knownArtifacts;

	public List<Artifact> getKnownArtifacts() {
		if(knownArtifacts == null){
			knownArtifacts = new ArrayList<>();
		}
		return knownArtifacts;
	}

	public void setKnownArtifacts(List<Artifact> knownArtifacts) {
		this.knownArtifacts = knownArtifacts;
	}

}
