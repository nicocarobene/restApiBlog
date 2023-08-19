package com.sistem.blog.exceptions;

import com.sistem.blog.controller.DetailsError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//manejo de exceptiones de toda nuestra app;
@ControllerAdvice
public class GlobalExeptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DetailsError> handlerResourseNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        DetailsError detailsError = new DetailsError(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(detailsError, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BlogAppException.class)
    public ResponseEntity<DetailsError> handlerBlogAppException(BlogAppException exception, WebRequest webRequest){
        DetailsError detailsError = new DetailsError(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(detailsError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<DetailsError> handlerResourceAlreadyExistsException(ResourceAlreadyExistsException exception, WebRequest webRequest){
        DetailsError detailsError = new DetailsError(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(detailsError, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DetailsError> handlerGlobalExeptionHandler(Exception exception, WebRequest webRequest){
        DetailsError detailsError = new DetailsError(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(detailsError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String nameCamp= ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(nameCamp,message);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
