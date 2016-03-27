package goalp.depl.planning;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import goalp.model.ArtifactBuilder;
import goalp.model.DeploymentRequest;
import goalp.model.DeploymentRequestBuilder;
import goalp.systems.Agent;
import goalp.systems.AgentBuilder;
import goalp.systems.DeploymentPlan;
import goalp.systems.DeploymentPlan.Status;
import goalp.systems.IDeploymentPlanner;
import goalp.systems.IRepository;
import goalp.systems.PlanSelectionException;
import goalp.systems.RepositoryBuilder;
import goalp.systems.SimpleDeploymentPlanner;

public class PlanResolutionTest {
	
	IDeploymentPlanner planner;
	
	IRepository repo;
	
	Agent agent;
	
	@Before
	public void setUp() {
		
		repo = RepositoryBuilder.create()
			.addArtifact(
				ArtifactBuilder.create()
				.identification("br.unb:greater:0.0.1")
				.providesGoal("br.unb.greet")
				.requires("display_cability")
				.build())
			.addArtifact(
				ArtifactBuilder.create()
				.identification("br.unb:alarm:0.0.1")
				.providesGoal("br.unb.alarm")
				.requires("sound_capability")
				.build())
			.build();
		
		agent = AgentBuilder.create()
				.addContext("display_cability")
				.build();
		
		planner = new SimpleDeploymentPlanner(repo);
	}
	
	@Test
	public void simplestArtifactSelectionTest() throws PlanSelectionException {
		DeploymentRequest request = DeploymentRequestBuilder.create()
				.addGoal("br.unb.greet")
				.build();
		

		DeploymentPlan plan = planner.doPlan(request, agent);
		Assert.assertEquals(Status.SUCCESS, plan.getStatus());
		Assert.assertEquals(1, plan.getSelectedArtifacts().size());
		Assert.assertEquals("br.unb:greater:0.0.1", plan.getSelectedArtifacts().get(0).getIdentification());
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
		Assert.assertEquals(DeploymentPlan.Status.FAILURE, plan.getStatus());
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
