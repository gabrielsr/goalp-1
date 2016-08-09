package goalp.evaluation.model;

import java.util.HashMap;
import java.util.Map;

public class ExecSpec implements Cloneable{

	public Map<String, Integer> repoSpec = new HashMap<>();
	
	public Map<String, Integer> getRepoSpec() {
		return repoSpec;
	}
	
	public void setRepoSpec(int width, int depth, int duplication){
		this.repoSpec.put("width", width);
		this.repoSpec.put("depth", depth);
		this.repoSpec.put("duplication", duplication);
	}
	
	public ExecSpec clone(){
		ExecSpec clone = new ExecSpec();
		clone.setRepoSpec(repoSpec.get("width"),
				repoSpec.get("depth"),
				repoSpec.get("duplication"));

		return clone;
	}
	
	@Override
	public String toString() {
		return "ExecSpec [repoSpec=" + repoSpec + "]";
	}
}
