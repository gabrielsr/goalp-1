package goalp.systems;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import goalp.model.Artifact;

public class DeploymentPlanningResult {

	public DeploymentPlan plan;
	
	public List<String> failures;
	
	/**
	 * Time of clock took in planning in milisecons.
	 */
	protected Long planDuration;

	protected Date startTime;
	
	public DeploymentPlanningResult(){
		startTime = new Date();
	}
	
	public void stop(){
		planDuration = startTime.getTime() - (new Date()).getTime();
	}
	
	public boolean isSuccessfull(){
		if(getFailures().isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	public List<String> getFailures(){
		if(failures == null){
			failures = new ArrayList<>();
		}
		return failures;
	}
	
	public void putFailure(String failure){
		 getFailures().add(failure);
	}

	public void incorporate(DeploymentPlan subPlan) {
		getPlan().getSelectedArtifacts().addAll(subPlan.getSelectedArtifacts());
	}
	
	public void incorporate(Artifact artifact) {
		getPlan().getSelectedArtifacts().add(artifact);
	}
	
	public DeploymentPlan getPlan() {
		if(plan == null){
			plan = new DeploymentPlan();
		}
		return plan;
	}
	
	public int getPlanSize(){
		return getPlan().getSelectedArtifacts().size();
	}
	
	public boolean isPresentInThePlan(String identification){
		for(Artifact artifact:getPlan().getSelectedArtifacts()){
			if(artifact.getIdentification().equals(identification)){
				return true;
			}
		}
	
		return false;
	}


	
}
