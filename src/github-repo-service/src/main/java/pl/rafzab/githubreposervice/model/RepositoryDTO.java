package pl.rafzab.githubreposervice.model;

import lombok.Getter;
import pl.rafzab.githubreposervice.client.git.github.model.Branch;
import pl.rafzab.githubreposervice.client.git.github.model.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class RepositoryDTO {
    private String repositoryName;
    private String ownerLogin;
    private Collection<BranchDTO> branches;

    public static RepositoryDTO mapFrom(Repository repository, Branch[] branches) {
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.repositoryName = repository.name();
        repositoryDTO.ownerLogin = repository.owner().login();
        repositoryDTO.branches = Arrays.stream(branches)
                .map(BranchDTO::mapFrom)
                .collect(Collectors.toList());

        return repositoryDTO;
    }
}
