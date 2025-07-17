package pl.rafzab.githubreposervice.config.response;

import lombok.Getter;

@Getter
public class ApiData<T> {
    private final String message;
    private final T data;

    public ApiData(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
