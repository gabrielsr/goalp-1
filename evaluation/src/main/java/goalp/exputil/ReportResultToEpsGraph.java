package goalp.exputil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import javax.inject.Named;

import com.panayotis.gnuplot.dataset.Point;

import goalp.Conf;
import goalp.Conf.Keys;
import goalp.evaluation.goals.IReportResult;
import goalp.evaluation.model.Experiment;

@Named
public class ReportResultToEpsGraph implements IReportResult {


	@Override
	public void exec(List<Experiment> experiments) {
		createGraph(experiments, (exp) -> {
			long size = exp.getResult().getRepoSize();
			long nanoSecs = exp.getResult().getDuration("execution");
			return new Point<Number>(size, nanoSecs);
		}, "size");

		createGraph(experiments, (exp) -> {
			long depth = exp.getSpecification().getRepoSpec().getDepth();
			long nanoSecs = exp.getResult().getDuration("execution");
			return new Point<Number>(depth, nanoSecs);
		}, "depth");
		
		createGraph(experiments, (exp) -> {
			long width = exp.getSpecification().getRepoSpec().getWidth();
			long nanoSecs = exp.getResult().getDuration("execution");
			return new Point<Number>(width, nanoSecs);
		}, "width");
		
		createGraph(experiments, (exp) -> {
			long dupl = exp.getSpecification().getRepoSpec().getDuplication();
			long nanoSecs = exp.getResult().getDuration("execution");
			return new Point<Number>(dupl, nanoSecs);
		}, "dupl");
	}
	
		
	private void createGraph(List<Experiment> experiments,  Function<Experiment, Point> mapper, String name) {
		
		//create dataset
		DataSetBuilder<Number> dsbuilder = DataSetBuilder.create();
		
		for(Experiment exp:experiments){
			
			dsbuilder.addPoint(mapper.apply(exp));
		}
		
		//create graph
		PlotBuilder.create()
		.asEps(Conf.get(Keys.RESULT_FILE) + name + ".eps")
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
