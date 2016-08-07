package goalp.exputil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import goalp.Conf;
import goalp.Conf.Keys;
import goalp.evaluation.goals.IReportResult;
import goalp.evaluation.model.Experiment;

@Named
public class ReportResultToEpsGraph implements IReportResult {


	@Override
	public void exec(List<Experiment> experiments) {
		
		//create dataset
		DataSetBuilder<Number> dsbuilder = DataSetBuilder.create();
		
		for(Experiment exp:experiments){
			long depth = exp.getSpecification().getRepoSpec().getDepth();
			long nanoSecs = exp.getResult().getDuration("execution");
			Double milisecs = nanoToMiliseconds(nanoSecs);
			dsbuilder.addPoint(depth, milisecs);
		}
		
		//create graph
		PlotBuilder.create()
		.asEps(Conf.get(Keys.RESULT_FILE) + ".eps")
		.xLabel("Artifacts")
		.yLabel("Time (ms)")
		.addDataSet(dsbuilder.build())
		.plot();

	}
	
	private Double nanoToMiliseconds(long nanoSecs) {
		Double value = (1.0d/1000.0d);
		value *= nanoSecs;
		return value;
	}

	public void experimentToString(BufferedWriter writer, Experiment exp){
		String experimentReport =  exp.getSpecification().numberOfGoals + "," +
				exp.getSpecification().repoSpec + ","
				+ "\n";
		try {
			
			writer.write(experimentReport);
			
		}catch(IOException e){
			throw new IllegalStateException(e);
		}
	}
	
}
