package pl.rafzab.githubreposervice.client.git.github;

import pl.rafzab.githubreposervice.client.git.GitClient;
import pl.rafzab.githubreposervice.client.git.GitFilter;
import pl.rafzab.githubreposervice.client.git.github.model.Branch;
import pl.rafzab.githubreposervice.client.git.github.model.Repository;
import pl.rafzab.githubreposervice.config.mapper.Mapper;
import pl.rafzab.githubreposervice.model.RepositoryDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitHubClient implements GitClient {
    private static final String REPOS_URL = "https://api.github.com/users/%s/repos";
    private static final String BRANCHES_URL = "https://api.github.com/repos/%s/%s/branches";

    private final HttpClient httpClient;

    public GitHubClient() {
        this.httpClient = HttpClient.newBuilder().build();
    }

    @Override
    public List<RepositoryDTO> getRepositoriesByUsername(String username, GitFilter filter) {
        Repository[] repos = getRepos(username);

        List<Repository> filteredRepos = filterRepositories(repos, filter);
        Map<Repository, Branch[]> branchesMap = getBranchesByRepos(filteredRepos);

        return branchesMap.entrySet().stream()
                .map(entry -> RepositoryDTO.mapFrom(entry.getKey(), entry.getValue()))
                .toList();
    }

    private Repository[] getRepos(String username) {
        String url = REPOS_URL.formatted(username);
        String bodyJsonAsString = sendRequest(url);
        return Mapper.mapTo(bodyJsonAsString, Repository[].class);
    }

    private List<Repository> filterRepositories(Repository[] repos, GitFilter filter) {
        GitHubRepoPredicateBuilder predicateBuilder = new GitHubRepoPredicateBuilder(filter);
        return Arrays.stream(repos).filter(predicateBuilder.createRepoPredicate()).toList();
    }

    private Map<Repository, Branch[]> getBranchesByRepos(List<Repository> repositories) {
        Map<Repository, Branch[]> branchesMap = new HashMap<>();
        for (var repo : repositories) {
            String url = BRANCHES_URL.formatted(repo.owner().login(), repo.name());
            String bodyJsonAsString = sendRequest(url);

            Branch[] branches = Mapper.mapTo(bodyJsonAsString, Branch[].class);
            branchesMap.put(repo, branches);
        }
        return branchesMap;
    }

    private String sendRequest(String url) {
        HttpRequest request = prepareRequest(url);
        HttpResponse<String> response = trySendRequest(request);

        //TODO: CHECKING STATUS

        return response.body();
    }

    private HttpRequest prepareRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/vnd.github+json")
                .build();
    }

    private HttpResponse<String> trySendRequest(HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
