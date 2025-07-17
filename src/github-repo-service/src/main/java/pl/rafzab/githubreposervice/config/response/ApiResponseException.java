package pl.rafzab.githubreposervice.config.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponseException {
    private final int status;
    private final String message;
}
