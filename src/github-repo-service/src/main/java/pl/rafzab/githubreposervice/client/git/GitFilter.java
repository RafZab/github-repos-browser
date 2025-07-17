package pl.rafzab.githubreposervice.client.git;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GitFilter {
    public Boolean includeFork;
}
