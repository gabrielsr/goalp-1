package goalp.exputil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import goalp.Conf;
import goalp.Conf.Keys;
import goalp.evaluation.plans.ExecuteExperiment.ExecResult;
import goalp.model.Artifact;
import goalp.model.Goal;
import goalp.systems.DeploymentPlan;

public class WriteService {

	public void it(ExecResult result){
		it(result, Conf.get(Keys.DEPL_PLAN_FILE));
	}
	
	public void it(ExecResult result,  String pathStr) {
		
		Path path = Paths.get(pathStr);
		
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			write(result, writer);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private void write(ExecResult result, BufferedWriter writer) throws IOException{
		for(Goal goal: result.getRequest().getGoals()){
			printTree(goal, result.getResultPlan().getPlan(), 0, writer);			
		}
	}

	private void printTree(Goal goal, DeploymentPlan plan, int level, BufferedWriter writer) throws IOException {
		if(level == 0){
			writer.write("## Root Goal");
			newLine(writer);
		}
		printLevel(level, writer);
		writer.write(goal.getIdentication());
		newLine(writer);
		for(Artifact artifact:plan.getSelectedArtifacts()){
			if(artifact.isProvider(goal)){
				printTree(artifact, plan, level+1, writer);
			}
		}
	}


	private void printTree(Artifact artifact, DeploymentPlan plan, int level, BufferedWriter writer) throws IOException {
		printLevel(level, writer);
		writer.write(artifact.getIdentification());
		if(artifact.getDependencies().size() == 0){
			writer.write("::");
			writer.write(artifact.getContextConditions().toString());
			newLine(writer);
		}else {
			newLine(writer);
			for(Goal goal:artifact.getDependencies()){
				printTree(goal, plan, level, writer);			
			}
		}
		
	}
	
	private void printLevel(int level, BufferedWriter writer) throws IOException{
		for(int i =0; i<level; i++){
			writer.write(" ");
		}
		writer.write("|_");
	}
	
	private void newLine(BufferedWriter writer) throws IOException {
		writer.write("\n");
	}
}
