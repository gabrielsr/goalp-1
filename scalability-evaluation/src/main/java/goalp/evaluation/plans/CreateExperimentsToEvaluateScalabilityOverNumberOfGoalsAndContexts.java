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
public class CreateExperimentsToEvaluateScalabilityOverNumberOfGoalsAndContexts {// implements ICreateExperiments {

	public class Params { 
		int depth = 2;
		int numOfDependencies = 5;
		int contextSpaceSize = 20;
		int contextVariabilityP = 5;
		int contextVariabilityK = 2;
		String[] contextSpace;
	}
	
	//@Override
	public List<PlanningExperiment> exec() {
		List<PlanningExperiment> experiments = new ArrayList<>();

		for(int i =5; i<=20; i+=5){ //TODO i<=20
			Params params = new Params();
			params.contextSpace = getContextSpace(i);
			experiments.add(createContextExperiment(params));
		}
		
		return experiments;
	}

	private PlanningExperiment createContextExperiment(Params params) {
		final String factorLavel = "numberOfGoals";
		//List<String> agentContext = RandomPrismRepositoryUtil.listOfPElements(params.contextSpace, p);
		List<String> agentContext = Arrays.asList(params.contextSpace);
		
		PlanningExperimentBuilder expBuilder = ((PlanningExperimentBuilder) PlanningExperimentBuilder
				.create()
				.setName(agentContext.size() + "contexts [p="+params.contextVariabilityP+", k="+params.contextVariabilityK+"]")
				.addFactor(factorLavel)
				.setResponseVariable("execution_time"))
				.putRepoSpec("depth", 3)
				.putRepoSpec("numOfDependencies", 7)
				.putRepoSpec("numberOfTrees", 30)
				.putRepoSpec("contextSpace", Arrays.asList(params.contextSpace))
				.putRepoSpec("agentContext", agentContext)
				.putRepoSpec("contextVariabilityP",params.contextVariabilityP)
				.putRepoSpec("contextVariabilityK",params.contextVariabilityK);
		
		ExecSpec model = ExecSpecBuilder.create()
				.put("type", 0)
			.build();
	
		
		//create execution specification from a range of k combination, from 0
		addExecSpecsWithInRangeSetter(model, 0, 16, 2, (spec, contextsInEachArtifact) ->{
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
	
	
	private String[] getContextSpace(int size){
		String[] space = new String[size];
		for(int i = 0; i< size; i++){
			space[i] = "C"+i;
		}
		return space;
	}
	
}
