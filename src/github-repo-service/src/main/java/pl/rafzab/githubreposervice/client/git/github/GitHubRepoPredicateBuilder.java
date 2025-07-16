package pl.rafzab.githubreposervice.client.git.github;

import pl.rafzab.githubreposervice.client.git.GitFilter;
import pl.rafzab.githubreposervice.client.git.github.model.Repository;

import java.util.function.Predicate;

public class GitHubRepoPredicateBuilder {
    private final GitFilter filter;

    public GitHubRepoPredicateBuilder(GitFilter filter) {
        this.filter = filter;
    }

    public Predicate<Repository> createRepoPredicate() {
        if (Boolean.FALSE.equals(filter.getIncludeFork()))
            return repo -> !repo.fork();
        return repo -> true;
    }
}
