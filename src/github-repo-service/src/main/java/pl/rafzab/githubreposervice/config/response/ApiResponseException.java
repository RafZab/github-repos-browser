package pl.rafzab.githubreposervice.config.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ApiResponseException {
    private final HttpStatus status;
    private final String message;
}
