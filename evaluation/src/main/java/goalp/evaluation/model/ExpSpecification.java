package goalp.evaluation.model;

public class ExpSpecification {

	public int numberOfGoals;

	public RepoSpec repoSpec;
	
	public int getGoalNumber() {
		return numberOfGoals;
	}

	public RepoSpec getRepoSpec() {
		return repoSpec;
	}
	
	public void setRepoSpec(int width, int depth, int duplication){
		this.repoSpec = new RepoSpec(width, depth, duplication);
	}
	
	public class RepoSpec {

		int width, depth, duplication;
		
		public RepoSpec(int width, int depth, int duplication){
			this.width = width;
			this.depth = depth;
			this.duplication = duplication;
		}
		
		public int getWidth() {
			return width;
		}
		
		public int getDepth() {
			return depth;
		}
		
		public int getDuplication() {
			return duplication;
		}
	}
}
