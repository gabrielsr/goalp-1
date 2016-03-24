package goalp.systems;

import goalp.model.Artifact;

public class RepositoryBuilder {

	private Repository repository;

	private RepositoryBuilder() {
		this.repository = new Repository();
	}

	public static RepositoryBuilder create() {
		return new RepositoryBuilder();
	}

	public Repository build() {
		Repository built = this.repository;
		this.repository = null;
		return built;
	}

	public RepositoryBuilder addArtifact(Artifact artifact) {
		this.repository.getKnownArtifacts().add(artifact);
		return this;
	}
}
