package goalp.model;

import java.util.ArrayList;
import java.util.List;

public class DeploymentRequest {

	
	List<Goal> goals;

	public List<Goal> getGoals() {
		if(goals == null){
			goals = new ArrayList<>();
		}
		return goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	@Override
	public String toString() {
		return "DeploymentRequest [goals=" + goals + "]";
	}
	
}
