package pl.rafzab.githubreposervice.exception.base;

import lombok.Getter;

@Getter
public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super(message);
    }
}
