package goalp.evaluation.plans;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import goalp.evaluation.model.ExecSpec;
import goalp.exputil.RandomPrismRepositoryUtil;
import goalp.model.Artifact;
import goalp.model.ArtifactBuilder;
import goalp.systems.RepositoryBuilder;

public class CreatePrismRepository {
	
	public static RepositorySetup exec(ExecSpec spec){
		Integer depth = spec.getInteger("depth");
		Integer numOfDependencies = spec.getInteger("numOfDependencies");
		Integer contextsInEachArtifact = spec.getInteger("contextsInEachArtifact");
		
		@SuppressWarnings("unchecked")
		List<String> contextSpace = (List<String>) spec.getObject(List.class, "contextSpace");
		
		RepositorySetup setup = new RepositorySetup();
		
		RepositoryBuilder builder = RepositoryBuilder.create();
		
		String rootGoal = createArtifactChain(builder,
				numOfDependencies, depth, contextsInEachArtifact, contextSpace);
		
		setup.setRepository(builder.build());
		setup.setRootGoal(rootGoal);
		
		return setup;
	}
	
	static String createArtifactChain(RepositoryBuilder builder, int numOfDependencies, int depth, int contextsInEachArtifact, 			
			List<String> contexts){
		if(depth==0){
			
			//create  context dependent plans

			Deque<String> contextSpace = new ArrayDeque<>();
			contextSpace.addAll(contexts);
			
			String artifactProvide = "br.unb:goalLeaf" + randonLabel() + ":0.0.1";
			
			Deque<Deque<String>> combinations = RandomPrismRepositoryUtil.getCombinations(contextSpace, contextsInEachArtifact);
			for(Deque<String> contextSelection:combinations){
				//leaf artifact, do not depends on any other
				String contextLabel  = RandomPrismRepositoryUtil.concat(contextSelection);
				String artifactLabel = "br.unb:artifactLeaf" + randonLabel() + contextLabel + ":0.0.1";
				
				
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
				String provide = createArtifactChain(builder, numOfDependencies, depth -1, contextsInEachArtifact, contexts);
				branchsProvidedGoal[i] = provide;
			}
			
			//no leaf
			String artifactLabel = "br.unb:artifactDeep"+ depth + randonLabel() + ":0.0.1";
			String artifactProvide = "br.unb:goalDeep"+ depth + randonLabel() + ":0.0.1";

			Artifact strategy =  ArtifactBuilder.create()
					.identification(artifactLabel)
					.provides(artifactProvide)
					.dependsOn(branchsProvidedGoal) //TODO should be the provided goal ?
					.build();
			builder.addArtifact(strategy);
			return artifactProvide;
		}
	}
	
	static String randonLabel(){
		Double seed = Math.random();
		return seed.toString().replace("0.","_");
	}
	
}
