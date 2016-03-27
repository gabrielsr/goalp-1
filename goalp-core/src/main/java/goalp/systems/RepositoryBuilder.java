package goalp.systems;

import goalp.model.Artifact;

public class RepositoryBuilder {

	private IRepository repository;

	private RepositoryBuilder() {
		this.repository = new SimpleListRepository();
	}

	public static RepositoryBuilder create() {
		return new RepositoryBuilder();
	}

	public IRepository build() {
		IRepository built = this.repository;
		this.repository = null;
		return built;
	}

	public RepositoryBuilder addArtifact(Artifact artifact) {
		this.repository.addArtifact(artifact);
		return this;
	}
}
