package goalp.evaluation.goals;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;

import goalp.evaluation.model.Experiment;

public class EvaluateStrategy implements IEvaluate {

	@Inject
	ICreateExperiments create;
	
	@Inject
	IExecuteExperiments execute;
	
	@Inject
	IReportResult report;
	
	
	private @Inject Logger log;
	
	
	@Override
	public void exec(){
		List<Experiment> experiments = create.exec();
		experiments.forEach(execute);
		report.exec(experiments);
 	}
	
}
