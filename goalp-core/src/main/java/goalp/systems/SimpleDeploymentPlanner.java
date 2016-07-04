package goalp.systems;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import goalp.model.Artifact;
import goalp.model.DeploymentRequest;
import goalp.model.Goal;

public class SimpleDeploymentPlanner implements IDeploymentPlanner {

	final static Logger logger = Logger.getLogger(SimpleDeploymentPlanner.class);
	
	private IRepository repository;

	public SimpleDeploymentPlanner(IRepository repository) {
		this.repository = repository;

	}

	@Override
	public DeploymentPlanningResult doPlan(DeploymentRequest request, Agent agent)
			throws PlanSelectionException {
	
		if(request == null || agent == null){
			throw new InvalidDeploymentRequestException();
		}
		
		DeploymentPlanningResult result = new DeploymentPlanningResult();
		try {
			makePlan(agent, request.getGoals(), result);
		}catch(Throwable e){
			result.stop();
			logger.error(e);
			e.printStackTrace();
			result.putFailure("Unespected Failure: " + e.toString() + "," + e.getMessage());
		}
		return result;
	}
	
	private void makePlan(Agent agent, List<Goal> goals, DeploymentPlanningResult result) {

		for(Goal goal: goals){
			makePlan(agent, goal, result);
		}

		result.stop();
	}
	
	private void makePlan(Agent agent, Goal goal, DeploymentPlanningResult result) {
	
		// Current result aready provides the required goal. 
		// it is the case when there is cycles in the deployment 
		if(result.getPlan().provides(goal)){
			return;
		}
		
		List<Artifact> candidateProviders = repository.getArtifactsThatProvidesGoal(goal);
		
		if(candidateProviders.isEmpty()){
			result.putFailure("no artifact provides " + goal.getIdentication());
			return;
		}
		
		List<DeploymentPlanningResult> alternativePlans = new ArrayList<>();
		for(Artifact candidate: candidateProviders) {
			
			if(!checkContextConditions(agent, candidate)){
				if(logger.isDebugEnabled()){
				    logger.debug("context conditions verification failure for " + candidate.getIdentification());
				}
				break;
			}else{
				//check dependencies
				//logs a debug message
				boolean allDependenciesSatisfied = true;
				DeploymentPlanningResult depencyResult = new DeploymentPlanningResult();
				depencyResult.incorporate(candidate);
				depencyResult.incorporate(result.getPlan());
				
				for(Goal dependency: candidate.getDependencies() ){
					DeploymentPlanningResult oneDepencyResult = new DeploymentPlanningResult();
					oneDepencyResult.incorporate(depencyResult.getPlan());
					
					makePlan(agent, dependency, oneDepencyResult);
					if(oneDepencyResult.isSuccessfull()){
						depencyResult.incorporate(oneDepencyResult.getPlan());
					}else {
						allDependenciesSatisfied = false;
						break;
					}
				}
				if(allDependenciesSatisfied){
					alternativePlans.add(depencyResult);
				}
			}
		}
		if(alternativePlans.isEmpty()){
			result.putFailure("no deployment plan that matches context conditions for " + goal.getIdentication());
			return;
		}
		
		DeploymentPlanningResult chosen = chooseAlternative(alternativePlans);
		
		result.incorporate(chosen.getPlan());
	}

	private DeploymentPlanningResult chooseAlternative(List<DeploymentPlanningResult> alternativePlans) {
		DeploymentPlanningResult chosen = alternativePlans.get(0);
		for(DeploymentPlanningResult alternative:alternativePlans){
			if(alternative.getPlan().getSelectedArtifacts().size() <
					chosen.getPlan().getSelectedArtifacts().size()){
				chosen = alternative;
			}
		}
		return chosen;
	}
	
	private boolean checkContextConditions(Agent agent, Artifact artifact) {
		for(String contextReq: artifact.getContextConditions()){
			if(agent.getContext().indexOf(contextReq)<0){
				return false;
			}
		}
		return true;
	}

}
