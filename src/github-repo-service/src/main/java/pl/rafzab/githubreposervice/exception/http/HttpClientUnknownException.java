package pl.rafzab.githubreposervice.exception.http;

import pl.rafzab.githubreposervice.exception.base.BaseException;

public class HttpClientUnknownException extends BaseException {
    public HttpClientUnknownException(String message) {
        super("Unknown error: " + message);
    }
}
