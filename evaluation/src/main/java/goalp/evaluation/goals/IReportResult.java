package goalp.evaluation.goals;

import java.util.List;
import java.util.function.Consumer;

import goalp.evaluation.model.Experiment;

public interface IReportResult {

	void exec(List<Experiment> experiments);

}
