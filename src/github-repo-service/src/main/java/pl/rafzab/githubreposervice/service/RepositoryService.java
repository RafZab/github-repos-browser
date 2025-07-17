package pl.rafzab.githubreposervice.service;

import org.springframework.stereotype.Service;
import pl.rafzab.githubreposervice.client.git.GitClient;
import pl.rafzab.githubreposervice.client.git.GitFilter;
import pl.rafzab.githubreposervice.client.git.GitType;
import pl.rafzab.githubreposervice.exception.base.BadRequestException;
import pl.rafzab.githubreposervice.model.RepositoryDTO;

import java.util.Collection;

@Service
public class RepositoryService {

    public Collection<RepositoryDTO> getUserNonForkRepositories(String username) {
        validUsername(username);

        GitClient gitClient = GitClient.getGitClient(GitType.GITHUB);

        GitFilter filter = GitFilter.builder()
                .includeFork(false)
                .build();
        return gitClient.getRepositoriesByUsername(username, filter);
    }

    private void validUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new BadRequestException("Username is null or empty");
        }
    }
}
