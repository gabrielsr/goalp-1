package goalp.evaluation.model;

import java.util.ArrayList;
import java.util.List;

public class Experiment {
	
	
	private EvaluationComponent evaluation;
		
	private List<Execution> executions;

	public List<Execution> getExecutions() {
		if(executions == null){
			executions = new ArrayList<Execution>();
		}
		return executions;
	}
	

	public void toExecute(Execution execution) {
		getExecutions().add(execution);
	}

	public EvaluationComponent getEvaluation() {
		if(evaluation == null){
			evaluation = new EvaluationComponent();
		}
		return evaluation;
	}

	public void setEvaluation(EvaluationComponent evaluation) {
		this.evaluation = evaluation;
	}

}
