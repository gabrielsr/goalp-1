package goalp.evaluation.plans;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Set;

import goalp.evaluation.model.ExperimentSetup;
import goalp.evaluation.model.RepoSpec;
import goalp.exputil.RandomPrismRepositoryUtil;
import goalp.model.Artifact;
import goalp.model.ArtifactBuilder;
import goalp.systems.IRepository;
import goalp.systems.RepositoryBuilder;

public class PrismRepositoryBuilder {
	
	private IRepository repository;
	private List<String> rootGoals;
	
	protected PrismRepositoryBuilder(){
		
	}
	
	public static PrismRepositoryBuilder create(){
		return new PrismRepositoryBuilder();
	}
	
	public PrismRepositoryBuilder setSetupWithRepo(ExperimentSetup setup){
		setup.setRepository(repository);
		return this;
	}

	public PrismRepositoryBuilder setSetupRootGoals(ExperimentSetup setup){
		setup.setRootGoals(rootGoals);
		return this;
	}
	
	public PrismRepositoryBuilder buildBySpec(RepoSpec repoSpec){
		Integer depth = repoSpec.getInteger("depth");
		Integer numOfDependencies = repoSpec.getInteger("numOfDependencies");
		Integer numOfContextConditionsPerGoal = repoSpec.getInteger("contextVariabilityP");
		Integer numOfContextConditionsPerArtifact = repoSpec.getInteger("contextVariabilityK");
		
		@SuppressWarnings("unchecked")
		List<String> contextSpace = (List<String>) repoSpec.getObject(List.class, "contextSpace");
		
		RepositoryBuilder builder = RepositoryBuilder.create();
		
		List<String> rootGoals = new ArrayList<String>();
		int numberOfTrees = repoSpec.getInteger("numberOfTrees");
		
		//crate a forest of i trees
		for(int i = 0; i<numberOfTrees; i++){
			String rootGoal = createArtifactChain(builder,
					numOfDependencies, depth, i, contextSpace, numOfContextConditionsPerGoal, numOfContextConditionsPerArtifact);
			rootGoals.add(rootGoal);
		}
		
		this.repository = builder.build();
		this.rootGoals = rootGoals;
		
		return this;
	}
	
	private String createArtifactChain(RepositoryBuilder builder, int numOfDependencies, int depth, int treeId, 			
			List<String> contexts,  int numOfContextConditionsPerGoal,  int numOfContextConditionsPerArtifact){
		if(depth==0){
			
			//create  context dependent plans

			Deque<String> contextSpace = new ArrayDeque<>();
			Set<String> artifactVariabilityContextSpace = RandomPrismRepositoryUtil.getPElements(contexts, numOfContextConditionsPerGoal);
			contextSpace.addAll(artifactVariabilityContextSpace);
			
			String artifactProvide = "br.unb.tree" + treeId + ":goalLeaf" + randonLabel() + ":0.0.1";
			
			Deque<Deque<String>> combinations = RandomPrismRepositoryUtil.getCombinations(contextSpace, numOfContextConditionsPerArtifact);
			for(Deque<String> contextSelection:combinations){
				//leaf artifact, do not depends on any other
				String contextLabel  = RandomPrismRepositoryUtil.concat(contextSelection);
				String artifactLabel = "br.unb.tree" + treeId + "artifactLeaf" + randonLabel() + contextLabel + ":0.0.1";
				
				
				Artifact leaf = ArtifactBuilder.create()
						.identification(artifactLabel)
						.provides(artifactProvide)
						.condition(contextLabel) // generated the conditions after the selection
						.build();
				builder.addArtifact(leaf);
			}
			
			return artifactProvide;
		}else{
			//create strategies
			
			String[] branchsProvidedGoal = new String[ numOfDependencies ];
			
			//create 'width number' artifacts  
			for(int i = 0; i < numOfDependencies; i++){
				//create 'depth deep' artifact chains
				String provide = createArtifactChain(builder, numOfDependencies, depth -1, treeId, contexts, numOfContextConditionsPerGoal, numOfContextConditionsPerArtifact);
				branchsProvidedGoal[i] = provide;
			}
			
			//no leaf
			String artifactLabel = "br.unb.tree" + treeId + "artifactDeep"+ depth + randonLabel() + ":0.0.1";
			String artifactProvide = "br.unb.tree" + treeId + "goalDeep"+ depth + randonLabel() + ":0.0.1";

			Artifact strategy =  ArtifactBuilder.create()
					.identification(artifactLabel)
					.provides(artifactProvide)
					.dependsOn(branchsProvidedGoal) //TODO should be the provided goal ?
					.build();
			builder.addArtifact(strategy);
			return artifactProvide;
		}
	}
	
	private String randonLabel(){
		Double seed = Math.random();
		return seed.toString().replace("0.","_");
	}
	
}
