package goalp.evaluation.plans;

import goalp.systems.IRepository;

public class RepositorySetup {

	private IRepository repository;
	
	private String rootGoal;

	public IRepository getRepository() {
		return repository;
	}

	public void setRepository(IRepository repository) {
		this.repository = repository;
	}

	public String getRootGoal() {
		return rootGoal;
	}

	public void setRootGoal(String rootGoal) {
		this.rootGoal = rootGoal;
	}
	
	
}
