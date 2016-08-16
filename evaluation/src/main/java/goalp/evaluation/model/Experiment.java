package goalp.evaluation.model;

import java.util.ArrayList;
import java.util.List;

public class Experiment {
	
	public String name;
	
	private EvaluationComponent evaluation;
		
	private List<Execution> executions;
	
	private RepoSpec repoSpec;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
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

	public RepoSpec getRepoSpec() {
		if(repoSpec == null){
			repoSpec = new RepoSpec();
		}
		return repoSpec;
	}

	

}
