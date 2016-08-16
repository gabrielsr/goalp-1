package goalp.evaluation.plans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import goalp.evaluation.goals.ICreateExperiments;
import goalp.evaluation.model.ExecSpec;
import goalp.evaluation.model.ExecSpecBuilder;
import goalp.evaluation.model.Experiment;
import goalp.evaluation.model.ExperimentBuilder;
import goalp.exputil.RandomPrismRepositoryUtil;

//@Named
public class CreateExperimentsToEvaluateScalabilityOverNumberOfGoalsAndContexts implements ICreateExperiments {

	@Override
	public List<Experiment> exec() {
		List<Experiment> experiments = new ArrayList<>();
		int p = 5;
		int k = 2;
		
		for(int i =5; i<=20; i+=5){ //TODO i<=20
			String[] contextSpace = getContextSpace(i);
			experiments.add(createContextExperiment(contextSpace, p, k));
		}
		
		return experiments;
	}

	private Experiment createContextExperiment(String[] contexts, int p, int k) {
		final String factorLavel = "numberOfGoals";
		List<String> agentContext = RandomPrismRepositoryUtil.listOfPElements(contexts, p);
		
		ExperimentBuilder expBuilder = ExperimentBuilder
				.create()
				.setName(contexts.length+ "contexts [p="+p+", k="+k+"]")
				.setFactor(factorLavel)
				.setResponseVariable("execution_time")
				.putRepoSpec("depth", 3)
				.putRepoSpec("numOfDependencies", 7)
				.putRepoSpec("numberOfTrees", 30)
				.putRepoSpec("contextSpace", Arrays.asList(contexts))
				.putRepoSpec("agentContext", agentContext)
				.putRepoSpec("contextVariabilityP",p)
				.putRepoSpec("contextVariabilityK",k);
		
		ExecSpec model = ExecSpecBuilder.create()
				.put("type", 0)
			.build();
	
		
		//create execution specification from a range of k combination, from 0
		addExecSpecsWithInRangeSetter(model, 0, 16, 2, (spec, contextsInEachArtifact) ->{
			spec.getRepoSpec().put(factorLavel, contextsInEachArtifact);
		}, expBuilder);
		
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
	
	
	private String[] getContextSpace(int size){
		String[] space = new String[size];
		for(int i = 0; i< size; i++){
			space[i] = "C"+i;
		}
		return space;
	}
	
}
