package goalp.evaluation.plans;

public interface ExperimentTimer {

	void begin();

	void split(String string);

	void finish();

	Object result();

}