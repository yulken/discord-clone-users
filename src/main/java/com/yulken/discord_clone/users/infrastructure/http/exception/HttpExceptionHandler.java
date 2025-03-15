package com.yulken.discord_clone.users.infrastructure.http.exception;

import com.yulken.discord_clone.users.domain.exceptions.ApiBusinessException;
import com.yulken.discord_clone.users.domain.exceptions.ConflictingDataException;
import com.yulken.discord_clone.users.domain.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RestControllerAdvice
public class HttpExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse handle(BindException ex) {
        log(ex, 1);

        List<ValidationError> list = ex.getBindingResult().getAllErrors()
                .stream()
                .map(error -> new ValidationError(((FieldError) error).getField(), error.getDefaultMessage()))
                .toList();

        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Erro de validação", list);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiBusinessException.class)
    public ErrorResponse handle(ApiBusinessException ex) {
        log(ex, 1);
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResponse handle(MissingServletRequestParameterException ex) {
        log(ex, 1);
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handle(MethodArgumentTypeMismatchException ex) {
        log(ex, 1);
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ErrorResponse handle(UnauthorizedException ex) {
        log(ex, 1);
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictingDataException.class)
    public ErrorResponse handle(ConflictingDataException ex) {
        log(ex, 1);
        return new ErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResponse handle(HttpRequestMethodNotSupportedException ex) {
        log(ex, 10);
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handle(Exception ex) {
        log(ex, 10);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro interno");
    }


    private void log(Exception ex, int stackNumbers) {
        log.error("Message: {}", ex.getMessage());
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<StackTraceElement> elementIterator = Stream.of(ex.getStackTrace()).iterator();
        for (int i = 0; i < stackNumbers && elementIterator.hasNext(); i++)
            stringBuilder.append(elementIterator.next()).append("\n");
        log.info("Error trace: {}", stringBuilder);
    }

}
