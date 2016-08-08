package goalp.evaluation.plans;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import goalp.evaluation.goals.IExecuteExperiments;
import goalp.evaluation.model.Experiment;
import goalp.evaluation.model.Setup;
import goalp.exputil.ExperimentTimer;
import goalp.model.DeploymentRequest;
import goalp.model.DeploymentRequestBuilder;
import goalp.systems.Agent;
import goalp.systems.AgentBuilder;
import goalp.systems.DeploymentPlanningResult;
import goalp.systems.IDeploymentPlanner;
import goalp.systems.IRepository;
import goalp.systems.PlanSelectionException;
import goalp.systems.SimpleDeploymentPlanner;

@Named
public class ExecuteExperiment implements IExecuteExperiments {

	@Inject
	Logger log;
	
	@Inject
	ExperimentTimer timer;

	Setup setup;
	DeploymentRequest request;
	DeploymentPlanningResult resultPlan;
	String rootGoal = "br.unb.rootGoal:0.0.1";
	
	
	@Override
	public void accept(Experiment experiment) {
		
		// TODO: make it beautiful
		//	And
		//	.exec(setupEnvironmen, experiment)
		//	.exec(executeAndMesureExperiment(setup))
		timer.begin();
		setupEnvironment(experiment);
		timer.split("setup");
		executeExperiment();
		timer.split("execution");
		validateResult();
		timer.split("validation");
		experiment.setResult(experiment.getSpecification(), setup.repo.getSize(), timer.result());
		clean();
	}


	private void setupEnvironment(Experiment experiment) {
		IRepository repo = CreateSpecifiedRepository.exec(experiment.getSpecification(), rootGoal);
		
		IDeploymentPlanner planner = new SimpleDeploymentPlanner(repo);
		
		Agent agent = AgentBuilder.create()
				.addContext("display_capability")
				.build();
		
		this.setup = new Setup(repo, planner, agent);
		this.request = DeploymentRequestBuilder.create()
				.addGoal(rootGoal)
				.build();
		
	}

	private void executeExperiment() {
		
		try {
			resultPlan = setup.planner.doPlan(request,  setup.agent);
		} catch (PlanSelectionException e) {
			log.error("error planning:" + e.getMessage());
			log.error("for " + request.toString());
			log.error("with setup " + setup.toString());
		}
		
	}
	

	private void validateResult() {
		// TODO Auto-generated method stub
		
	}
	
	private void clean(){
		this.setup = null;
	}

}
