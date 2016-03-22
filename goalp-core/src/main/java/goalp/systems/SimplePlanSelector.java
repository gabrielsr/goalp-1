package goalp.systems;

import java.util.ArrayList;
import java.util.List;

import goalp.model.Agent;
import goalp.model.DeploymentRequest;
import goalp.model.Plan;

public class SimplePlanSelector implements PlanSelector{

	@Override
	public List<Plan> select(DeploymentRequest request, Agent agent, List<Plan> knownPlans)
			throws PlanSelectionException {
		if(knownPlans==null){
			throw new PlanSelectionException("knownArtifacts is null");
		}
		
		List<Plan> selected = new ArrayList<>();
		for(Plan plan:knownPlans){
			selected.add(plan);
		}
		
		return selected;
	}

}
