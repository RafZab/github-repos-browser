package pl.rafzab.githubreposervice.client.git.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Branch(@JsonProperty("name") String name,
                     @JsonProperty("commit") Commit commit) {
}
