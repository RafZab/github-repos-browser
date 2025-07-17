package pl.rafzab.githubreposervice.client.git;

import pl.rafzab.githubreposervice.client.git.github.GitHubClient;
import pl.rafzab.githubreposervice.config.logger.Logger;
import pl.rafzab.githubreposervice.model.RepositoryDTO;

import java.util.List;

public interface GitClient {
    static GitClient getGitClient(GitType gitType) {
        Logger.debug("get client with type {}", gitType);
        return switch (gitType) {
            case GITHUB -> new GitHubClient();
        };
    }

    List<RepositoryDTO> getRepositoriesByUsername(String username, GitFilter filter);
}
