package goalp.evaluation.model;

import java.util.List;

import goalp.evaluation.ExperimentTimerImpl.Split;

public class ExpResult {
	
	List<Split> splits;
	
	public ExpResult(){
		
	}

	public ExpResult(List<Split> splits){
		this.splits = splits;
	}
	
	public Long getDuration(String label){
		for(Split split: splits){
			if(label.equals(split.getLabel())){
				return split.getDuration();
			}
		}
		throw new IllegalArgumentException("split label not found");
	}

}
