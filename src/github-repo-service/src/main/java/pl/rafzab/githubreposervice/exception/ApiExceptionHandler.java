package pl.rafzab.githubreposervice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.rafzab.githubreposervice.config.logger.Logger;
import pl.rafzab.githubreposervice.config.response.ApiResponseException;
import pl.rafzab.githubreposervice.exception.base.BadRequestException;
import pl.rafzab.githubreposervice.exception.base.ForbiddenException;
import pl.rafzab.githubreposervice.exception.base.NotFoundException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<ApiResponseException> handlerApiRequestForbiddenException(ForbiddenException e) {
        Logger.debug(
                """
                        Exception ->
                        Message Exception: %s
                        Stacktrace Exception:
                        """.formatted(e.getMessage()),
                e
        );
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ApiResponseException response = new ApiResponseException(
                httpStatus.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(response, httpStatus);
    }


    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ApiResponseException> handlerApiRequestNotFoundException(NotFoundException e) {
        Logger.debug(
                """
                        Exception ->
                        Message Exception: %s
                        Stacktrace Exception:
                        """.formatted(e.getMessage()),
                e
        );
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiResponseException response = new ApiResponseException(
                httpStatus.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<ApiResponseException> handlerApiRequestBadRequestException(BadRequestException e) {
        Logger.debug(
                """
                        Exception ->
                        Message Exception: %s
                        Stacktrace Exception:
                        """.formatted(e.getMessage()),
                e
        );
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiResponseException response = new ApiResponseException(
                httpStatus.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiResponseException> handlerApiRequestInternalException(Exception e) {
        Logger.error(
                """
                        Service: [github-repo-service]
                        Message Exception: %s
                        Stacktrace Exception:
                        """.formatted(e.getMessage()),
                e
        );

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponseException response = new ApiResponseException(
                httpStatus.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(response, httpStatus);
    }
}
