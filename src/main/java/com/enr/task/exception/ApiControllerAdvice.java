package com.enr.task.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExResult> noSuchElementException(NoSuchElementException e) {
        log.error("[NoSuchElementException]", e);
        ExResult exResult = new ExResult("404", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exResult);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExResult> illegalArgumentException(IllegalArgumentException e) {
        log.error("[IllegalArgumentException]", e);
        ExResult exResult = new ExResult("400", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exResult);
    }

}
