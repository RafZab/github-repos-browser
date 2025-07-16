package pl.rafzab.githubreposervice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.rafzab.githubreposervice.model.dto.RepositoryDTO;
import pl.rafzab.githubreposervice.model.github.Branch;
import pl.rafzab.githubreposervice.model.github.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class RepositoryService {
    private static final String REPOS_URL = "https://api.github.com/users/%s/repos";
    private static final String BRANCHES_URL = "https://api.github.com/repos/%s/%s/branches";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public RepositoryService(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();

    }

    public Collection<RepositoryDTO> getRepositories(String username) {
        return getUserNonForkRepositories(username);
    }


    public Collection<RepositoryDTO> getUserNonForkRepositories(String username) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(REPOS_URL.formatted(username)))
                    .header("Accept", "application/vnd.github+json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // TODO: CATCH EXCEPTION AS UserNotFoundException
//            if (response.statusCode() == 404) {
//                throw new UserNotFoundException(username);
//            }

            // TODO: CATCH EXCEPTION MAPPING
            Repository[] repos = objectMapper.readValue(response.body(), Repository[].class);
            List<Repository> reposWithoutFork = Arrays.stream(repos).filter(repo -> !repo.fork()).toList();

            Map<Repository, Branch[]> branchesMap = new HashMap<>();
            for (Repository repo : reposWithoutFork) {
                HttpRequest branchesRequest = HttpRequest.newBuilder()
                        .uri(URI.create(BRANCHES_URL.formatted(repo.owner().login(), repo.name())))
                        .header("Accept", "application/vnd.github+json")
                        .build();
                HttpResponse<String> branchesResponse = httpClient.send(branchesRequest, HttpResponse.BodyHandlers.ofString());

                // TODO: CATCH EXCEPTION MAPPING
                Branch[] branches = objectMapper.readValue(branchesResponse.body(), Branch[].class);
                branchesMap.put(repo, branches);
            }
            return branchesMap.entrySet().stream()
                    .map(entry -> RepositoryDTO.mapFrom(entry.getKey(), entry.getValue()))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Unknow error", e);
        }

    }
}
