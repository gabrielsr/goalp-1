package goalp.exputil;

import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import com.panayotis.gnuplot.dataset.Point;

import goalp.Conf;
import goalp.Conf.Keys;
import goalp.evaluation.goals.IReportResult;
import goalp.evaluation.model.Execution;
import goalp.evaluation.model.Experiment;

/**
 *  Context Requirement: gnuplot installed
 */
@Named
public class ReportResultToEpsGraph implements IReportResult {
	
	
	@Inject
	Logger log;

	@Override
	public void exec(List<Experiment> experiments) {
		experiments.forEach((exp) ->{
			List<String> factors = exp.getEvaluation().getFactorList();
			if(factors.size() == 0){
				throw new IllegalStateException("experiment factors was not initialized");
			}else if(factors.size() == 1){
				
				String factor = factors.get(0);
				String responseVariable = ((Experiment)exp).getEvaluation().getResponseVariable();
				log.info("ploting {0} vs (1)", factor, responseVariable);
				
				//create graph (factor vs result) for the experiment execution list
				createGraph(exp.getExecutions(), (exec) -> {
					Number factorValue = exec.getEvaluation().getFactors().get(factor);
					Number response = exec.getEvaluation().getResponseValue();
					return new Point<Number>(factorValue, nanoToMiliseconds(response));
				}, factor);
				
			}else if(factors.size()>1){
				throw new IllegalStateException("multi factors experiment report was requested but implemented");
			}

		});
	}
	
		
	private void createGraph(List<Execution> executions,  Function<Execution, Point<Number>> mapper, String name) {
		
		//create dataset
		DataSetBuilder<Number> dsbuilder = DataSetBuilder.create();
		
		for(Execution exec:executions){
			
			dsbuilder.addPoint(mapper.apply(exec));
		}
		
		//create graph
		PlotBuilder.create()
		.asEps(Conf.get(Keys.RESULT_FILE) + name + ".eps")
		.xLabel("Artifacts")
		.yLabel("Time (ms)")
		.addDataSet(dsbuilder.build())
		.plot();

	}
	
	private Double nanoToMiliseconds(Number nanoSecs) {
		Double value = (1.0d/1000.0d);
		value *= nanoSecs.longValue();
		return value;
	}	
}
