package goalp.exputil;

import java.util.List;

import goalp.evaluation.model.Experiment;

public class EvalUtil {

	public static List<String> getFactors(Experiment exp){
		return exp.getEvaluation().getFactorList();
	}
	
	public static String getResponseVariable(Experiment exp){
		return exp.getEvaluation().getResponseVariable();
	}
}
