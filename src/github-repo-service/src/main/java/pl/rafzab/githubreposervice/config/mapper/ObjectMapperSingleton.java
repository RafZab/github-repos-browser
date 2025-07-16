package pl.rafzab.githubreposervice.config.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSingleton {
    private ObjectMapperSingleton() {
    }

    public static ObjectMapper getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        static final ObjectMapper INSTANCE = new ObjectMapper();
    }
}
