package pl.rafzab.githubreposervice.exception.base;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}
