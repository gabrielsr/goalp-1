package goalp.evaluation.plans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import goalp.evaluation.model.ExecSpec;
import goalp.evaluation.model.ExecSpecBuilder;
import goalp.evaluation.model.PlanningExperiment;
import goalp.evaluation.model.PlanningExperimentBuilder;

//@Named
public class CreateExperimentsToEvaluateScalabilityOverNumberOfContexts{ // implements ICreateExperiments {

	//@Override
	public List<PlanningExperiment> exec() {
		List<PlanningExperiment> experiments = new ArrayList<>();
		experiments.add(createContextExperiment());
		return experiments;
	}

	private PlanningExperiment createContextExperiment() {
		String factorLavel = "contextsInEachArtifact";
		
		PlanningExperimentBuilder expBuilder = (PlanningExperimentBuilder) PlanningExperimentBuilder
				.create()
				.addFactor(factorLavel)
				.setResponseVariable("execution_time");
		
		// create a model execution specifications with default values
		String[] contexts = {"A","B","C","D"};//,"E","F","G","H","I","J","K","L"};
		List<String> contextSpace = Arrays.asList(contexts);
		
		ExecSpec model = ExecSpecBuilder.create()
				.put("depth", 3)
				.put("numOfDependencies", 10)
				.put("contextSpace", contextSpace)
				.put("duplication", 0)
				.put("type", 0)
			.build();
	
		
		//create execution specification from a range of k combination, from 0
		addExecSpecsWithInRangeSetter(model, 0, contextSpace.size(), 1, (spec, contextsInEachArtifact) ->{
			spec.put(factorLavel, contextsInEachArtifact);
		}, expBuilder);
		
		return expBuilder.build();
	}

	private void addExecSpecsWithInRangeSetter(ExecSpec model, int from, int to, int step,
			BiConsumer<ExecSpec, Integer> setter, PlanningExperimentBuilder parentBuilder) {
		for (int i = from; i <= to; i += step) {

			ExecSpec spec = model.clone();
			setter.accept(spec, i);
			parentBuilder.addSpec(spec);
		}
	}

}
