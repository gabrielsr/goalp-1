package goalp.evaluation.goals;

import java.util.List;

import goalp.evaluation.model.Experiment;

public class Evaluate implements IEvaluate {

	ICreateExperiments create;
	IExecuteExperiments execute;
	IReportResult report;
	
	@Override
	public void exec(){
		List<Experiment> experiments = create.exec();
		experiments.forEach(execute);
		report.exec(experiments);
 	}
	
}
