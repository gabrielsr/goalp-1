package goalp.depl.planning;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import goalp.model.Agent;
import goalp.model.DeploymentRequest;
import goalp.model.Plan;
import goalp.systems.PlanSelectionException;
import goalp.systems.PlanSelector;
import goalp.systems.SimplePlanSelector;

public class PlanResolutionTest {
	
	
	Agent agent;
	
	List<Plan> knownPlans;
	
	@Before
	public void setUp() {
		knownPlans = new ArrayList<>();
		
		//knownPlans.add();
		
	}
	
	@Test(expected = PlanSelectionException.class)
	public void knownArtifactsNullTest() throws PlanSelectionException {
		DeploymentRequest request = new DeploymentRequest();
		
		
		PlanSelector selector = new SimplePlanSelector();
		List<Plan> selected = selector.select(request, agent, null);
	}

	@Test
	public void simplestArtifactSelectionTest() throws PlanSelectionException {
		DeploymentRequest request = new DeploymentRequest();
		
		
		PlanSelector selector = new SimplePlanSelector();
		List<Plan> selected = selector.select(request, agent, knownPlans);
		Assert.assertNotNull(selected);
		
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
