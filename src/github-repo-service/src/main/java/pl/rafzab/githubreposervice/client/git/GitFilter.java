package pl.rafzab.githubreposervice.client.git;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GitFilter {
    public Boolean includeFork;
}
