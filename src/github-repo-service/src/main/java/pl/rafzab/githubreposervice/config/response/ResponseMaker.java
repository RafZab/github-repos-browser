package pl.rafzab.githubreposervice.config.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMaker<T> {
    public static <T> ResponseEntity<ApiData<T>> ok(T dto) {
        return ok(dto, ApiMessage.OK);
    }

    public static <T> ResponseEntity<ApiData<T>> ok(T dto, ApiMessage message) {
        ApiData<T> apiData = new ApiData<>(message.name(), dto);
        return ResponseEntity.status(HttpStatus.OK).body(apiData);
    }
}
