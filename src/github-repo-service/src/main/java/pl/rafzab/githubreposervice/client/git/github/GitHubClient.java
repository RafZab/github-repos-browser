package pl.rafzab.githubreposervice.client.git.github;

import pl.rafzab.githubreposervice.client.git.GitClient;
import pl.rafzab.githubreposervice.client.git.GitFilter;
import pl.rafzab.githubreposervice.client.git.github.model.Branch;
import pl.rafzab.githubreposervice.client.git.github.model.GitHubError;
import pl.rafzab.githubreposervice.client.git.github.model.Repository;
import pl.rafzab.githubreposervice.config.http.RestClient;
import pl.rafzab.githubreposervice.config.logger.Logger;
import pl.rafzab.githubreposervice.config.mapper.Mapper;
import pl.rafzab.githubreposervice.exception.base.BadRequestException;
import pl.rafzab.githubreposervice.exception.base.BaseException;
import pl.rafzab.githubreposervice.exception.base.ForbiddenException;
import pl.rafzab.githubreposervice.exception.base.NotFoundException;
import pl.rafzab.githubreposervice.exception.http.HttpClientUnknownException;
import pl.rafzab.githubreposervice.model.RepositoryDTO;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitHubClient implements GitClient {
    private static final String REPOS_URL = "https://api.github.com/users/%s/repos";
    private static final String BRANCHES_URL = "https://api.github.com/repos/%s/%s/branches";

    @Override
    public List<RepositoryDTO> getRepositoriesByUsername(String username, GitFilter filter) {
        Logger.info("get repositories for username {} in github", username);

        Repository[] repos = getRepos(username);
        List<Repository> filteredRepos = filterRepositories(repos, filter);
        Map<Repository, Branch[]> branchesMap = getBranchesByRepos(filteredRepos);

        return branchesMap.entrySet().stream()
                .map(entry -> RepositoryDTO.mapFrom(entry.getKey(), entry.getValue()))
                .toList();
    }

    private Repository[] getRepos(String username) {
        Logger.debug("get github repositories for user {}", username);
        String url = REPOS_URL.formatted(username);
        String bodyJsonAsString = sendRequest(url);
        return Mapper.mapTo(bodyJsonAsString, Repository[].class);
    }

    private List<Repository> filterRepositories(Repository[] repos, GitFilter filter) {
        Logger.debug("filter github repository {}", filter);
        GitHubRepoPredicateBuilder predicateBuilder = new GitHubRepoPredicateBuilder(filter);
        return Arrays.stream(repos).filter(predicateBuilder.createRepoPredicate()).toList();
    }

    private Map<Repository, Branch[]> getBranchesByRepos(List<Repository> repositories) {
        Map<Repository, Branch[]> branchesMap = new HashMap<>();
        for (var repo : repositories) {
            Logger.debug("get branches for repository {}", repo.name());
            String url = BRANCHES_URL.formatted(repo.owner().login(), repo.name());
            String bodyJsonAsString = sendRequest(url);

            Branch[] branches = Mapper.mapTo(bodyJsonAsString, Branch[].class);
            branchesMap.put(repo, branches);
        }
        return branchesMap;
    }

    private String sendRequest(String url) {
        HttpRequest request = prepareRequest(url);
        HttpResponse<String> response = RestClient.trySendRequest(request);

        int statusCode = response.statusCode();
        if (statusCode == 200) {
            return response.body();
        }
        throw prepareRequestException(statusCode, response.body());
    }

    private HttpRequest prepareRequest(String url) {
        Logger.debug("prepare request to {}", url);
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/vnd.github+json")
                .build();
    }

    private BaseException prepareRequestException(int statusCode, String bodyJsonAsString) {
        Logger.debug("github api exception with status {}", statusCode);
        GitHubError error = Mapper.mapTo(bodyJsonAsString, GitHubError.class);

        String errorMessage = error.message() + " " + error.documentationUrl();
        return switch (statusCode) {
            case 400 -> new BadRequestException(errorMessage);
            case 401, 403 -> new ForbiddenException(errorMessage);
            case 404 -> new NotFoundException(errorMessage);
            default -> {
                Logger.warn("github unknown api exception with status {}", statusCode);
                yield new HttpClientUnknownException(errorMessage);
            }
        };
    }
}
