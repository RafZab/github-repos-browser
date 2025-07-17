package pl.rafzab.githubreposervice.exception.http;

import pl.rafzab.githubreposervice.exception.base.BaseException;

public class HttpClientSenderException extends BaseException {
    public HttpClientSenderException(String message) {
        super("Problem with http client: " + message);
    }
}
