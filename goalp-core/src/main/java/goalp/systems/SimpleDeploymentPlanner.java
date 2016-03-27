package goalp.systems;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import goalp.model.Artifact;
import goalp.model.DeploymentRequest;
import goalp.model.Goal;
import goalp.systems.DeploymentPlan.Status;

public class SimpleDeploymentPlanner implements IDeploymentPlanner {
	
	private IRepository repository;

	public SimpleDeploymentPlanner(IRepository repository) {
		this.repository = repository;

	}

	@Override
	public DeploymentPlan doPlan(DeploymentRequest request, Agent agent)
			throws PlanSelectionException {
	
		if(request == null || agent == null){
			throw new InvalidDeploymentRequestException();
		}
		
		Date begin,end;
		
		begin = new Date();
		DeploymentPlan plan = makePlan(request, agent);
		end = new Date();
		plan.planDuration = begin.getTime() - end.getTime();
		
		return plan;
	}
	
	private DeploymentPlan makePlan(DeploymentRequest request, Agent agent)
			throws PlanSelectionException {
		DeploymentPlan plan = new DeploymentPlan();
		
		Status status = Status.SUCCESS;
		
		for(Goal goal: request.getGoals()){
			List<Artifact> candidateProviders = repository.getArtifactsThatProvidesGoal(goal);
			List<Artifact> selectedArtifacts = new ArrayList<>();
			if(!checkAndAddToSelected(agent, candidateProviders, selectedArtifacts)) {
				status = Status.FAILURE;
				break;
			}else{
				plan.getSelectedArtifacts().addAll(selectedArtifacts);
			}
			
		}
		
		plan.setStatus(status);
		return plan;
	}
	
	private boolean checkAndAddToSelected(Agent agent, List<Artifact> candidates, List<Artifact> selectedArtifacts){
		if(candidates.isEmpty()){
			return false;
		}
		
		boolean result = false;
		
		for(Artifact candidate: candidates) {
			
			if(!checkContextRequirements(agent, candidate)){
				break;
			}else{
				// TODO check dependencies
				//List<IDependency> dependencies = candidate.getDependencies();
				//repository.getArtifactsThatProvidesGoal(goal);
				selectedArtifacts.add(candidate);
				result = true;
			}	
		}
		return result;
	}

	private boolean checkContextRequirements(Agent agent, Artifact artifact) {
		for(String contextReq: artifact.getContextRequirement()){
			if(agent.getComputingEnvironment().indexOf(contextReq)<0){
				return false;
			}
		}
		return true;
	}

}
