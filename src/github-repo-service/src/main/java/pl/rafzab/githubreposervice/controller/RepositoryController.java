package pl.rafzab.githubreposervice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.rafzab.githubreposervice.config.response.ApiData;
import pl.rafzab.githubreposervice.config.response.ResponseMaker;
import pl.rafzab.githubreposervice.model.dto.RepositoryDTO;
import pl.rafzab.githubreposervice.service.RepositoryService;

import java.util.Collection;

@RestController
@RequestMapping("/api/${api.version}/users/{username}/repositories")
public class RepositoryController {
    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping
    public ResponseEntity<ApiData<Collection<RepositoryDTO>>> getRepositories(@PathVariable String username) {
        var repositories = repositoryService.getRepositories(username);
        return ResponseMaker.ok(repositories);
    }
}
