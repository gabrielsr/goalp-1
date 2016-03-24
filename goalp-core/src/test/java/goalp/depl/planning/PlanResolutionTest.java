package goalp.depl.planning;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import goalp.model.Artifact;
import goalp.model.ArtifactBuilder;
import goalp.model.DeploymentRequest;
import goalp.model.DeploymentRequestBuilder;
import goalp.systems.IDeploymentPlanner;
import goalp.systems.PlanSelectionException;
import goalp.systems.SimpleDeploymentPlanner;

public class PlanResolutionTest {
	
//	
//	Agent agent;
//	
//	List<Plan> knownPlans;
//	
	@Before
	public void setUp() {
	
	}
	
//	@Test(expected = PlanSelectionException.class)
//	public void knownArtifactsNullTest() throws PlanSelectionException {
//		DeploymentRequest request = new DeploymentRequest();
//		
//		
//		PlanSelector selector = new SimplePlanSelector();
//		List<Plan> selected = selector.select(request, agent, null);
//	}

	@Test
	public void simplestArtifactSelectionTest() throws PlanSelectionException {
		DeploymentRequest request = DeploymentRequestBuilder.create()
				.addGoal("br.unb.greet")
				.build();
		
		Artifact artifact = ArtifactBuilder.create()
				.identification("greater.jar")
				.providesGoal("br.unb.greet")
				.build();
		
		List<Artifact> knownArtifacts = new ArrayList<>();
		knownArtifacts.add(artifact);
		
		IDeploymentPlanner planner = new SimpleDeploymentPlanner();
		List<Artifact> selected = planner.select(request, null, knownArtifacts);
		Assert.assertEquals(selected.get(0).getIdentification(), "greater.jar");
	}
	
	@Test
	public void artifactSelectionWithResourceRequirementTest() throws PlanSelectionException {
	}
	
	@Test
	public void artifactSelectionWithDependencieTest() throws PlanSelectionException {
	}
	
	@Test
	public void artifactSelectionWithCyclicDependencieTest() throws PlanSelectionException {
	}

}
