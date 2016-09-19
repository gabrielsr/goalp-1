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
import goalp.evaluation.model.PlanningExperimentBuilder;

//@Named
public class CreateExperimentsToEvaluateScalabilityOverNumberOfGoalsAndContexts3d implements ICreateExperiments {


	public class Factor {
		String label;
		
		public Factor(String label){
			
		}
		
	}
	
	@Override
	public List<Experiment> exec() {
		List<Experiment> experiments = new ArrayList<>();
		experiments.add(createContextExperiment());
		return experiments;
	}

	private Experiment createContextExperiment() {
		//List<String> agentContext = RandomPrismRepositoryUtil.listOfPElements(params.contextSpace, p);

/*		public class Params { 
			int depth = 2;
			int numOfDependencies = 5;
			int contextSpaceSize = 20;
			int contextVariabilityP = 5;
			int contextVariabilityK = 2;
			String[] contextSpace;
			Factor[] factors = {new Factor("Variability"), "Plan Size"};
			String responseVar = "Time (ms)";
		}
		*/
		
		ExperimentBuilder expBuilder = ((PlanningExperimentBuilder)PlanningExperimentBuilder
				.create()
				.setName("Variability, Plan Size vs Planning Time (ms)")
				.setResponseVariable("Time (ms)")
				.addFactor("Variability")
				.addFactor("Plan size"))
				.putRepoSpec("depth", 2)
				.putRepoSpec("numOfDependencies", 5)
				.putRepoSpec("variabilityRange", 1, 10)
				.putRepoSpec("numberOfTrees", 100)
				.putRepoSpec("contextSpace", getContextSpace(20))
				.putRepoSpec("agentContext", getContextSpace(20))
				.putRepoSpec("contextVariabilityP",5)
				.putRepoSpec("contextVariabilityK",2);
		
		ExecSpec model = ExecSpecBuilder.create()
				.put("type", 0)
			.build();
	
		
		//create execution specification from a range of k combination, from 0
		addExecSpecsWithTwoInRangeSetter(model, 
				0, 100, 1, //goals
				1, 10, 1, //variability
				(spec, rangeValues) ->{
			spec.put("numberOfGoals", rangeValues[0]);
			spec.put("variability", rangeValues[1]);
		}, expBuilder);
		
		return expBuilder.build();
	}

	private void addExecSpecsWithTwoInRangeSetter(ExecSpec model, 
			int r1From, int r1To, int r1Step,
			int r2From, int r2To, int r2Step,
			BiConsumer<ExecSpec, Integer[]> setter, ExperimentBuilder parentBuilder){
		for (int i = r1From; i <= r1To; i += r1Step) {
			for (int j = r2From; j <= r2To; j += r2Step) {
				Integer[] params = {i,j};
				ExecSpec spec = model.clone();
				setter.accept(spec, params);
				parentBuilder.addSpec(spec);
			}
		}
	}
	
	
	private List<String> getContextSpace(int size){
		String[] space = new String[size];
		for(int i = 0; i< size; i++){
			space[i] = "C"+i;
		}
		return Arrays.asList(space);
	}
	
}
