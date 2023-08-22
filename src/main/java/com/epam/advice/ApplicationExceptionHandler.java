package com.epam.advice;

import com.epam.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {
    private static final String MESSAGE = "message";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionBody> handleInvalidArgument(MethodArgumentNotValidException ex, WebRequest request){
        Map<String,String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        ExceptionBody exceptionBody = new ExceptionBody(new Date().toString(),request.getDescription(false), HttpStatus.BAD_REQUEST.toString(),errorMap);
        log.error("{}",exceptionBody);
        return new ResponseEntity<>(exceptionBody,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionBody> handleDataIntegrityViolation(DataIntegrityViolationException ex,WebRequest request)
    {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put(MESSAGE,ex.getMessage());
        ExceptionBody exceptionBody = new ExceptionBody(new Date().toString(),request.getDescription(false), HttpStatus.BAD_REQUEST.toString(),errorMap);
        return new ResponseEntity<>(exceptionBody,HttpStatus.OK);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionBody> handleUserException(UserException ex,WebRequest request)
    {
        Map<String,String> errorMap = new HashMap<>();
        log.info("error msg: {}",ex.getMessage());
        errorMap.put(MESSAGE,ex.getMessage());
        ExceptionBody exceptionBody = new ExceptionBody(new Date().toString(),request.getDescription(false), HttpStatus.NOT_FOUND.toString(),errorMap);
        log.error("{}",exceptionBody);
        return new ResponseEntity<>(exceptionBody,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionBody> handleRuntimeException(RuntimeException ex,WebRequest request)
    {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put(MESSAGE,ex.getMessage());
        ExceptionBody exceptionBody = new ExceptionBody(new Date().toString(),request.getDescription(false), HttpStatus.BAD_REQUEST.toString(),errorMap);
        log.error("IN RUNTIME {}",exceptionBody);
        return new ResponseEntity<>(exceptionBody,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
