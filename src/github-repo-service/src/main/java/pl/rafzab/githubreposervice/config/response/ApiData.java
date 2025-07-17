package pl.rafzab.githubreposervice.config.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ApiData<T> {
    private final String message;
    private final T data;

    @JsonCreator
    public ApiData(@JsonProperty("message") String message, @JsonProperty("data") T data) {
        this.message = message;
        this.data = data;
    }
}
