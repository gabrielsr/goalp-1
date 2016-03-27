package goalp.systems;

import java.util.List;

import goalp.model.Artifact;
import goalp.model.Goal;

public interface IRepository {

	void addArtifact(Artifact artifact);

	List<Artifact> getArtifactsThatProvidesGoal(Goal goal);

}