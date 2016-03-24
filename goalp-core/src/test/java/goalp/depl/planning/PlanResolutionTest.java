package goalp.depl.planning;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import goalp.model.Artifact;
import goalp.model.ArtifactBuilder;
import goalp.model.DeploymentRequest;
import goalp.model.DeploymentRequestBuilder;
import goalp.systems.Agent;
import goalp.systems.AgentBuilder;
import goalp.systems.DeploymentPlan;
import goalp.systems.IDeploymentPlanner;
import goalp.systems.PlanSelectionException;
import goalp.systems.Repository;
import goalp.systems.RepositoryBuilder;
import goalp.systems.SimpleDeploymentPlanner;

public class PlanResolutionTest {
	
	IDeploymentPlanner planner;
	
	Repository repo;
	
	@Before
	public void setUp() {
		
		repo = RepositoryBuilder.create()
			.addArtifact(
				ArtifactBuilder.create()
				.identification("br.unb:greater:0.0.1")
				.providesGoal("br.unb.greet")
				.build())
			.addArtifact(
				ArtifactBuilder.create()
				.identification("br.unb:alarm:0.0.1")
				.providesGoal("br.unb.alarm")
				.build())
			.build();
		
		planner = new SimpleDeploymentPlanner(repo);
	}
	
	@Test
	public void simplestArtifactSelectionTest() throws PlanSelectionException {
		DeploymentRequest request = DeploymentRequestBuilder.create()
				.addGoal("br.unb.greet")
				.build();
		

		List<Artifact> selected = planner.doPlan(request, null).getSelectedArtifacts();
		Assert.assertEquals(selected.size(), 1);
		Assert.assertEquals(selected.get(0).getIdentification(), "br.unb:greater:0.0.1");
	}
	
	@Test
	public void artifactSelectionWithResourceRequirementTest() throws PlanSelectionException {
		DeploymentRequest request = DeploymentRequestBuilder.create()
				.addGoal("br.unb.alarm")
				.build();
		
		Agent agent = AgentBuilder.create()
				.addContext("display")
				.build();
		
		DeploymentPlan plan = planner.doPlan(request, agent);
		Assert.assertEquals(plan.getStatus(), DeploymentPlan.Status.FAILURE);
	}
	
	@Test
	public void artifactSelectionWithDependencieTest() throws PlanSelectionException {
	}
	
	@Test
	public void artifactSelectionWithCyclicDependencieTest() throws PlanSelectionException {
	}

//	@Test(expected = PlanSelectionException.class)
//	public void knownArtifactsNullTest() throws PlanSelectionException {
//		DeploymentRequest request = new DeploymentRequest();
//		
//		
//		PlanSelector selector = new SimplePlanSelector();
//		List<Plan> selected = selector.select(request, agent, null);
//	}

}
