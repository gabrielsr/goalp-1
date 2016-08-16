package goalp.systems;

import goalp.model.Artifact;
import goalp.repository.HashMapRepository;

public class RepositoryBuilder {

	private IRepository repository;

	private RepositoryBuilder() {
		this.repository = new HashMapRepository();
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
