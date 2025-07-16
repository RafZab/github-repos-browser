package pl.rafzab.githubreposervice.model.dto;

import lombok.Getter;
import pl.rafzab.githubreposervice.model.github.Branch;

@Getter
public class BranchDTO {
    private String branchName;
    private String lastCommitSha;

    public static BranchDTO mapFrom(Branch branch) {
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.branchName = branch.name();
        branchDTO.lastCommitSha = branch.commit().sha();
        return branchDTO;
    }
}
