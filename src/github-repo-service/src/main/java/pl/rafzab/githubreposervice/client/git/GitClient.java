package pl.rafzab.githubreposervice.client.git;

import pl.rafzab.githubreposervice.client.git.github.GitHubClient;
import pl.rafzab.githubreposervice.model.RepositoryDTO;

import java.util.List;

public interface GitClient {
    static GitClient getGitClient(GitType gitType) {
        return switch (gitType) {
            case GITHUB -> new GitHubClient();
        };
    }

    List<RepositoryDTO> getRepositoriesByUsername(String username, GitFilter filter);
}
