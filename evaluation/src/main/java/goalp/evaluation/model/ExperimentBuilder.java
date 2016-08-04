package goalp.evaluation.model;

public class ExperimentBuilder {
	
	protected Experiment experiment;
	
	protected ExperimentBuilder(){
		this.experiment = new Experiment();
	}
	
	public static ExperimentBuilder create(){
		return new ExperimentBuilder();
	}
	
	public Experiment build(){
		Experiment built = this.experiment;
		this.experiment = null;
		return built;
	}

	public ExperimentBuilder spec(ExpSpecification spec) {
		this.experiment.spec = spec;
		return this;
	}

}
