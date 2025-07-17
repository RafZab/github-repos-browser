package pl.rafzab.githubreposervice.exception.base;

import lombok.Getter;

@Getter
public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super(message);
    }
}
