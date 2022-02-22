package com.interfaces.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.domain.NoteNotFoundException;
import com.domain.ExpirationTimeExceededException;
import com.domain.NotAuthorizedException;

@Slf4j
@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<Object> handle(NoteNotFoundException exception) {
        log.error("Handling exception. Error {}", NoteNotFoundException.ERROR_MESSAGE, exception);
        return new ResponseEntity<>(new ErrorDto(NoteNotFoundException.ERROR_MESSAGE), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpirationTimeExceededException.class)
    public ResponseEntity<Object> handle(ExpirationTimeExceededException exception) {
        log.error("Handling exception. Error {}", ExpirationTimeExceededException.ERROR_MESSAGE, exception);
        return new ResponseEntity<>(new ErrorDto(ExpirationTimeExceededException.ERROR_MESSAGE), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<Object> handle(NotAuthorizedException exception) {
        log.error("Handling exception. Error {}", NotAuthorizedException.ERROR_MESSAGE, exception);
        return new ResponseEntity<>(new ErrorDto(NotAuthorizedException.ERROR_MESSAGE), HttpStatus.FORBIDDEN);
    }

    @RequiredArgsConstructor
    @Getter
    private class ErrorDto {
        private final String message;
    }
}
