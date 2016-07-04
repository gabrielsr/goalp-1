package goalp.depl.planning;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import goalp.model.ArtifactBuilder;
import goalp.model.DeploymentRequest;
import goalp.model.DeploymentRequestBuilder;
import goalp.systems.Agent;
import goalp.systems.AgentBuilder;
import goalp.systems.DeploymentPlanningResult;
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
				.provides("br.unb.greet")
				.condition("display_capability")
				.build())
			.addArtifact(
				ArtifactBuilder.create()
				.identification("br.unb:alarm:0.0.1")
				.provides("br.unb.alarm")
				.condition("sound_capability")
				.build())
			.addArtifact(
				ArtifactBuilder.create()
				.identification("br.unb:display_my_position:0.0.1")
				.provides("br.unb.display_my_position")
				.condition("display_capability")
				.dependsOn("br.unb:getPositionByGPS:0.0.1")
				.dependsOn("br.unb:mapView:0.0.1")
				.build())
			.addArtifact(
				ArtifactBuilder.create()
				.identification("br.unb:getPositionByGPS:0.0.1")
				.condition("gps_capability")
				.build())
			.addArtifact(
				ArtifactBuilder.create()
				.identification("br.unb:mapView:0.0.1")
				.condition("display_capability")
				.build())
			.build();
		
		planner = new SimpleDeploymentPlanner(repo);
		
		agent = AgentBuilder.create()
				.addContext("display_capability")
				.build();
	}
	
	@Test
	public void simplestArtifactSelectionTest() throws PlanSelectionException {
		DeploymentRequest request = DeploymentRequestBuilder.create()
				.addGoal("br.unb.greet")
				.build();
		

		DeploymentPlanningResult result = planner.doPlan(request, agent);
		Assert.assertTrue(result.isSuccessfull());
		Assert.assertEquals(1, result.getPlanSize());
		Assert.assertTrue(result.isPresentInThePlan("br.unb:greater:0.0.1"));
	}
	
	@Test
	public void artifactSelectionWithResourceRequirementTest() throws PlanSelectionException {
		DeploymentRequest request = DeploymentRequestBuilder.create()
				.addGoal("br.unb.alarm")
				.build();
		
		Agent agent = AgentBuilder.create()
				.addContext("display")
				.build();
		
		DeploymentPlanningResult result = planner.doPlan(request, agent);
		Assert.assertFalse(result.isSuccessfull());
	}
	
	@Test
	public void artifactSelectionWithDependencieTest() throws PlanSelectionException {
		DeploymentRequest request = DeploymentRequestBuilder.create()
			.addGoal("br.unb.display_my_position")
			.build();
		
		Agent agent = AgentBuilder.create()
			.addContext("display_capability")
			.addContext("gps_capability")
			.build();
		
		DeploymentPlanningResult result = planner.doPlan(request, agent);
		Assert.assertTrue(result.isSuccessfull());
		Assert.assertEquals(3, result.getPlanSize());
	}
	
	@Test
	public void artifactSelectionWithCyclicDependencieTest() throws PlanSelectionException {
	
		IRepository repoWithCycle = RepositoryBuilder.create()
				.addArtifact(
					ArtifactBuilder.create()
					.identification("br.unb:goalA:0.0.1")
					.provides("br.unb.goalA")
					.dependsOn("br.unb.goalB")
					.build())
				.addArtifact(
					ArtifactBuilder.create()
					.identification("br.unb:goalB:0.0.1")
					.provides("br.unb.goalB")
					.dependsOn("br.unb.goalA")
					.build())
				.build();
		
		DeploymentRequest request = DeploymentRequestBuilder.create()
				.addGoal("br.unb.goalA")
				.build();
			
		Agent agent = AgentBuilder.create()
			.build();
		
		IDeploymentPlanner planner = new SimpleDeploymentPlanner(repoWithCycle);
		
		DeploymentPlanningResult result = planner.doPlan(request, agent);
		Assert.assertTrue(result.isSuccessfull());
		Assert.assertEquals(2, result.getPlanSize());
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
