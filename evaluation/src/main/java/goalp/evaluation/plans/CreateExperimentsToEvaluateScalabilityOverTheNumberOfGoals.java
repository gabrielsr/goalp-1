package goalp.evaluation.plans;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import goalp.evaluation.goals.ICreateExperiments;
import goalp.evaluation.model.ExpSpecification;
import goalp.evaluation.model.Experiment;
import goalp.evaluation.model.ExperimentBuilder;

@Named
public class CreateExperimentsToEvaluateScalabilityOverTheNumberOfGoals implements ICreateExperiments {

	@Override
	public List<Experiment> exec() {
		return createExperiments(0,1000,10);
	}
	public List<Experiment> createExperiments(int from, int to, int step){
		List<Experiment> experiments = new ArrayList<>();
		//TODO for each repo size
		createSpecs(from, to, step).forEach(( spec ) -> {
			Experiment exp = ExperimentBuilder.create()
				.spec(spec)
				.build();
			experiments.add(exp);
		});
		return experiments;
		
	}
	
	public List<ExpSpecification> createSpecs(int from, int to, int step){
		List<ExpSpecification> specs = new ArrayList<>();
		for(int i = from; i<=to; i+=step){
			ExpSpecification spec = new ExpSpecification();
			
			//TODO, it creates only 'square' beaded curtain repositories, without duplicatin
			int widthAndDepth = (int) Math.sqrt(((Integer) i).doubleValue());

			spec.setRepoSpec(widthAndDepth, widthAndDepth, 0);
			specs.add(spec);
		}
		return specs;
	}

}
