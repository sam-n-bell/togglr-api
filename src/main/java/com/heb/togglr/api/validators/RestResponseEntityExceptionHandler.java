package com.heb.togglr.api.validators;

import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RepositoryConstraintViolationException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        RepositoryConstraintViolationException nevEx = (RepositoryConstraintViolationException) ex;

        List<FieldError> errors = nevEx.getErrors().getFieldErrors();//nevEx.getErrors().getAllErrors().stream().map(p -> p.toString()).collect(Collectors.joining("\n"));

        List<String> errorCodes = new ArrayList<>();
        for (FieldError error: errors) {
            errorCodes.add(error.getCode());
        }

        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST, errorCodes);

        System.out.println(customErrorResponse.getMessage());
        return new ResponseEntity<Object>(customErrorResponse, new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }
}
