package pl.rafzab.githubreposervice.config.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.rafzab.githubreposervice.exception.mapper.MappingException;

public class Mapper {
    public static <T> T mapTo(Object source, Class<T> targetType) {
        ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
        try {
            String jsonString = source instanceof String
                    ? (String) source
                    : objectMapper.writeValueAsString(source);

            return objectMapper.readValue(jsonString, targetType);
        } catch (Exception e) {
            throw new MappingException(e.getMessage(), targetType.getSimpleName());
        }
    }

    public static <T> T mapTo(Object source, TypeReference<T> typeReference) {
        ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

        try {
            String jsonString = source instanceof String
                    ? (String) source
                    : objectMapper.writeValueAsString(source);

            return objectMapper.readValue(jsonString, typeReference);
        } catch (Exception e) {
            throw new MappingException(e.getMessage(), typeReference.getType());
        }
    }
}
