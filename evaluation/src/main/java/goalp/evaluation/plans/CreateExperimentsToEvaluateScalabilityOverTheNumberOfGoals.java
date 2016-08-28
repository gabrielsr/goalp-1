package goalp.evaluation.plans;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import goalp.evaluation.model.ExecSpec;
import goalp.evaluation.model.ExecSpecBuilder;
import goalp.evaluation.model.Experiment;
import goalp.evaluation.model.ExperimentBuilder;

//@Named
public class CreateExperimentsToEvaluateScalabilityOverTheNumberOfGoals {// implements ICreateExperiments {

	//@Override
	public List<Experiment> exec() {
		List<Experiment> experiments = new ArrayList<>();
		experiments.add(createDepthExperiment(0, 100, 1));
		experiments.add(createWidthExperiment(0, 100, 1));
		return experiments;
	}

	private Experiment createDepthExperiment(int from, int to, int step) {
		ExperimentBuilder expBuilder = ExperimentBuilder
				.create()
				.addFactor("depth")
				.setResponseVariable("execution_time");
		
		// create a model execution specifications with default values
		ExecSpec model = ExecSpecBuilder.create()
				.put("width", 15)
				.put("duplication", 0)
				.put("type", 0)
			.build();
		
		//create execution specification from a range os depths
		addExecSpecsWithInRangeSetter(model, from, to, step, (spec, depth) ->{
			spec.put("depth", depth);
		}, expBuilder);
		
		return expBuilder.build();
	}

	private Experiment createWidthExperiment(int from, int to, int step) {
		ExperimentBuilder expBuilder = ExperimentBuilder
			.create()
			.addFactor("width")
			.setResponseVariable("execution_time");
		
		// create a model execution specifications with default values
		ExecSpec model = ExecSpecBuilder.create()
				.put("depth", 15)
				.put("duplication", 0)
				.put("type", 0)
			.build();
		
		
		//create execution specification from a range os depths
		addExecSpecsWithInRangeSetter(model, from, to, step, (spec, width) ->{
			spec.put("width", width);
		}, expBuilder);
		
		//add exec specs to the experiment and return
		return expBuilder.build();
	}

	private void addExecSpecsWithInRangeSetter(ExecSpec model, int from, int to, int step,
			BiConsumer<ExecSpec, Integer> setter, ExperimentBuilder parentBuilder) {
		for (int i = from; i <= to; i += step) {

			ExecSpec spec = model.clone();
			setter.accept(spec, i);
			parentBuilder.addSpec(spec);
		}
	}

}
