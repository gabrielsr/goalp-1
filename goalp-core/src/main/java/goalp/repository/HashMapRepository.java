package goalp.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import goalp.model.Artifact;
import goalp.model.Goal;
import goalp.systems.IRepository;

public class HashMapRepository implements IRepository {

	protected HashMap<String, Set<Artifact>> knownArtifacts;
	
	int repoSize = 0;

	/* (non-Javadoc)
	 * @see goalp.systems.IRepository#getKnownArtifacts()
	 */
	protected HashMap<String, Set<Artifact>> getKnownArtifacts() {
		if(knownArtifacts == null){
			knownArtifacts = new HashMap<>();
		}
		return knownArtifacts;
	}

	@Override
	public void addArtifact(Artifact artifact) {
		repoSize++;
		artifact.getProvide().forEach(goal ->{
			put(goal, artifact);
		});
	}
	
	private void put(Goal goal, Artifact artifact) {
		Set<Artifact> list = getKnownArtifacts().get(goal.getIdentication());
		if (list == null) {
			list = new HashSet<>();
			getKnownArtifacts().put(goal.getIdentication(), list);
		}
		list.add(artifact);
	}
	
	public int getSize(){
		return repoSize;
	}
	
	/* (non-Javadoc)
	 * @see goalp.systems.IRepository#getArtifactsThatProvideGoal(java.lang.String)
	 */
	@Override
	public List<Artifact>  getArtifactsThatProvidesGoal(Goal goal){
		List<Artifact> artifacts = new ArrayList<>();
		Set<Artifact> artifactsSet = getKnownArtifacts().get(goal.getIdentication());
		if(artifactsSet != null) {
			artifacts.addAll(artifactsSet);
		}
		return artifacts;
	}

}