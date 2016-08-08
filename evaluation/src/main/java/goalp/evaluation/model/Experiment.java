package goalp.evaluation.model;

import java.util.List;

import goalp.evaluation.ExperimentTimerImpl.Split;

public class Experiment {

	private ExpSpecification spec;
	
	private ExpResult result;

	public ExpSpecification getSpecification() {
		return spec;
	}
	
	public void setSpecification(ExpSpecification spec){
		this.spec = spec;
	}

	public ExpResult getResult(){
		return this.result;
	}

	public void setResult(ExpResult result) {
		this.result = result;
	}

	public void setResult(ExpSpecification spec, int size, List<Split> splits) {
		this.result = new ExpResult(size, spec.getRepoSpec(), splits);
	}


}
