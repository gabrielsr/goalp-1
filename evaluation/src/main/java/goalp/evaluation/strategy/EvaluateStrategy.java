package goalp.evaluation.strategy;

import java.util.List;

import javax.inject.Inject;

import goalp.evaluation.goals.ICreateExperiments;
import goalp.evaluation.goals.IEvaluate;
import goalp.evaluation.goals.IExecuteExperiments;
import goalp.evaluation.goals.IReportResult;
import goalp.evaluation.model.Experiment;

public class EvaluateStrategy implements IEvaluate {

	//inject all
	@Inject
	ICreateExperiments create;
	
	@Inject
	IExecuteExperiments execute;
	
	@Inject
	IReportResult report;
	
	@Override
	public void exec(){
		List<Experiment> experiments = create.exec();
		experiments.forEach(execute);
		report.exec(experiments);
 	}
	
}
