package pl.rafzab.githubreposervice.client.git.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Repository(
        @JsonProperty("name") String name,
        @JsonProperty("fork") boolean fork,
        @JsonProperty("owner") Owner owner) {
}
