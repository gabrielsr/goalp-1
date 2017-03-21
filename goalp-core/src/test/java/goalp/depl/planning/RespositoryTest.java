package goalp.depl.planning;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import goalp.model.Artifact;
import goalp.model.ArtifactBuilder;
import goalp.model.Goal;
import goalp.systems.IRepository;
import goalp.systems.RepositoryBuilder;

public class RespositoryTest {

	IRepository repo;
	
	@Before
	public void setUp() {
		
		repo = RepositoryBuilder.create()
			.add(
				ArtifactBuilder.create()
				.identification("br.unb:greater:0.0.1")
				.provides("br.unb.greet")
				.build())
			.add(
				ArtifactBuilder.create()
				.identification("br.unb:alarm:0.0.1")
				.provides("br.unb.alarm")
				.build())
			.build();
	}
	
	@Test
	public void testGetArtifactsThatProvidesGoal() {

		Goal goal = new Goal("br.unb.greet");
		List<Artifact> artifacts = repo.getArtifactsThatProvidesGoal(goal);

		Assert.assertEquals(1, artifacts.size());
		Assert.assertEquals("br.unb:greater:0.0.1",artifacts.get(0).getIdentification());
		
	}

}
