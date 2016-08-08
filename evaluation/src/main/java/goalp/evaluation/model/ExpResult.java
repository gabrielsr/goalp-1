package goalp.evaluation.model;

import java.util.List;

import goalp.evaluation.ExperimentTimerImpl.Split;
import goalp.evaluation.model.ExpSpecification.RepoSpec;

public class ExpResult {
	

	int repoSize, depth, width, dupl;
	
	List<Split> splits;
	
	public ExpResult(int repoSize, int depth, int width, int dupl, List<Split> splits){
		this.repoSize = repoSize;
		this.depth = depth;
		this.width = width;
		this.dupl = dupl;
		this.splits =splits;
	}

	public ExpResult(List<Split> splits){
		this.splits = splits;
	}
	
	public ExpResult(int repoSize, RepoSpec repoSpec, List<Split> splits) {
		this(repoSize, repoSpec.depth, repoSpec.width, repoSpec.duplication, splits);
	}

	public Long getDuration(String label){
		for(Split split: splits){
			if(label.equals(split.getLabel())){
				return split.getDuration();
			}
		}
		throw new IllegalArgumentException("split label not found");
	}
	
	public int getRepoSize() {
		return repoSize;
	}

	public int getDepth() {
		return depth;
	}

	public int getWidth() {
		return width;
	}

	public int getDupl() {
		return dupl;
	}

	public List<Split> getSplits() {
		return splits;
	}

}
