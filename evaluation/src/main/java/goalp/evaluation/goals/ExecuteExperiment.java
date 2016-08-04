package goalp.evaluation.goals;

import goalp.evaluation.ExperimentTimer;
import goalp.evaluation.model.Experiment;
import goalp.evaluation.model.Setup;
import goalp.evaluation.plans.CreateSpecifiedRepository;
import goalp.model.DeploymentRequest;
import goalp.model.DeploymentRequestBuilder;
import goalp.systems.Agent;
import goalp.systems.AgentBuilder;
import goalp.systems.DeploymentPlanningResult;
import goalp.systems.IDeploymentPlanner;
import goalp.systems.IRepository;
import goalp.systems.PlanSelectionException;
import goalp.systems.SimpleDeploymentPlanner;

public class ExecuteExperiment implements IExecuteExperiments {

	Setup setup;
	ExperimentTimer timer;
	DeploymentRequest request;
	DeploymentPlanningResult result;
	String rootGoal = "br.unb.rootGoal:0.0.1";
	
	
	@Override
	public void accept(Experiment experiment) {
		
		// TODO: make it beautiful
		//	And
		//	.exec(setupEnvironmen, experiment)
		//	.exec(executeAndMesureExperiment(setup))
		timer.begin();
		setupEnvironment(experiment);
		timer.split("finish setup");
		executeExperiment();
		timer.split("finish executed");
		validateResult();
		timer.split("finish validation");
		experiment.setResult(timer.result());
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
			result = setup.planner.doPlan(request,  setup.agent);
		} catch (PlanSelectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	private void validateResult() {
		// TODO Auto-generated method stub
		
	}
	
	private void clean(){
		this.setup = null;
	}

}
