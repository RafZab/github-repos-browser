package pl.rafzab.githubreposervice.exception.base;

import lombok.Getter;

@Getter
public class ForbiddenException extends BaseException {
    public ForbiddenException(String message) {
        super(message);
    }
}
