package pl.rafzab.githubreposervice.exception.mapper;

import pl.rafzab.githubreposervice.exception.base.BaseException;

import java.lang.reflect.Type;

public class MappingException extends BaseException {

    public MappingException(String message, String type) {
        super("JSON to object mapping error: " + type + " - " + message);
    }

    public MappingException(String message, Type type) {
        super("JSON to object mapping error: " + type + " - " + message);
    }
}
